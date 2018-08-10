package com.qbhsnetschool.mvp.activity;

import android.os.Bundle;

import com.qbhsnetschool.uitls.ActivityStackUtils;
import com.qbhsnetschool.widget.swipebacklayout.SwipeBackActivity;

/**
 * created by liangzhen at 2018/8/10
 */
public class BaseActivity extends SwipeBackActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtils.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.getInstance().delActivity(this);
    }

    @Override
    public void onSwipeFinish() {}
}
