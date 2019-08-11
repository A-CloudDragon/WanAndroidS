package com.jiyun.wanandroids.ui.wan.Wan_homer.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanHomeinfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_homerFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_homerPresenter extends BasePresenter implements IPresenter {


    private Wan_homerFragment mWanHomeFragment;
    private RequestImp mRequestImp;
    private int x = 0;



    public Wan_homerPresenter() {
    }

    public Wan_homerPresenter(Wan_homerFragment wan_homerFragment) {
        this.mWanHomeFragment = wan_homerFragment;
        mRequestImp = new RequestImp();
    }


    @Override
    public void startLoadData() {
        mWanHomeFragment.showLoading();//显示
        if (NetWorkUtil.isConnected(BaseApp.getContext())) {
            mRequestImp.getData("http://www.wanandroid.com/article/list/"+ x++ +"/json/",
                    new BaseObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            //获取完数据 交给V层更新UI
                            try {
                                String strJson = responseBody.string();
                                Object obj = GsonUtil.str2Entity(strJson, WanHomeinfo.class);
                               mWanHomeFragment.loadDataHttpSucess(obj);
                                mWanHomeFragment.hideLoading();
                            } catch (IOException e) {
                                e.printStackTrace();
                                mWanHomeFragment.loadDataFaile(e.getMessage());
                                mWanHomeFragment.hideLoading();
                            }
                        }
                    });

        } else {
            mWanHomeFragment.hideLoading();
            mWanHomeFragment.showToast("网络连接失败");
        }
    }



    public int aaa(int a){
        return x = a;
    }
}
