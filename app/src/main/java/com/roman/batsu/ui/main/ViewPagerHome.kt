package com.roman.batsu.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.roman.batsu.R
import com.roman.batsu.databinding.ViewPagerBinding
import com.roman.batsu.ui.main.HomeFragment.Companion.newInstance

class ViewPagerHome : Fragment() {
    private lateinit var binding: ViewPagerBinding

    companion object {
        fun newInstance(): ViewPagerHome = ViewPagerHome()
    }

    private val numOfTabs = 3

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.view_pager, container, false)
        val mAdapter = ViewPager(childFragmentManager)
        binding.pager.adapter = mAdapter
        binding.pager.offscreenPageLimit = numOfTabs
        binding.tabs.setupWithViewPager(binding.pager)
        return binding.root
    }

    internal inner class ViewPager(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        private val mNumOfTabs: Int = numOfTabs

        override fun getItem(position: Int): Fragment = newInstance(position)

        override fun getPageTitle(position: Int): CharSequence? =
                resources.getStringArray(R.array.groups_name)[position]

        override fun getCount(): Int = mNumOfTabs

    }


}