package com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanKnowledgeIteminfo;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.adapter.WanKnowledgeItemAdapter;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.presenter.Wan_Knowledge_Item_Fragment_Presenter;
import com.jiyun.wanandroids.util.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wan_Knowledge_Item_Fragment extends BaseFragment implements IView {


    @BindView(R.id.wan_knowledge_item_rlv)
    RecyclerView wanKnowledgeItemRlv;
    @BindView(R.id.wan_knowledge_item_SmartRefreshLayout)
    SmartRefreshLayout wanKnowledgeItemSmartRefreshLayout;
    private Wan_Knowledge_Item_Fragment_Presenter wan_knowledge_item_fragment_presenter;
    private ArrayList<WanKnowledgeIteminfo.DataBean.DatasBean> list;
    private WanKnowledgeItemAdapter wanKnowledgeItemAdapter;

    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_knowledge_item_fragment_presenter = new Wan_Knowledge_Item_Fragment_Presenter(this);
        int id = getArguments().getInt("id");
        wan_knowledge_item_fragment_presenter.id(id);
        wan_knowledge_item_fragment_presenter.startLoadData();
        list = new ArrayList<>();
        wanKnowledgeItemRlv.setLayoutManager(new LinearLayoutManager(BaseApp.getContext()));


        wanKnowledgeItemAdapter = new WanKnowledgeItemAdapter(BaseApp.getContext(), list);

        wanKnowledgeItemRlv.setAdapter(wanKnowledgeItemAdapter);


        wanKnowledgeItemAdapter.setmOnItemClickListener(new WanKnowledgeItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {
                WanKnowledgeIteminfo.DataBean.DatasBean datasBean = list.get(i);
                String link = datasBean.getLink();
                Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                intent.putExtra("url", link);
                startActivity(intent);
            }
        });


    }

    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan__knowledge__item_;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadDataHttpSucess(Object o) {
        if (o instanceof WanKnowledgeIteminfo){
            WanKnowledgeIteminfo wki = (WanKnowledgeIteminfo) o;
            List<WanKnowledgeIteminfo.DataBean.DatasBean> datas = wki.getData().getDatas();

            list.addAll(datas);


        }

    }

    @Override
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(BaseApp.getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
