package com.jiyun.wanandroids.ui.home.registration.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseActivtiy;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.ui.home.registration.presenter.RegistrationPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends BaseActivtiy<RegistrationPresenter> {


    @BindView(R.id.registration_phone)
    EditText registrationPhone;
    @BindView(R.id.registration_pwd1)
    EditText registrationPwd1;
    @BindView(R.id.registration_pwd2)
    EditText registrationPwd2;
    @BindView(R.id.registration)
    Button registration;
    @BindView(R.id.registration_img)
    ImageView registrationImg;

    @Override
    protected void initView() {

    }

    @Override
    protected RegistrationPresenter createPresenter() {
        return new RegistrationPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.registration_img, R.id.registration})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registration_img:
                finish();
                break;
            case R.id.registration:
                String phone = registrationPhone.getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(BaseApp.getContext(), "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    if (phone.matches("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$")) {
                        String pwd1 = registrationPwd1.getText().toString();
                        String pwd2 = registrationPwd2.getText().toString();
                        if (pwd1.isEmpty()) {
                            Toast.makeText(BaseApp.getContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                        } else {
                            if (pwd2.isEmpty()) {
                                Toast.makeText(BaseApp.getContext(), "重复密码不能为空", Toast.LENGTH_LONG).show();
                            } else {
                                if (pwd1.equals(pwd2)) {
                                    Toast.makeText(BaseApp.getContext(), "注册成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(BaseApp.getContext(), "两次密码不一致", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(BaseApp.getContext(), "手机号格式不正确", Toast.LENGTH_LONG).show();
                        Toast.makeText(BaseApp.getContext(), phone, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
