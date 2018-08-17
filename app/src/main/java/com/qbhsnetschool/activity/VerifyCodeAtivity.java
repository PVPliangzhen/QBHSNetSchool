package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.UIUtils;

public class VerifyCodeAtivity extends BaseActivity{

    private String phonenumber;
    private TextView phone_number;
    private TextView count_timer_txt;
    private Button login_btn;
    private ImageView page_back;
    private EditText verify_code_edit;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private TextView txt6;
    private TextView [] textViews = new TextView [6];
    private ImageView pwd_show_img;
    private EditText pwd_content;

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
        verify_code_edit = (EditText) findViewById(R.id.verify_code_edit);
        pwd_show_img = (ImageView) findViewById(R.id.pwd_show_img);
        pwd_show_img.setOnClickListener(clickListener);
        pwd_content = (EditText) findViewById(R.id.pwd_content);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        textViews[0] = txt1;
        textViews[1] = txt2;
        textViews[2] = txt3;
        textViews[3] = txt4;
        textViews[4] = txt5;
        textViews[5] = txt6;

        verify_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String editNumber = "";

                if (editable.length() > 6){
                    return;
                }else{
                    editNumber = editable.toString() + " " + " " + " " + " " + " " + " ";
                }

                for(int i = 0; i < 6; i++){
                    textViews[i].setText(String.valueOf(editNumber.charAt(i)));
                }
            }
        });
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
                case R.id.pwd_show_img:
                    UIUtils.showPasswordExpress(pwd_content, pwd_show_img);
                    break;
            }
        }
    };
}
