package com.roman.batsu.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.roman.batsu.R

class LoaderFragment {
    private var startingPosition = 0

    fun hideOldAndShowNewFrag(
            fragmentManager: FragmentManager, activeFragment: Fragment?, showingFragment: Fragment?): Boolean {
        if (activeFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(showingFragment!!)
                    .commit()
            return true
        }
        return false
    }

    fun hideOldAndShowNewFragWithBackStackAnimation(fragmentManager: FragmentManager,
                                                    activeFragment: Fragment?,
                                                    showingFragment: Fragment?,
                                                    newPosition: Int) {
        if (activeFragment != null) {
            if (newPosition == 0) {
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(showingFragment!!)
                        .commit()
            }
            if (startingPosition > newPosition) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .hide(activeFragment)
                        .show(showingFragment!!)
                        .commit()
            }
            if (startingPosition < newPosition) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .hide(activeFragment)
                        .show(showingFragment!!)
                        .commit()
            }
            startingPosition = newPosition
        }
    }
}