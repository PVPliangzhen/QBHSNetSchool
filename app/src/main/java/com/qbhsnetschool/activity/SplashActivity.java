package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, CourseDetailActivity.class));
            }
        });
        startActivity(new Intent(this, HomeActivity.class));
        //finish();
    }
}
