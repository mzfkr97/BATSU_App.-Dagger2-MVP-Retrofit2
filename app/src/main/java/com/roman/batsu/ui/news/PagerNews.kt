package com.roman.batsu.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.roman.batsu.R
import com.roman.batsu.ui.news.news_group.NewsFragment
import com.roman.batsu.ui.news.universe.UniverseNewsFrag

class PagerNews : Fragment(R.layout.view_pager) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view = view
        val mAdapter = Pager(childFragmentManager)
        val mPager: ViewPager? = view?.findViewById(R.id.pager)
        mPager?.adapter = mAdapter
        mPager?.offscreenPageLimit = NUM_OF_TABS
        val tabLayout: TabLayout? = view?.findViewById(R.id.tabs)
        tabLayout?.setupWithViewPager(mPager)
    }

    internal inner class Pager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

        private val mNumOfTabs: Int = NUM_OF_TABS

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                NewsFragment.newInstance()
            } else {
                UniverseNewsFrag.newInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? = resources.getStringArray(R.array.news_name)[position]


        override fun getCount(): Int = mNumOfTabs

    }

    companion object {
        fun newInstance(): PagerNews = PagerNews()
        private const val NUM_OF_TABS = 2
    }
}