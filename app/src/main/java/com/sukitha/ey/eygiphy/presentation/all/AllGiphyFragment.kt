package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentAllGiphyBinding
import com.sukitha.ey.eygiphy.domain.data.Giphy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AllGiphyFragment : Fragment(R.layout.fragment_all_giphy) {

    private var binding: FragmentAllGiphyBinding? = null
    private val viewModel by viewModels<AllGiphyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllGiphyBinding.bind(view)

        setupSearchView()
        setupRecyclerView()

        viewModel.fetchTrendingGiphy()
    }

    private fun setupRecyclerView() {
        val giphy = mutableListOf<Giphy>()
        val adapter = AllGiphyListAdapter(giphy) {
            onFavouritesIconClick(it)
        }
        binding?.giphyRecyclerView?.adapter = adapter
        binding?.giphyRecyclerView?.isNestedScrollingEnabled = false
        binding?.giphyRecyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.giphyRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.giphyList
                .onEach { newGiphy ->
                    giphy.clear()
                    giphy.addAll(newGiphy)
                    adapter.notifyDataSetChanged()
                }.launchIn(this)
        }
    }

    private fun setupSearchView() {
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    viewModel.fetchTrendingGiphy()
                } else {
                    viewModel.fetchGiphy(query)
                }
//                Toast.makeText(activity, "No Language found..", Toast.LENGTH_LONG)
//                    .show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.fetchTrendingGiphy()
                }
                return false
            }
        })
    }

    private fun onFavouritesIconClick(giphy: Giphy) {

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}