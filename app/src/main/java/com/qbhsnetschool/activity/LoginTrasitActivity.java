package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qbhsnetschool.R;

public class LoginTrasitActivity extends BaseActivity{

    private LoginTrasitActivity activity;
    private ImageView login_transit_close;
    private Button to_register;
    private Button to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_transit);
        activity = this;
        login_transit_close = (ImageView) findViewById(R.id.login_transit_close);
        login_transit_close.setOnClickListener(clickListener);
        to_register = (Button) findViewById(R.id.to_register);
        to_register.setOnClickListener(clickListener);
        to_login = (Button) findViewById(R.id.to_login);
        to_login.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_transit_close:
                    finish();
                    break;
                case R.id.to_register:
                    Intent intent = new Intent(activity, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.to_login:
                    break;
            }
        }
    };
}
