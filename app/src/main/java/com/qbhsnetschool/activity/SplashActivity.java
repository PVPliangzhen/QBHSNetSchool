package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;

import com.qbhsnetschool.R;

/**
 * created by liangzhen at 2018/8/10
 */
public class SplashActivity extends BaseActivity{

    private SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        startActivity(new Intent(this, HomeActivity.class));
    }
}
