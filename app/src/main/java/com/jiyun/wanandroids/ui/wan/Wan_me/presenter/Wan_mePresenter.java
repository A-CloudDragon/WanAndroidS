package com.jiyun.wanandroids.ui.wan.Wan_me.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanMeinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_meFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_mePresenter extends BasePresenter implements IPresenter {

    private final RequestImp mRequestImp;
    private Wan_meFragment mWanMeFragment;

    public Wan_mePresenter(Wan_meFragment wanMeFragment) {
        this.mWanMeFragment = wanMeFragment;
        mRequestImp = new RequestImp();
    }

    @Override
    public void startLoadData() {
        mWanMeFragment.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://wanandroid.com/wxarticle/chapters/json ", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanMeinfo.class);

                        mWanMeFragment.loadDataHttpSucess(o);
                        mWanMeFragment.hideLoading();

                    } catch (IOException e) {
                        e.printStackTrace();
                        mWanMeFragment.loadDataFaile(e.getMessage());
                        mWanMeFragment.hideLoading();
                    }

                }
            });
        }

    }
}
