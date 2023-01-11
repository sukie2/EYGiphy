package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View
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

        viewModel.fetchTrendingGiphy()

        val giphy = mutableListOf(Giphy("item 1", "", ""), Giphy("Item 2", "", ""))
        val adapter = AllGiphyListAdapter(giphy) {
            onFavouritesIconClick(it)
        }
        binding?.giphyRecyclerView?.adapter = adapter
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

    private fun onFavouritesIconClick(giphy: Giphy) {

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}