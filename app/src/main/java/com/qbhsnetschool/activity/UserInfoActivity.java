package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class UserInfoActivity extends BaseActivity{

    private ImageView page_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_user_info, false, R.color.status_bar_bg_color, false);
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("基本信息");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
            }
        }
    };
}
