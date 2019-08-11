package com.jiyun.wanandroids.base;

public interface IView<T> {
    void showLoading();

    void hideLoading();

    void loadDataHttpSucess(T t);

    void loadDataFaile(String errorMsg);

    void showToast(String msg);


}
