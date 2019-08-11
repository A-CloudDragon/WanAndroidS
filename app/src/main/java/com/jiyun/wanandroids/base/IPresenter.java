package com.jiyun.wanandroids.base;

//1.链接V层和M层的桥梁  2.处理业务逻辑
public interface IPresenter<V> {
    void startLoadData();

    //关联V层的生命周期
    void attachView(V view);

    void detachView();

    V obtainView();



}
