package com.jiyun.wanandroids.ui.wan.Wan_project.presenter;

import android.net.wifi.WpsInfo;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanProjectinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_projectFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_projectPresenter extends BasePresenter implements IPresenter {
    private Wan_projectFragment mwan_projectFragment;
    private final RequestImp mRequestImp;

    public Wan_projectPresenter(Wan_projectFragment wan_projectFragment) {
        this.mwan_projectFragment = wan_projectFragment;
        mRequestImp = new RequestImp();
    }

    @Override
    public void startLoadData() {
        mwan_projectFragment.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("https://www.wanandroid.com/project/tree/json", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanProjectinfo.class);

                        mwan_projectFragment.loadDataHttpSucess(o);

                        mwan_projectFragment.hideLoading();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mwan_projectFragment.loadDataFaile(e.getMessage());
                        mwan_projectFragment.hideLoading();
                    }

                }
            });
        }

    }
}
