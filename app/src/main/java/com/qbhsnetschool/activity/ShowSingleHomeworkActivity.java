package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbhsnetschool.R;

import java.util.List;

public class ShowSingleHomeworkActivity extends BaseActivity{

    private Intent intent;
    private List<LocalMedia> selectList;
    private int select_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_show_single_homework, false, R.color.color_ffffff, true);
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView delete_btn = (ImageView) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView large_img = (ImageView) findViewById(R.id.large_img);

        intent = getIntent();
        selectList = intent.getParcelableArrayListExtra("select_list");
        select_position = intent.getIntExtra("select_position", 0);

        Glide.with(this).load(selectList.get(select_position).getPath()).asBitmap().into(large_img);
    }
}
