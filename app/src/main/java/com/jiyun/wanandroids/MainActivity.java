package com.jiyun.wanandroids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;

import com.jiyun.wanandroids.ui.home.activity.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);


          new CountDownTimer(1000,100){

                      @Override
                      public void onTick(long millisUntilFinished) {

                      }

                      @Override
                      public void onFinish() {
                          startActivity(new Intent(MainActivity.this, HomeActivity.class));
                          finish();
                      }
                  }.start();
    }
}
