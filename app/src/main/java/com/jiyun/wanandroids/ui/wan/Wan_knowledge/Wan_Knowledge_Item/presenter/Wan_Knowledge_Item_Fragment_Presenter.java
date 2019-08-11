package com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.presenter;

import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.BaseObserver;
import com.jiyun.wanandroids.base.BasePresenter;
import com.jiyun.wanandroids.base.IPresenter;
import com.jiyun.wanandroids.model.RequestImp;
import com.jiyun.wanandroids.model.entity.WanKnowledgeIteminfo;
import com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.fragment.Wan_Knowledge_Item_Fragment;
import com.jiyun.wanandroids.util.GsonUtil;
import com.jiyun.wanandroids.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Wan_Knowledge_Item_Fragment_Presenter extends BasePresenter implements IPresenter {
    private final RequestImp mRequestImp;
    private Wan_Knowledge_Item_Fragment mWan_knowledge_item_fragment;

    private int cid;
    private int a = 0;


    public Wan_Knowledge_Item_Fragment_Presenter(Wan_Knowledge_Item_Fragment Wan_knowledge_item_fragment) {
        this.mWan_knowledge_item_fragment = Wan_knowledge_item_fragment;
        mRequestImp = new RequestImp();
    }

    @Override
    public void startLoadData() {
        mWan_knowledge_item_fragment.showLoading();

        if (NetWorkUtil.isConnected(BaseApp.getContext())){
            mRequestImp.getData("http://www.wanandroid.com/article/list/"+ a++ +"/json?cid="+cid+"", new BaseObserver<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String string = responseBody.string();

                        Object o = GsonUtil.str2Entity(string, WanKnowledgeIteminfo.class);

                        mWan_knowledge_item_fragment.loadDataHttpSucess(o);
                        mWan_knowledge_item_fragment.hideLoading();

                    } catch (IOException e) {
                        e.printStackTrace();
                        mWan_knowledge_item_fragment.loadDataFaile(e.getMessage());
                        mWan_knowledge_item_fragment.hideLoading();
                    }
                }
            });
        }
    }



    public int aaa (int aa){
        return a=aa;
    }



    public int id(int id){
        return cid = id;
    }
}
