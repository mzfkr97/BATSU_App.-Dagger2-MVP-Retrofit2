package com.roman.batsu.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.roman.batsu.R;
import com.google.android.material.tabs.TabLayout;

public class ViewPagerHome extends Fragment {


    public static ViewPagerHome newInstance() {
        return new ViewPagerHome();
    }

    private static final int NUM_OF_TABS = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        ViewPager mAdapter = new ViewPager(getChildFragmentManager());
        final androidx.viewpager.widget.ViewPager mPager = view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    class ViewPager extends FragmentPagerAdapter {
        private int mNumOfTabs;

        ViewPager(FragmentManager fm) {
            super(fm);
            this.mNumOfTabs = NUM_OF_TABS;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return HomeFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources()
                    .getStringArray(R.array.groups_name)[position];
        }


        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}