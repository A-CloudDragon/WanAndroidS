package com.jiyun.wanandroids.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jiyun.wanandroids.model.entity.WanProjectinfo;

import java.util.ArrayList;
import java.util.List;

public class WanPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    private ArrayList<String>title;

    public WanPagerAdapter(FragmentManager fm, List<Fragment> mList, ArrayList<String> title) {
        super(fm);
        this.mList = mList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
