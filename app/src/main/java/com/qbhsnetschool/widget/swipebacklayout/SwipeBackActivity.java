package com.qbhsnetschool.widget.swipebacklayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class SwipeBackActivity extends AppCompatActivity implements
        SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        PreferenceUtils.setPrefInt(getApplicationContext(),
                "key_tracking_mode", SwipeBackLayout.EDGE_LEFT);
        getSwipeBackLayout().setEdgeSize(50);
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (state == 2) {
                    onSwipeFinish();
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {

            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public abstract void onSwipeFinish();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if ((v == null) && (mHelper != null)) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
