package com.jiyun.wanandroids.ui.wan.Wan_me.presenter;

import android.os.Bundle;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseFragment;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanMeIteminfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_meFragment;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_me_itemFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_me_itemPresenter extends BasePresenter implements IPresenter {

    private Wan_me_itemFragment mWan_me_itemFragment;
    private final RequestImp mRequestImp;
    private int a = 408;
    private int x;
    private String k;


    public Wan_me_itemPresenter(Wan_me_itemFragment Wan_me_itemFragment) {
        this.mWan_me_itemFragment = Wan_me_itemFragment;
        mRequestImp = new RequestImp();
    }


    @Override
    public void startLoadData() {
        mWan_me_itemFragment.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
                                    //http://wanandroid.com/wxarticle/list/405/1/json?k=Java
            mRequestImp.getData("https://wanandroid.com/wxarticle/list/"+ a+"/"+ x++ +"/json", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanMeIteminfo.class);

                        mWan_me_itemFragment.loadDataHttpSucess(o);
                        mWan_me_itemFragment.hideLoading();

                    } catch (IOException e) {
                        e.printStackTrace();

                        mWan_me_itemFragment.loadDataFaile(e.getMessage());
                        mWan_me_itemFragment.hideLoading();
                    }

                }
            });
        }
    }













    public void startSearchData() {
        mWan_me_itemFragment.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            //http://wanandroid.com/wxarticle/list/405/1/json?k=Java
            mRequestImp.getData("https://wanandroid.com/wxarticle/list/"+ a+"/"+ x++ +"/json?k="+k+"", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanMeIteminfo.class);

                        mWan_me_itemFragment.loadDataHttpSucess(o);
                        mWan_me_itemFragment.hideLoading();

                    } catch (IOException e) {
                        e.printStackTrace();

                        mWan_me_itemFragment.loadDataFaile(e.getMessage());
                        mWan_me_itemFragment.hideLoading();
                    }

                }
            });
        }
    }
    public int aaa(int aa){
        return a =aa;
    }



    public int xxx(int xx){
        return x =xx;
    }



    public String kkk(String kk){
        return k = kk;
    }


}