package com.jiyun.wanandroids.ui.wan.Wan_homer.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanHomeBannerinfo;
import com.jiyun.wanandroids.model.entity.WanHomeinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_homerFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_homerBannerPresenter extends BasePresenter implements IPresenter {
    private Wan_homerFragment mWanHomeBannerFragment;
    private RequestImp mRequestImp;




    public Wan_homerBannerPresenter() {
    }

    public Wan_homerBannerPresenter(Wan_homerFragment wan_homerFragment) {
        this.mWanHomeBannerFragment = wan_homerFragment;
        mRequestImp = new RequestImp();
    }


    @Override
    public void startLoadData() {
        mWanHomeBannerFragment.showLoading();//显示
        if (NetWorkUtil.isConnected(BaseApp.getContext())) {
            mRequestImp.getData("http://www.wanandroid.com/banner/json/",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            //获取完数据 交给V层更新UI
                            try {
                                String strJson = responseBody.string();
                                Object obj = GsonUtil.str2Entity(strJson, WanHomeBannerinfo.class);
                                mWanHomeBannerFragment.loadDataBannerHttpSucess(obj);
                                mWanHomeBannerFragment.hideLoading();
                            } catch (IOException e) {
                                e.printStackTrace();
                                mWanHomeBannerFragment.loadDataBannerFaile(e.getMessage());
                                mWanHomeBannerFragment.hideLoading();
                            }
                        }
                    });

        } else {
            mWanHomeBannerFragment.hideLoading();
            mWanHomeBannerFragment.showToast("网络连接失败");
        }
    }




}