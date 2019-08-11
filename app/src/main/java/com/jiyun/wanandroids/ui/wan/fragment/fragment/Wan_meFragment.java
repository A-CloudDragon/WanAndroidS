package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.adapter.WanPagerAdapter;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanMeinfo;
import com.jiyun.wanandroids.ui.wan.Wan_me.presenter.Wan_mePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_meFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_me_tab)
    TabLayout wanMeTab;
    @BindView(R.id.wan_me_vp)
    ViewPager wanMeVp;
    @BindView(R.id.wan_me_pb)
    ProgressBar wanMePb;
    private Wan_mePresenter wan_mePresenter;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private WanPagerAdapter wanProjectPagerAdapter;
    private FragmentManager mFragmentManager;


    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_mePresenter = new Wan_mePresenter(this);
        wan_mePresenter.startLoadData();



    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_me;
    }

    @Override
    public void showLoading() {
        wanMePb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        wanMePb.setVisibility(View.GONE);
    }

    @Override
    public void loadDataHttpSucess(Object o) {
        if (o instanceof WanMeinfo) {
            WanMeinfo wm = (WanMeinfo) o;
            List<WanMeinfo.DataBean> data = wm.getData();


            fragments = new ArrayList<>();
            titles = new ArrayList<>();
            mFragmentManager = getActivity().getSupportFragmentManager();



            for (int i = 0; i < data.size(); i++) {
                titles.add(data.get(i).getName());
//                fragments.add(new Wan_me_itemFragment());

                // 创建每个Tab对应的Fragment，并传递id
                Bundle bundle = new Bundle ();
                bundle.putInt ("id",  data.get(i).getId());
                Wan_me_itemFragment wanMeItemFragment = new Wan_me_itemFragment ();
                wanMeItemFragment.setArguments (bundle);
                fragments.add (wanMeItemFragment);

            }


//            try {
//            // 6. 根据Tab栏数据的个数去创建对应的Fragment，并将id传递
//            for (int i = 0; i < tablist.size (); i++) {
//                // 获取Tab的每个数据
//                GzTabBean.DataBean dataBean = tablist.get (i);
//                // 创建每个Tab对应的Fragment，并传递id
//                Bundle bundle = new Bundle ();
//                bundle.putInt ("id",  dataBean.getId ());
//                GzListFragment xiangMutabFragment = new GzListFragment ();
//                xiangMutabFragment.setArguments (bundle);
//                mTabfragmentlist.add (xiangMutabFragment);
//            }
//            adapter.notifyDataSetChanged ();
//        } catch (Exception e) {
//            e.printStackTrace ();
//        }





            wanProjectPagerAdapter = new WanPagerAdapter(mFragmentManager, fragments, titles);
            wanMeVp.setAdapter(wanProjectPagerAdapter);
            wanMeTab.setupWithViewPager(wanMeVp);

        }






    }

    @Override
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
