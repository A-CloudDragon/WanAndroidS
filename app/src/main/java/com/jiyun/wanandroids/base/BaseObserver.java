package com.jiyun.wanandroids.base;

import android.util.Log;

import com.bumptech.glide.load.HttpException;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<ResponseBody> implements Observer<ResponseBody> {
    private String mErrMsg;
    protected Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public abstract void onNext(ResponseBody responseBody);

    @Override
    public void onError(Throwable e) {
        Log.e("Subscrber Error", e.getMessage());
        if (e instanceof HttpException) {
            String msg = ((HttpException) e).getMessage();
            mErrMsg = "网络连接异常" + msg;
        } else if (e instanceof IOException) {
            String msg = ((IOException) e).getMessage();
            mErrMsg = "网络异常" + msg;
        }
        disposable();
    }

    private void disposable() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    @Override
    public void onComplete() {
        disposable();
    }
}
