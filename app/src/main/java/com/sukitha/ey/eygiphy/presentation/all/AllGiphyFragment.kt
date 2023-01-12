package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentAllGiphyBinding
import com.sukitha.ey.eygiphy.domain.data.Giphy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllGiphyFragment : Fragment(R.layout.fragment_all_giphy) {

    private var binding: FragmentAllGiphyBinding? = null
    private val viewModel by viewModels<AllGiphyViewModel>()
    private val giphy = mutableListOf<Giphy>()
    private lateinit var adapter: AllGiphyListAdapter
    private val emptyListIndicator = MutableStateFlow(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllGiphyBinding.bind(view)

        setupSearchView()
        setupRecyclerView()
        observeState()

        if(savedInstanceState == null){
            viewModel.fetchTrendingGiphy()
        }
    }

    private fun observeState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.giphyList.collect { newGiphy ->
                    giphy.clear()
                    giphy.addAll(newGiphy)
                    adapter.notifyDataSetChanged()
                    emptyListIndicator.value = newGiphy.isEmpty()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    binding?.progressBar?.isVisible = it
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                emptyListIndicator.collect {
                    binding?.emptyMessageTextView?.isVisible = it
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showError.collect {
                    if (it.first != null) {
                        var message = it.second
                        if (message.isNullOrBlank()) {
                            message = getString(R.string.generic_error_message)
                        }
                        Toast.makeText(activity, message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = AllGiphyListAdapter(giphy) {
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
    }

    private fun setupSearchView() {
        binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    viewModel.fetchTrendingGiphy()
                } else {
                    viewModel.fetchGiphy(query)
                }
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