package com.qbhsnetschool.activity;

import android.os.Bundle;

import com.qbhsnetschool.R;

public class HomeActivity extends BaseActivity{

    private int[] img_tab_checked = new int[]{R.mipmap.ico_tab_course_nor,
            R.mipmap.ico_tab_study_nor, R.mipmap.ico_tab_me_nor};
    // tab未选中的图片
    private int[] img_tab_uncheck = new int[]{R.mipmap.ico_tab_course_pre,
            R.mipmap.ico_tab_study_pre, R.mipmap.ico_tab_me_pre};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
