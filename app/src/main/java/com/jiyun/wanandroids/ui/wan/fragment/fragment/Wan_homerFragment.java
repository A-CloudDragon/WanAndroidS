package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.MainActivity;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.ui.wan.Wan_homer.adapter.WanHomeAdapter;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanHomeBannerinfo;
import com.jiyun.wanandroids.model.entity.WanHomeinfo;
import com.jiyun.wanandroids.ui.wan.Wan_homer.presenter.Wan_homerBannerPresenter;
import com.jiyun.wanandroids.ui.wan.Wan_homer.presenter.Wan_homerPresenter;
import com.jiyun.wanandroids.util.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_homerFragment extends BaseFragment implements IView {



    @BindView(R.id.wan_home_rlv)
    RecyclerView wanHomeRlv;
    @BindView(R.id.wan_home_pb)
    ProgressBar wanHomePb;
    @BindView(R.id.wan_home_SmartRefreshLayout)
    SmartRefreshLayout wanHomeSmartRefreshLayout;
    private Wan_homerPresenter wan_homerPresenter;
    private ArrayList<WanHomeinfo.DataBean.DatasBean> list;
    private ArrayList<String> banner;
    private ArrayList<String> bannertitle;
    private WanHomeAdapter wanHomeAdapter;
    private Wan_homerBannerPresenter wan_homerBannerPresenter;
    private ArrayList<String> bannerLink;


    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_homerPresenter = new Wan_homerPresenter(this);
        wan_homerPresenter.startLoadData();

        wan_homerBannerPresenter = new Wan_homerBannerPresenter(this);
        wan_homerBannerPresenter.startLoadData();


        list = new ArrayList<>();
        banner = new ArrayList<>();
        bannerLink = new ArrayList<>();
        bannertitle = new ArrayList<>();
        wanHomeRlv.setLayoutManager(new LinearLayoutManager(BaseApp.getContext()));

        wanHomeAdapter = new WanHomeAdapter(BaseApp.getContext(), list,banner,bannerLink,bannertitle);

        wanHomeRlv.setAdapter(wanHomeAdapter);



        wanHomeSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                wan_homerPresenter.startLoadData();
            }
        });


        wanHomeSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
               wan_homerPresenter.aaa(0);
                wan_homerPresenter.startLoadData();
            }
        });



        wanHomeAdapter.setmOnItemClickListener(new WanHomeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {
                String apkLink = list.get(i).getLink();
                Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                intent.putExtra("url", apkLink);
                startActivity(intent);
            }
        });



//        wanHomeRlv.setItemAnimator(R.style.windowAnimation);
//
//        Animation animation = AnimationUtils.loadAnimation(BaseApp.getContext(), R.anim.popup_window_enter);
//        wanHomeRlv.setItemAnimator();
//        wanHomeRlv.startAnimation(animation);

    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_homer;
    }

    //显示进度条
    @Override
    public void showLoading() {
        wanHomePb.setVisibility(View.VISIBLE);
    }

    //隐藏进度条
    @Override
    public void hideLoading() {
        wanHomePb.setVisibility(View.GONE);
    }

    //解析结果
    @Override
    public void loadDataHttpSucess(Object obj) {
        if (obj instanceof WanHomeinfo) {
            WanHomeinfo info = (WanHomeinfo) obj;
            WanHomeinfo.DataBean data = info.getData();
            List<WanHomeinfo.DataBean.DatasBean> datas = data.getDatas();
            list.addAll(datas);
            wanHomeSmartRefreshLayout.finishLoadmore();
            wanHomeSmartRefreshLayout.finishRefresh();
            wanHomeAdapter.notifyDataSetChanged();
        }
    }


    public void loadDataBannerHttpSucess(Object obj) {
        if (obj instanceof WanHomeBannerinfo) {
            WanHomeBannerinfo info = (WanHomeBannerinfo) obj;
            final List<WanHomeBannerinfo.DataBean> data = info.getData();

            for (int i = 0;i<data.size();i++){
                banner.add(data.get(i).getImagePath());
                bannertitle.add(data.get(i).getTitle());
                bannerLink.add(data.get(i).getUrl());
            }
        }
    }












    //网路请求失败返回错误
    public void loadDataBannerFaile(String errorMsg) {
        showToast(errorMsg);
    }





    //网路请求失败返回错误
    @Override
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }


    @Override
    public void showToast(String msg) {
        if (msg != null)
            Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
