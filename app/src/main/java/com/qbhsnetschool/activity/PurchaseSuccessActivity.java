package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class PurchaseSuccessActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_purchase_success, false, R.color.status_bar_bg_color, false);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("购买成功");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView watch_order = (TextView) findViewById(R.id.watch_order);
        TextView choose_course = (TextView) findViewById(R.id.choose_course);
        watch_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PurchaseSuccessActivity.this, MyOrderActivity.class);
                startActivity(intent);
            }
        });

        choose_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("home_tab", "0");
                intent.setClass(PurchaseSuccessActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
