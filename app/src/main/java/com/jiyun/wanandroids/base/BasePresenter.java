package com.jiyun.wanandroids.base;

import java.lang.ref.WeakReference;// 强引用  软引用 弱引用  虚引用
public abstract class BasePresenter<V> implements IPresenter<V> {
    //处理V层引用 让V层引用得到及时释放 避免内存泄露
    private WeakReference<V> mWeakReference;

    //RxJava中Disposable的容器 是用来存Disposable对象的
    // 当Activtiy退出的时候切换所有的网络请求，避免内存泄露
    // protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    public abstract void startLoadData();


    //和V引用关联
    @Override
    public void attachView(V view) {
        mWeakReference = new WeakReference<V>(view);
    }


    //让V引用及时释放
    @Override
    public void detachView() {
        if (isAttach()) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    private boolean isAttach() {
        return mWeakReference != null && mWeakReference.get() != null;
    }


    @Override
    public V obtainView() {
        return isAttach() ? mWeakReference.get() : null;
    }
}

