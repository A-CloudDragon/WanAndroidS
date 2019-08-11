package com.jiyun.wanandroids.ui.wan.Wan_navigation.presenter;

import android.util.Log;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanNavigationinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_navigationFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_navigationPresenter extends BasePresenter implements IPresenter {
    private final RequestImp mRequestImp;
    private Wan_navigationFragment mWan_navigationFragment;


    public Wan_navigationPresenter(Wan_navigationFragment Wan_navigationFragment) {
        this.mWan_navigationFragment = Wan_navigationFragment;
        mRequestImp = new RequestImp();
    }

    @Override
    public void startLoadData() {
        mWan_navigationFragment.showLoading();//显示
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://www.wanandroid.com/navi/json",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();

                                Object o = GsonUtil.str2Entity(string, WanNavigationinfo.class);

                                mWan_navigationFragment.loadDataHttpSucess(o);
                                mWan_navigationFragment.hideLoading();
                                Log.e("tab","NAV:"+o.toString());
                            } catch (IOException e) {
                                e.printStackTrace();

                                mWan_navigationFragment.loadDataFaile(e.getMessage());
                                mWan_navigationFragment.hideLoading();
                            }
                        }
                    });
        }

    }
}
