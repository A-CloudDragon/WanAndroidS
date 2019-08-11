package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.ui.wan.Wan_me.adapter.WanMeItemAdapter;
import com.jiyun.wanandroids.ui.wan.Wan_project.adapter.WanProjectItemAdapter;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanProjectIteminfo;
import com.jiyun.wanandroids.ui.wan.Wan_project.presenter.Wan_project_itemPresenter;
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
public class Wan_project_itemFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_project_rlv)
    RecyclerView wanProjectRlv;
    @BindView(R.id.wan_project_pb)
    ProgressBar wanProjectPb;
    @BindView(R.id.wan_project_SmartRefreshLayout)
    SmartRefreshLayout wanProjectSmartRefreshLayout;
    private Wan_project_itemPresenter wan_project_itemPresenter;
    private ArrayList<WanProjectIteminfo.DataBean.DatasBean> datasBeans;
    private WanProjectItemAdapter wanProjectItemAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {

        wan_project_itemPresenter = new Wan_project_itemPresenter(this);
        int id = getArguments().getInt("id");
        wan_project_itemPresenter.addId(id);
        wan_project_itemPresenter.startLoadData();

        datasBeans = new ArrayList<>();

        wanProjectRlv.setLayoutManager(new LinearLayoutManager(BaseApp.getContext()));

        wanProjectItemAdapter = new WanProjectItemAdapter(BaseApp.getContext(), datasBeans);

        wanProjectRlv.setAdapter(wanProjectItemAdapter);

        wanProjectItemAdapter.notifyDataSetChanged();


        wanProjectSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                wan_project_itemPresenter.startLoadData();
            }
        });


        wanProjectSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                datasBeans.clear();
                wan_project_itemPresenter.page(0);
                wan_project_itemPresenter.startLoadData();
            }
        });


        wanProjectItemAdapter.setmOnItemClickListener(new WanMeItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {
                String link = datasBeans.get(i).getLink();
                Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                intent.putExtra("url", link);
                startActivity(intent);

            }
        });

    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan__project__item;
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
        if (o instanceof WanProjectIteminfo) {
            WanProjectIteminfo wp = (WanProjectIteminfo) o;
            List<WanProjectIteminfo.DataBean.DatasBean> datas = wp.getData().getDatas();
            datasBeans.addAll(datas);
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
