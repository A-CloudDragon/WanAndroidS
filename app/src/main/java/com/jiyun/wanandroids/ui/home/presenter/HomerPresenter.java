package com.jiyun.wanandroids.ui.home.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.HomerPopFriendinfo;
import com.jiyun.wanandroids.model.entity.HomerPopHotkeyinfo;
import com.jiyun.wanandroids.ui.home.activity.HomeActivity;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class HomerPresenter extends BasePresenter implements IPresenter {
    private final RequestImp mRequestImp;
    private HomeActivity mHomeActivity;

    public HomerPresenter(HomeActivity homeActivity) {
        this.mHomeActivity = homeActivity;
        mRequestImp = new RequestImp();
    }



    @Override
    public void startLoadData() {
        mHomeActivity.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://www.wanandroid.com/friend/json",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();
                                Object o = GsonUtil.str2Entity(string, HomerPopFriendinfo.class);
                                mHomeActivity.loadfriendSucess(o);
                                mHomeActivity.hideLoading();

                            } catch (IOException e) {
                                e.printStackTrace();
                                mHomeActivity.loadDataFaile(e.getMessage());
                                mHomeActivity.hideLoading();
                            }
                        }
                    });
        }

    }




    public void startLoadData2() {
        mHomeActivity.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://www.wanandroid.com//hotkey/json",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();
                                Object o = GsonUtil.str2Entity(string, HomerPopHotkeyinfo.class);
                                mHomeActivity.loadDataHttpSucess(o);
                                mHomeActivity.hideLoading();

                            } catch (IOException e) {
                                e.printStackTrace();
                                mHomeActivity.loadDataFaile(e.getMessage());
                                mHomeActivity.hideLoading();
                            }
                        }
                    });
        }

    }
}
