package com.sukitha.ey.eygiphy.presentation.favourite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentFavouriteGiphyBinding
import com.sukitha.ey.eygiphy.domain.data.Giphy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteGiphyFragment : Fragment(R.layout.fragment_favourite_giphy) {

    private var binding: FragmentFavouriteGiphyBinding? = null
    private val viewModel by viewModels<FavouriteGiphyViewModel>()
    private val giphy = mutableListOf<Giphy>()
    private lateinit var adapter: FavouriteGiphyListAdapter
    private val emptyListIndicator = MutableStateFlow(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteGiphyBinding.bind(view)
        setupRecyclerView()
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouriteGiphyList.collect { giphyList ->
                    giphy.clear()
                    giphy.addAll(giphyList)
                    adapter.notifyDataSetChanged()
                    emptyListIndicator.value = giphyList.isEmpty()
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

    }

    private fun setupRecyclerView() {
        adapter = FavouriteGiphyListAdapter(giphy) { it ->
            viewModel.removeGiphy(it)
        }
        binding?.giphyRecyclerView?.adapter = adapter
        binding?.giphyRecyclerView?.isNestedScrollingEnabled = false
        binding?.giphyRecyclerView?.layoutManager = GridLayoutManager(context, 2)
        binding?.giphyRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}