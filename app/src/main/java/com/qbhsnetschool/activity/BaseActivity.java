package com.qbhsnetschool.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.ActivityStackUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.swipebacklayout.SwipeBackActivity;

/**
 * created by liangzhen at 2018/8/10
 */
public class BaseActivity extends SwipeBackActivity{

    public RelativeLayout baseContentView;
    private boolean isMIUI;
    private boolean isFLYME;
    private View lollipop_statusBar_mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isMIUI = UIUtils.isOverMIUIV6();
        isFLYME = UIUtils.isOverFlymeV5();
        ActivityStackUtils.getInstance().addActivity(this);
    }

    /**
     * 默认模式（可侧滑关闭，状态栏栏填充相同高度白色布局，状态栏图标黑色）
     *
     * @param resId 布局资源id
     */
    public void setBaseContentView(int resId) {
        setBaseContentView(resId, true);
    }

    /**
     * 自定义模式1（自定义是否可侧滑关闭，状态栏栏填充相同高度白色布局，状态栏图标黑色）
     *
     * @param canSwipeBack 是否可以策划关闭
     */
    public void setBaseContentView(int resId, boolean canSwipeBack) {
        setBaseContentView(resId, canSwipeBack, 0, true);
    }

    /**
     * 自定义模式2（自定义是否可侧滑关闭，状态栏默认透明并且不填充布局，自定义状态栏图标是否黑色）
     *
     * @param isLightStatusBar 状态栏图标是否黑色
     */
    public void setBaseContentView(int resId, boolean canSwipeBack, boolean isLightStatusBar) {
        setBaseContentView(resId, canSwipeBack, R.color.transparent, isLightStatusBar);
    }

    /**
     * 自定义模式3（自定义是否可侧滑关闭，状态栏填充布局的颜色，自定义状态栏图标是否黑色）
     *
     * @param color 状态栏需要填充的布局颜色
     */
    public void setBaseContentView(int resId, boolean canSwipeBack, int color, boolean isLightStatusBar) {
        getSwipeBackLayout().setEnableGesture(false);
        // 先把布局填充成一个view
        baseContentView = (RelativeLayout) LayoutInflater.from(this).inflate(
                R.layout.activity_base, null);
        View contentView = LayoutInflater.from(this).inflate(resId, baseContentView, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isFLYME) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (color != R.color.transparent) {
                    if (color == 0) {
                        baseContentView.setBackgroundResource(R.color.color_ffffff);
                    } else {
                        baseContentView.setBackgroundResource(color);
                    }
                }
                if (isLightStatusBar) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            } else {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                if (color != R.color.transparent) {
                    if (color != 0) {
                        baseContentView.setBackgroundResource(color);
                    } else {
                        baseContentView.setBackgroundResource(R.color.android_l_status_bac);
                    }
                }
            }
            if (isMIUI && isLightStatusBar) {
                UIUtils.MIUISetStatusBarLightMode(getWindow(), true);
            }
            addAndSetContentView(contentView, color != R.color.transparent);
            if (color == R.color.transparent && isLightStatusBar) {
                openLollipopStatusMask();
            }
        } else {
            baseContentView.addView(contentView);
            setContentView(baseContentView);
        }
    }

    private void addAndSetContentView(View contentView, boolean needFillView) {
        getWindow().setStatusBarColor(UIUtils.getColor(this, R.color.transparent));
        int statusBarHeight = UIUtils.getStatusBarHeight(this);
        if (needFillView) {
            baseContentView.setPadding(0, statusBarHeight, 0, 0);
            baseContentView.addView(contentView);
        } else {
            baseContentView.addView(contentView);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && !UIUtils.isOverMIUIV6()) {
                lollipop_statusBar_mask = new View(this);
                baseContentView.addView(lollipop_statusBar_mask);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lollipop_statusBar_mask.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = statusBarHeight;
                    layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                }
            }
        }
        setContentView(baseContentView);
    }

    public void openLollipopStatusMask() {
        if (lollipop_statusBar_mask != null) {
            lollipop_statusBar_mask.setBackgroundResource(R.color.android_l_status_tran);
        }
    }

    public void closeLollipopStatusMask() {
        if (lollipop_statusBar_mask != null) {
            lollipop_statusBar_mask.setBackgroundResource(R.color.transparent);
        }
    }

    public void openDarkStatusBarIconMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isFLYME) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            openLollipopStatusMask();
        }
        if (isMIUI) {
            UIUtils.MIUISetStatusBarLightMode(getWindow(), true);
        }

    }

    public void closeDarkStatusBarIconMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isFLYME) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
            closeLollipopStatusMask();
        }
        if (isMIUI) {
            UIUtils.MIUISetStatusBarLightMode(getWindow(), false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.getInstance().delActivity(this);
    }

    @Override
    public void onSwipeFinish() {}
}
