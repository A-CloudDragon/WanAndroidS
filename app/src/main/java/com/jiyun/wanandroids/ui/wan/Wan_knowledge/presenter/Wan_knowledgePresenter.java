package com.jiyun.wanandroids.ui.wan.Wan_knowledge.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanKnowledgeinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_knowledgeFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_knowledgePresenter extends BasePresenter implements IPresenter {

    private Wan_knowledgeFragment mWanKnowledgeFragment;
    private RequestImp mRequestImp;
    private int x = 0;



    public Wan_knowledgePresenter() {
    }

    public Wan_knowledgePresenter(Wan_knowledgeFragment wan_knowledgeFragment) {
        this.mWanKnowledgeFragment = wan_knowledgeFragment;
        mRequestImp = new RequestImp();
    }


    @Override
    public void startLoadData() {
        mWanKnowledgeFragment.showLoading();

        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://www.wanandroid.com/tree/json",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();

                                Object obj = GsonUtil.str2Entity(string, WanKnowledgeinfo.class);

                                mWanKnowledgeFragment.loadDataHttpSucess(obj);

                                mWanKnowledgeFragment.hideLoading();

                            } catch (IOException e) {
                                e.printStackTrace();


                                mWanKnowledgeFragment.loadDataFaile(e.getMessage());
                                mWanKnowledgeFragment.hideLoading();
                            }
                        }
                    });
        }
    }


    public int aaa(int a){
        return x=a;
    }



}
