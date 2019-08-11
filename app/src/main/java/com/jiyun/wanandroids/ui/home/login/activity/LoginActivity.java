package com.jiyun.wanandroids.ui.home.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseActivtiy;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.ui.home.login.presenter.LoginPresenter;
import com.jiyun.wanandroids.ui.home.registration.activity.RegistrationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivtiy<LoginPresenter> {


    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.egistration)
    Button registration;
    @BindView(R.id.login_img)
    ImageView loginImg;
    @BindView(R.id.login_pwd_cb)
    CheckBox loginPwdCb;


    @Override
    protected void initView() {

        //登录的点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = loginName.getText().toString();
                String pwd = loginPwd.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(BaseApp.getContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
                } else {

                    if (pwd.isEmpty()) {
                        Toast.makeText(BaseApp.getContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BaseApp.getContext(), "登录唱功", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //注册的点击事件
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseApp.getApp(), RegistrationActivity.class));
            }
        });




        loginPwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //选择状态 显示明文--设置为可见的密码
                    loginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    //默认状态显示密码--设置文本 要一起写才能起作用  InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    loginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });



    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_img)
    public void onClick() {
        finish();
    }
}