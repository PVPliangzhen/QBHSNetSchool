package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.SingleHomeworkAdapter;
import com.qbhsnetschool.entity.HomeworkImgBean;

import java.util.List;

public class ShowSingleHomeworkActivity extends BaseActivity{

    private Intent intent;
    private List<HomeworkImgBean> homeworkImgBeans;
    private int select_position;
    private int position;
    private ViewPager single_img_vp;
    private SingleHomeworkAdapter singleHomeworkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_show_single_homework, false, R.color.status_bar_bg_color, true);
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intent = getIntent();
        homeworkImgBeans = (List<HomeworkImgBean>) intent.getSerializableExtra("homeworkImgBeans");
        position = intent.getIntExtra("select_position", 0);
        single_img_vp = (ViewPager) findViewById(R.id.single_img_vp);
        single_img_vp.setOffscreenPageLimit(3);
        singleHomeworkAdapter = new SingleHomeworkAdapter(ShowSingleHomeworkActivity.this);
        singleHomeworkAdapter.setData(homeworkImgBeans);
        single_img_vp.setAdapter(singleHomeworkAdapter);
        single_img_vp.setCurrentItem(position);
    }
}
