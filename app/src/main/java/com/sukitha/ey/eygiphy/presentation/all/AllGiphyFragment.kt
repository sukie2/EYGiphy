package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentAllGiphyBinding
import com.sukitha.ey.eygiphy.presentation.domain.data.Giphy

class AllGiphyFragment : Fragment(R.layout.fragment_all_giphy) {

    private var binding: FragmentAllGiphyBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllGiphyBinding.bind(view)

        val giphy = listOf(Giphy("item 1"), Giphy("Item 2"))
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
    }

    fun onFavouritesIconClick(giphy: Giphy) {

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}