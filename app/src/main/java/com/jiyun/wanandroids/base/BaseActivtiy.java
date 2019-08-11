package com.jiyun.wanandroids.base;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivtiy<Presenter extends BasePresenter> extends AppCompatActivity {
    protected Unbinder mUnBinder;
    protected Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getResLayoutId() != 0)
            setContentView(getResLayoutId());
        mUnBinder = ButterKnife.bind(this);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initView();
        initData(savedInstanceState);

    }

    protected abstract void initView();

    protected abstract Presenter createPresenter();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract int getResLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}

