package com.sukitha.ey.eygiphy.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sukitha.ey.eygiphy.presentation.all.AllGiphyFragment
import com.sukitha.ey.eygiphy.presentation.favourite.FavouriteGiphyFragment

private const val NUM_TABS = 2

class MainViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllGiphyFragment()
            1 -> return FavouriteGiphyFragment()
        }
        return AllGiphyFragment()
    }
}