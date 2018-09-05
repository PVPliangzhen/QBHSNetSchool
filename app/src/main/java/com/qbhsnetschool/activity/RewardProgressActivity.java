package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class RewardProgressActivity extends BaseActivity{

    private RewardProgressActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reward_progress, true, R.color.status_bar_bg_color, false);
        activity = this;
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("奖励进度");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        TextView release_times = (TextView) findViewById(R.id.release_times);
        release_times.setText(Html.fromHtml("领取次数还剩<font color =" + getResources().getColor(R.color.color_E20000) + "><big>0</big></font>次"));
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    activity.finish();
                    break;
            }
        }
    };
}
