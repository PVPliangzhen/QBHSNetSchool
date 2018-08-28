package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;

/**
 * Created by liangzhen on 2018/8/23.
 */

public class CourseDetailActivity extends BaseActivity{

    private LinearLayout sign_up_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_course_detail, false, R.color.status_bar_bg_color, false);
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("课程详情");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        sign_up_btn = (LinearLayout) findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.sign_up_btn:

                    break;

            }
        }
    };
}
