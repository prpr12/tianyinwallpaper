package com.zeaze.tianyinwallpaper.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private final List<String> titles;
    private final List<BaseFragment>fragments;
    public BaseFragmentAdapter(FragmentManager fm, List<String>titles, List<BaseFragment>fragments) {
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return titles.size();
    }
}
