package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.adapter.WanPagerAdapter;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanProjectinfo;
import com.jiyun.wanandroids.ui.wan.Wan_project.presenter.Wan_projectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_projectFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_project_tab)
    TabLayout wanProjectTab;
    @BindView(R.id.wan_project_vp)
    ViewPager wanProjectVp;
    @BindView(R.id.wan_project_pb)
    ProgressBar wanProjectPb;
    private Wan_projectPresenter wan_projectPresenter;
    private FragmentManager mFragmentManager;
    private List<Fragment> fragmentList;
    private ArrayList<String> titles;


    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_projectPresenter = new Wan_projectPresenter(this);
        wan_projectPresenter.startLoadData();


        //给TAB添加一条分割线
        LinearLayout linearLayout = (LinearLayout) wanProjectTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(BaseApp.getContext(),
                R.drawable.f));
    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_project;
    }


    @Override
    public void showLoading() {
        wanProjectPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        wanProjectPb.setVisibility(View.GONE);
    }

    @Override
    public void loadDataHttpSucess(Object o) {
        WanProjectinfo wp = (WanProjectinfo) o;
        List<WanProjectinfo.DataBean> data = wp.getData();
        mFragmentManager = getActivity().getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();


        for (int i = 0; i < data.size(); i++) {
            Bundle bundle = new Bundle ();
                bundle.putInt ("id",  data.get(i).getId());
            titles.add(data.get(i).getName());
            Wan_project_itemFragment wan = new Wan_project_itemFragment();
            wan.setArguments (bundle);
            fragmentList.add(wan);
        }


        WanPagerAdapter adapter = new WanPagerAdapter(mFragmentManager, fragmentList, titles);
        wanProjectVp.setAdapter(adapter);
        wanProjectTab.setupWithViewPager(wanProjectVp);


//        try {
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


    }

    @Override
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showToast(String msg) {

    }
}
