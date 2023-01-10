package com.sukitha.ey.eygiphy.presentation.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sukitha.ey.eygiphy.R
import com.sukitha.ey.eygiphy.databinding.FragmentAllGiphyBinding

class AllGiphyFragment : Fragment(R.layout.fragment_all_giphy) {

    private var binding: FragmentAllGiphyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllGiphyBinding.bind(view)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}