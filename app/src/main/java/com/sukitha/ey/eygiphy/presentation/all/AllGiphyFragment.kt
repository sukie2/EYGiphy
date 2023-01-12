package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
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
import androidx.recyclerview.widget.RecyclerView
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentAllGiphyBinding
import com.sukitha.ey.eygiphy.domain.data.Giphy
import com.sukitha.ey.eygiphy.util.ALL_GIPHY_PAGE_SIZE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllGiphyFragment : Fragment(R.layout.fragment_all_giphy) {

    private var binding: FragmentAllGiphyBinding? = null
    private val viewModel by viewModels<AllGiphyViewModel>()
    private val favourites = mutableListOf<Giphy>()
    private lateinit var adapter: AllGiphyListAdapter
    private val emptyListIndicator = MutableStateFlow(false)
    var showTrending = true
    var searchQuery = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllGiphyBinding.bind(view)

        setupSearchView()
        setupRecyclerView()
        observeState()

        if (savedInstanceState == null) {
            viewModel.fetchTrendingGiphy()
        }
    }

    private fun observeState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.giphyList.collect { newGiphy ->
                    adapter.differ.submitList(newGiphy)
                    val totalPages = newGiphy.size / ALL_GIPHY_PAGE_SIZE + 2
                    isLastPage = viewModel.page == totalPages
                    emptyListIndicator.value = newGiphy.isEmpty()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouriteGiphyList.collect { newGiphy ->
                    favourites.clear()
                    favourites.addAll(newGiphy)
                    adapter.notifyDataSetChanged()
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
        adapter = AllGiphyListAdapter(favourites) { it, isFavourite ->
            if (isFavourite) {
                viewModel.insertGiphy(it)
            } else {
                viewModel.removeGiphy(it)
            }
        }
        binding?.giphyRecyclerView?.adapter = adapter
        binding?.giphyRecyclerView?.addOnScrollListener(scrollListener)
        binding?.giphyRecyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.giphyRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrBlank()) {
                    showTrending = true
                    viewModel.fetchTrendingGiphy()
                } else {
                    showTrending = false
                    searchQuery = query
                    viewModel.fetchGiphy(query)
                }
                viewModel.resetPaging()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    showTrending = true
                    viewModel.resetPaging()
                    viewModel.fetchTrendingGiphy()
                }
                return false
            }
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= ALL_GIPHY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                if(showTrending) {
                    viewModel.fetchTrendingGiphy()
                } else {
                    viewModel.fetchGiphy(searchQuery)
                }
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }
}