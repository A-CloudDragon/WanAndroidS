package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanNavigationinfo;
import com.jiyun.wanandroids.ui.wan.Wan_navigation.adapter.WanNavigationAdapter;
import com.jiyun.wanandroids.ui.wan.Wan_navigation.presenter.Wan_navigationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_navigationFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_navigation_vtab)
    VerticalTabLayout wanNavigationVtab;
    @BindView(R.id.wan_navigation_pb)
    ProgressBar wanNavigationPb;
    @BindView(R.id.wan_navigation_rlv)
    RecyclerView wanNavigationRlv;
    private Wan_navigationPresenter wan_navigationPresenter;
    private ArrayList<String> titles;
    private List<WanNavigationinfo.DataBean> list;
    private WanNavigationAdapter wanNavigationAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_navigationPresenter = new Wan_navigationPresenter(this);
        wan_navigationPresenter.startLoadData();
        titles = new ArrayList<>();

        manager = new LinearLayoutManager(BaseApp.getContext());
        wanNavigationRlv.setLayoutManager(manager);


        list = new ArrayList<>();
        wanNavigationAdapter = new WanNavigationAdapter(BaseApp.getContext(), list);

        wanNavigationRlv.setAdapter(wanNavigationAdapter);


        //RecyclerView和tab栏联动       
        wanNavigationRlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                wanNavigationVtab.setTabSelected(manager.findFirstVisibleItemPosition());
            }

        });
        //tab栏和RecyclerView联动 
        wanNavigationVtab.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabView tab, int position) {
                 manager.scrollToPositionWithOffset(position,0);
             }
             @Override
             public void onTabReselected(TabView tab, int position) {
             }
         });


    }

    //布局ID
    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_navigation;
    }

    //显示加载中动画
    @Override
    public void showLoading() {
        wanNavigationPb.setVisibility(View.VISIBLE);
    }

    //关闭加载中动画
    @Override
    public void hideLoading() {
        wanNavigationPb.setVisibility(View.GONE);
    }

    //返回解析结果
    @Override
    public void loadDataHttpSucess(Object o) {
        if (o instanceof WanNavigationinfo) {
            WanNavigationinfo wn = (WanNavigationinfo) o;
            final List<WanNavigationinfo.DataBean> data = wn.getData();

            for (int i = 0; i < data.size(); i++) {
                titles.add(data.get(i).getName());
            }


            //设置粘性头部局，返回对应的头布局
            NormalDecoration normalDecoration = new NormalDecoration() {

                @Override
                public String getHeaderName(int i) {
                    return data.get(i).getName();
                }
            };

            //设置item分割
            wanNavigationRlv.addItemDecoration(normalDecoration);
            //刷新数据
            wanNavigationAdapter.addData(data);
        }


        //垂直Tab
        wanNavigationVtab.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(titles.get(position))//从集合中获取标题
                        .setTextColor(Color.RED, Color.BLACK)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
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
