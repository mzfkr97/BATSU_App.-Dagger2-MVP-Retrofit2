package com.roman.batsu.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.roman.batsu.R;

public class LoaderFragment {
    private int startingPosition = 0;


    public static void replaceFragment(Fragment fragment, FragmentManager fragmentManager, boolean withAnimation) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (withAnimation) {
            transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim);
        } else {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        transaction
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragmentNoBackStack(Fragment fragment, FragmentManager fragmentManager, boolean withAnimation) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            if (withAnimation) {
                transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim);
            } else {
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
            transaction
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show fragment with fragment manager with animation parameter
     */

    public boolean loadFragment(Fragment fragment, FragmentManager fragmentManager) {
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean hideOldAndShowNewFrag(FragmentManager fragmentManager, Fragment activeFragment, Fragment showingFragment) {
        if (activeFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(showingFragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void hideOldAndShowNewFragWithBackStackAnimation(FragmentManager fragmentManager,
                                                            Fragment activeFragment,
                                                            Fragment showingFragment,
                                                            int newPosition) {

        if (activeFragment != null) {
            if (newPosition == 0) {
                fragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(showingFragment)
                        .commit();
            }
            if (startingPosition > newPosition) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .hide(activeFragment)
                        .show(showingFragment)
                        .commit();

            }
            if (startingPosition < newPosition) {

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .hide(activeFragment)
                        .show(showingFragment)
                        .commit();

            }
            startingPosition = newPosition;
        }

    }


    /* Тут проверяем фрагмент с анимацией
     * при переключении от большего к меньшему - одна анимация - и наоборот
     * private int startingPosition = 0;
     * задает начальное значение
     * Используется в главном меню
     * */
    public boolean loadFragmentWithBackStackAnimation(Fragment fragment, FragmentManager fragmentManager, int newPosition) {
        if (fragment != null) {
            if (newPosition == 0) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment).commit();
            }
            if (startingPosition > newPosition) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.container, fragment).commit();

            }
            if (startingPosition < newPosition) {
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.container, fragment).commit();

            }
            startingPosition = newPosition;
            return true;
        }

        return false;
    }


}
