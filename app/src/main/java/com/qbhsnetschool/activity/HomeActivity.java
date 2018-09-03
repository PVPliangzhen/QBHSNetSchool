package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.Pingpp;
import com.qbhsnetschool.R;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.fragment.CourseSelectionFragment;
import com.qbhsnetschool.fragment.LearnFragment;
import com.qbhsnetschool.fragment.MineFragment;
import com.qbhsnetschool.fragment.TestFragment;
import com.qbhsnetschool.uitls.ActivityStackUtils;
import com.qbhsnetschool.uitls.UIUtils;

import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private int[] img_tab_checked = new int[]{R.mipmap.icon_tab_choose_sel,
            R.mipmap.icon_tab_course_sel, R.mipmap.icon_tab_test_sel, R.mipmap.icon_tab_me_sel};
    private int[] img_tab_unchecked = new int[]{R.mipmap.icon_tab_choose_nor,
            R.mipmap.icon_tab_course_nor, R.mipmap.icon_tab_test_nor, R.mipmap.icon_tab_me_nor};
    private int position_change;

    private CourseSelectionFragment courseSelectionFragment;
    private LearnFragment learnFragment;
    private TestFragment testFragment;
    private MineFragment mineFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    private Fragment currentFragment;
    private List<ImageView> img_tab;
    private List<TextView> txt_tab;
    private HomeActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_home, false, R.color.status_bar_bg_color, false);
        baseContentView.setPadding(0, 0, 0, 0);
        activity = this;
        initView();
        initTab();
    }

    private void initView() {
        LinearLayout lne_tab_choose_course = (LinearLayout) findViewById(R.id.lne_tab_choose_course);
        lne_tab_choose_course.setOnClickListener(clickListener);
        LinearLayout lne_tab_learn = (LinearLayout) findViewById(R.id.lne_tab_learn);
        lne_tab_learn.setOnClickListener(clickListener);
        LinearLayout lne_tab_mine = (LinearLayout) findViewById(R.id.lne_tab_mine);
        lne_tab_mine.setOnClickListener(clickListener);
        LinearLayout lne_tab_test = (LinearLayout) findViewById(R.id.lne_tab_test);
        lne_tab_test.setOnClickListener(clickListener);
        img_tab = new LinkedList<>();
        img_tab.add((ImageView) findViewById(R.id.img_tab_choose_course));
        img_tab.add((ImageView) findViewById(R.id.img_tab_learn));
        img_tab.add((ImageView) findViewById(R.id.img_tab_test));
        img_tab.add((ImageView) findViewById(R.id.img_tab_mine));

        txt_tab = new LinkedList<>();
        txt_tab.add((TextView) findViewById(R.id.txt_tab_choose_course));
        txt_tab.add((TextView) findViewById(R.id.txt_tab_learn));
        txt_tab.add((TextView) findViewById(R.id.txt_tab_test));
        txt_tab.add((TextView) findViewById(R.id.txt_tab_mime));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String home_tab = intent.getStringExtra("home_tab");
        if (home_tab != null) {
            switch (home_tab) {
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent1 = new Intent(HomeActivity.this, LoginTrasitActivity.class);
                        startActivity(intent1);
                    } else {
                        clickMineTab();
                    }
                    break;
            }
        }
    }

    private void initTab() {
        position_change = 0;
        if (courseSelectionFragment == null) {
            courseSelectionFragment = new CourseSelectionFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content, courseSelectionFragment).commit();
        currentFragment = courseSelectionFragment;
    }

    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.layout_content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }

    public void clickCourseTab() {
        if (courseSelectionFragment == null) {
            courseSelectionFragment = new CourseSelectionFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), courseSelectionFragment);
        changeTabView(0);
        position_change = 0;
    }

    public void clickLearnTab() {
        if (learnFragment == null) {
            learnFragment = new LearnFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), learnFragment);
        changeTabView(1);
        position_change = 1;
    }

    public void clickTestTab() {
        if (testFragment == null) {
            testFragment = new TestFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), testFragment);
        changeTabView(2);
        position_change = 2;
    }

    public void clickMineTab() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mineFragment);
        changeTabView(3);
        position_change = 3;
    }

    private void changeTabView(int position_current) {
        if (position_current == position_change) {
            return;
        }
        img_tab.get(position_change).setImageResource(
                img_tab_unchecked[position_change]);
        txt_tab.get(position_change).setTextColor(
                getResources().getColor(R.color.color_333333));
        img_tab.get(position_current).setImageResource(
                img_tab_checked[position_current]);
        txt_tab.get(position_current).setTextColor(
                getResources().getColor(R.color.color_E20000));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lne_tab_choose_course:
                    clickCourseTab();
                    break;
                case R.id.lne_tab_learn:
                case R.id.img_tab_learn:
                case R.id.txt_tab_learn:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(HomeActivity.this, LoginTrasitActivity.class);
                        startActivity(intent);
                    } else {
                        clickLearnTab();
                    }
                    break;
                case R.id.lne_tab_test:
                case R.id.img_tab_test:
                case R.id.txt_tab_test:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(HomeActivity.this, LoginTrasitActivity.class);
                        startActivity(intent);
                    } else {
                        clickTestTab();
                    }
                    break;
                case R.id.lne_tab_mine:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(HomeActivity.this, LoginTrasitActivity.class);
                        startActivity(intent);
                    } else {
                        clickMineTab();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("-------------------------------------");
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
        }
    }

    private long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    Toast.makeText(activity, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    ActivityStackUtils.getInstance().clearAllActivity();
                    System.gc();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
