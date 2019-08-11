package com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.adapter.WanPagerAdapter;
import com.jiyun.wanandroids.base.BaseActivtiy;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.model.entity.WanKnowledgeinfo;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.fragment.Wan_Knowledge_Item_Fragment;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.presenter.Wan_Knowledge_Item_Presenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Wan_Knowledge_Item_Activity extends BaseActivtiy<Wan_Knowledge_Item_Presenter> {


    @BindView(R.id.wan_knowledge_item_tab)
    TabLayout wanKnowledgeItemTab;
    @BindView(R.id.wan_knowledge_item_vp)
    ViewPager wanKnowledgeItemVp;
    private ArrayList<WanKnowledgeinfo.DataBean.ChildrenBean> list;
    private ArrayList<String> title;
    private ArrayList<Fragment> fragments;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Bundle bundl = intent.getExtras();
        WanKnowledgeinfo.DataBean data = (WanKnowledgeinfo.DataBean) bundl.getSerializable("data");

        list = new ArrayList<>();
        title = new ArrayList<>();
        fragments = new ArrayList<>();
        List<WanKnowledgeinfo.DataBean.ChildrenBean> children = data.getChildren();
        list.addAll(children);



        for (int i = 0;i<list.size();i++){
            title.add(list.get(i).getName());
            Wan_Knowledge_Item_Fragment wan_knowledge_item_fragment = new Wan_Knowledge_Item_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",list.get(i).getId());
            wan_knowledge_item_fragment.setArguments(bundle);
            fragments.add(wan_knowledge_item_fragment);
        }

        WanPagerAdapter wanPagerAdapter = new WanPagerAdapter(getSupportFragmentManager(), fragments, title);

        wanKnowledgeItemVp.setAdapter(wanPagerAdapter);

        wanKnowledgeItemTab.setupWithViewPager(wanKnowledgeItemVp);

    }

    @Override
    protected Wan_Knowledge_Item_Presenter createPresenter() {
        return new Wan_Knowledge_Item_Presenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_wan__knowledge__item_;
    }


}
