package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.activity.Wan_Knowledge_Item_Activity;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.adapter.WanKnowledgeAdapter;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanKnowledgeinfo;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.presenter.Wan_knowledgePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_knowledgeFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_knowledge_rlv)
    RecyclerView wanKnowledgeRlv;
    @BindView(R.id.wan_knowledge_SmartRefreshLayout)
    SmartRefreshLayout wanKnowledgeSmartRefreshLayout;
    @BindView(R.id.wan_knowledge_pb)
    ProgressBar wanKnowledgePb;
    private ArrayList<WanKnowledgeinfo.DataBean> list;
    private WanKnowledgeAdapter wanKnowledgeAdapter;
    private Wan_knowledgePresenter wan_knowledgePresenter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_knowledgePresenter = new Wan_knowledgePresenter(this);
        wan_knowledgePresenter.startLoadData();


        list = new ArrayList<>();

        wanKnowledgeRlv.setLayoutManager(new LinearLayoutManager(BaseApp.getContext()));

        wanKnowledgeAdapter = new WanKnowledgeAdapter(BaseApp.getContext(), list);

        wanKnowledgeRlv.setAdapter(wanKnowledgeAdapter);




        wanKnowledgeSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.clear();
                wan_knowledgePresenter.aaa(0);
                wan_knowledgePresenter.startLoadData();
            }
        });


        wanKnowledgeSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                wan_knowledgePresenter.startLoadData();
            }
        });










        wanKnowledgeAdapter.setmOnItemClickListener(new WanKnowledgeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int i) {
                WanKnowledgeinfo.DataBean dataBean = list.get(i);
                Intent intent = new Intent(BaseApp.getContext(), Wan_Knowledge_Item_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",dataBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_knowledge;
    }

    @Override//显示
    public void showLoading() {
        wanKnowledgePb.setVisibility(View.VISIBLE);
    }

    @Override//关闭
    public void hideLoading() {
        wanKnowledgePb.setVisibility(View.GONE);
    }



    @Override//网路请求成功
    public void loadDataHttpSucess(Object o) {
        if (o instanceof WanKnowledgeinfo){
            WanKnowledgeinfo wk = (WanKnowledgeinfo) o;
            List<WanKnowledgeinfo.DataBean> data = wk.getData();

            list.addAll(data);

            wanKnowledgeSmartRefreshLayout.finishRefresh();
            wanKnowledgeSmartRefreshLayout.finishLoadmore();
            wanKnowledgeAdapter.notifyDataSetChanged();
        }
    }

    @Override//网路请求错误
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showToast(String msg) {

    }
}
