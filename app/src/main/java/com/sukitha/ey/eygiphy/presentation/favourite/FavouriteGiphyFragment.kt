package com.sukitha.ey.eygiphy.presentation.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentFavouriteGiphyBinding

class FavouriteGiphyFragment : Fragment(R.layout.fragment_favourite_giphy) {

    private var binding: FragmentFavouriteGiphyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteGiphyBinding.bind(view)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}