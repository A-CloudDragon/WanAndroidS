package com.jiyun.wanandroids.ui.wan.Wan_project.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanProjectIteminfo;
import com.jiyun.wanandroids.ui.wan.fragment.fragment.Wan_project_itemFragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_project_itemPresenter extends BasePresenter implements IPresenter {
    private final RequestImp mRequestImp;
    private Wan_project_itemFragment mWan_project_itemFragment;

    private int a =1;
    private int id =0;



    public Wan_project_itemPresenter(Wan_project_itemFragment Wan_project_itemFragment) {
        this.mWan_project_itemFragment = Wan_project_itemFragment;
        mRequestImp = new RequestImp();
    }

    @Override
    public void startLoadData() {
        mWan_project_itemFragment.showLoading();
        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("https://www.wanandroid.com/project/list/"+a+++"/json?cid="+id++ +"", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanProjectIteminfo.class);

                        mWan_project_itemFragment.loadDataHttpSucess(o);
                        mWan_project_itemFragment.hideLoading();


                    } catch (IOException e) {
                        e.printStackTrace();
                        mWan_project_itemFragment.loadDataFaile(e.getMessage());
                        mWan_project_itemFragment.hideLoading();
                    }

                }
            });

        }
    }



    public int page(int aa){
        return a =aa;
    }



    public int addId(int xx){
        return id =xx;
    }

}
