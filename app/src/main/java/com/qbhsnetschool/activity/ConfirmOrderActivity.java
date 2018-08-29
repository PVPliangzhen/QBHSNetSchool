package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.CheapieBean;

public class ConfirmOrderActivity extends BaseActivity{

    private CheapieBean cheapieBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_confirm_order, false, R.color.status_bar_bg_color, false);
        initView();
        initIntent();
        initAddress();
    }

    private void initAddress() {

    }

    private void initIntent() {
        Intent intent = getIntent();
        cheapieBean = (CheapieBean) intent.getSerializableExtra("cheapieBean");
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("确认订单");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
