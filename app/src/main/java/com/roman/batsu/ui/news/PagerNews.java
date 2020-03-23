package com.roman.batsu.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.roman.batsu.R;
import com.roman.batsu.ui.news.news_group.NewsFragment;
import com.roman.batsu.ui.news.universe.UniverseNewsFrag;

public class PagerNews extends Fragment {


    public static PagerNews newInstance() {
        return new PagerNews();
    }

    private static final int NUM_OF_TABS = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;
        Pager mAdapter = new Pager(getChildFragmentManager());
        final androidx.viewpager.widget.ViewPager mPager = view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(NUM_OF_TABS);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);
    }

    class Pager extends FragmentPagerAdapter {
        private int mNumOfTabs;

        Pager(FragmentManager fm) {
            super(fm);
            this.mNumOfTabs = NUM_OF_TABS;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return NewsFragment.newInstance();
            }else {
                return UniverseNewsFrag.newInstance();
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources()
                    .getStringArray(R.array.news_name)[position];
        }


        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
