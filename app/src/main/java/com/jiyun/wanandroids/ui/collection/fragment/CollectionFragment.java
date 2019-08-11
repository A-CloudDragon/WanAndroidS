package com.jiyun.wanandroids.ui.collection.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.ui.home.login.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {


    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        startActivity(new Intent(BaseApp.getContext(), LoginActivity.class));
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

}
