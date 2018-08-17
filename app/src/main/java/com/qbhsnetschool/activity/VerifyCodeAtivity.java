package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class VerifyCodeAtivity extends BaseActivity{

    private String phonenumber;
    private TextView phone_number;
    private TextView count_timer_txt;
    private Button login_btn;
    private ImageView page_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        initView();
    }

    private void initView() {
        phonenumber = getIntent().getStringExtra("phonenumber");
        phone_number = (TextView) findViewById(R.id.phone_number);
        phone_number.setText(phonenumber);
        count_timer_txt = (TextView) findViewById(R.id.count_timer_txt);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(clickListener);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("获取验证码");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        countDownTimer.start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(60 *1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            count_timer_txt.setText(millisUntilFinished / 1000 + "s后重新发送");
        }

        @Override
        public void onFinish() {
            count_timer_txt.setText("请重新获取验证码");
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_btn:
                    break;
                case R.id.page_back:
                    finish();
                    break;
            }
        }
    };
}
