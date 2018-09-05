package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class ShareForComeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_share_for_come, false, R.color.status_bar_bg_color, false);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("转介绍活动");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
