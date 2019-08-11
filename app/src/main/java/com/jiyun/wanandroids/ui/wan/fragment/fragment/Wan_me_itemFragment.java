package com.jiyun.wanandroids.ui.wan.fragment.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.WanMeIteminfo;
import com.jiyun.wanandroids.ui.wan.Wan_me.adapter.WanMeItemAdapter;
import com.jiyun.wanandroids.ui.wan.Wan_me.presenter.Wan_me_itemPresenter;
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
public class Wan_me_itemFragment extends BaseFragment implements IView {


    @BindView(R.id.wan_me_item_rlv)
    RecyclerView wanMeItemRlv;
    @BindView(R.id.wan_me_item_SmartRefreshLayout)
    SmartRefreshLayout wanMeItemSmartRefreshLayout;
    @BindView(R.id.wan_me_item_search)
    SearchView wanMeItemSearch;
    @BindView(R.id.wan_me_item_search_but)
    Button wanMeItemSearchBut;
    private Wan_me_itemPresenter wan_me_itemPresenter;
    private ArrayList<WanMeIteminfo.DataBean.DatasBean> datasBeans;
    private WanMeItemAdapter wanMeItemAdapter;
    private int id;
    private String tex;

    @Override
    protected void initData(Bundle savedInstanceState) {
        wan_me_itemPresenter = new Wan_me_itemPresenter(this);
        id = getArguments().getInt("id");
        wan_me_itemPresenter.aaa(id);
        wan_me_itemPresenter.startLoadData();
        datasBeans = new ArrayList<>();
        wanMeItemRlv.setLayoutManager(new LinearLayoutManager(BaseApp.getContext()));
        wanMeItemAdapter = new WanMeItemAdapter(BaseApp.getContext(), datasBeans);
        wanMeItemRlv.setAdapter(wanMeItemAdapter);
        wanMeItemAdapter.notifyDataSetChanged();


        wanMeItemSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                wan_me_itemPresenter.startLoadData();
            }
        });


        wanMeItemSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                datasBeans.clear();
                wan_me_itemPresenter.xxx(0);
                wan_me_itemPresenter.startLoadData();
            }
        });


        wanMeItemAdapter.setmOnItemClickListener(new WanMeItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i) {
                String link = datasBeans.get(i).getLink();
                Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                intent.putExtra("url", link);
                startActivity(intent);

            }
        });




        //搜索框设置
        wanMeItemSearch.setIconifiedByDefault(true);
        wanMeItemSearch.setFocusable(true);
        wanMeItemSearch.setIconified(false);
        wanMeItemSearch.requestFocusFromTouch();



        // 设置搜索文本监听
        wanMeItemSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {



            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(BaseApp.getContext(),query,Toast.LENGTH_LONG).show();

                datasBeans.clear();
                wan_me_itemPresenter.startSearchData();
                wan_me_itemPresenter.aaa(id);
                wan_me_itemPresenter.kkk(tex);
                wanMeItemAdapter.notifyDataSetChanged();


                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(BaseApp.getContext(),newText,Toast.LENGTH_LONG).show();
                tex = newText;
                return false;
            }
        });


        wanMeItemSearchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasBeans.clear();
                wan_me_itemPresenter.startSearchData();
                wan_me_itemPresenter.aaa(id);
                wan_me_itemPresenter.kkk(tex);
                wanMeItemAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected int getFragmentResLayoutId() {
        return R.layout.fragment_wan_me_item;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadDataHttpSucess(Object o) {
        if (o instanceof WanMeIteminfo) {
            WanMeIteminfo wm = (WanMeIteminfo) o;
            List<WanMeIteminfo.DataBean.DatasBean> datas = wm.getData().getDatas();
            datasBeans.addAll(datas);
            wanMeItemSmartRefreshLayout.finishLoadmore();
            wanMeItemSmartRefreshLayout.finishRefresh();

            if (datas!=null){
                Toast.makeText(BaseApp.getContext(),"本次干货搜集到了"+datas.size()+"个哦！",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(BaseApp.getContext(),"没有更多干活哦！！！",Toast.LENGTH_LONG).show();
            }

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
