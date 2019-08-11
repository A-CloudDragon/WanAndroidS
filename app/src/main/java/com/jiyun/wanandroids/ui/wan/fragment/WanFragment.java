package com.jiyun.wanandroids.ui.wan.fragment;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_homerFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_knowledgeFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_meFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_navigationFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_projectFragment;
import com.jiyun.wanandroids.util.NoScrollViewPager;


import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WanFragment extends BaseFragment {


    @BindView(R.id.wan_vp)
    NoScrollViewPager wanVp;
    @BindView(R.id.wan_bnv)
    BottomNavigationView wanBnv;
    private ArrayList<Fragment> fragments;

    @Override
    protected void initData(Bundle savedInstanceState) {

        //设置VP禁止滑动
        wanVp.setNoScroll(true);

        fragments = new ArrayList<>();
        fragments.add(new Wan_homerFragment());
        fragments.add(new Wan_knowledgeFragment());
        fragments.add(new Wan_meFragment());
        fragments.add(new Wan_navigationFragment());
        fragments.add(new Wan_projectFragment());


        wanVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });






        wanBnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.wan_home:
                        wanVp.setCurrentItem(0);
                        break;

                    case R.id.wan_knowledge:
                        wanVp.setCurrentItem(1);
                        break;

                    case R.id.wan_me:
                        wanVp.setCurrentItem(2);
                        break;

                    case R.id.wan_navigation:
                        wanVp.setCurrentItem(3);
                        break;

                    case R.id.wan_project:
                        wanVp.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan;
    }
}
