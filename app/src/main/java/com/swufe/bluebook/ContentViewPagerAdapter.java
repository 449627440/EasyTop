package com.swufe.bluebook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.swufe.bluebook.TabPagers.JianJieFragment;
import com.swufe.bluebook.TabPagers.LuQuFragment;
import com.swufe.bluebook.TabPagers.QuanJingFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> mFragment;
    public ContentViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragment = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {     //返回Tab名字
        String[] name = {"录取", "简介", "印象", "官网"};
        return  name[position];
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
