package com.jiyun.wanandroids.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected Unbinder mUnBind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = null;
        if (getFragmentResLayoutId() != 0) {
            inflate = inflater.inflate(getFragmentResLayoutId(), container, false);
            mUnBind = ButterKnife.bind(this, inflate);
            return inflate;
        }
        return inflate;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        if (mUnBind != null)
            mUnBind.unbind();
        super.onDestroyView();
    }

    protected abstract int getFragmentResLayoutId();

}

