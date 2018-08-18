package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.UIUtils;

public class LoginTrasitActivity extends BaseActivity{

    private LoginTrasitActivity activity;
    private ImageView login_transit_close;
    private Button to_register;
    private Button to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_login_transit, true, false);
        activity = this;
        RelativeLayout activity_login_transit_root = (RelativeLayout) findViewById(R.id.activity_login_transit_root);
        activity_login_transit_root.setPadding(0, UIUtils.getStatusBarHeight(activity), 0, 0);
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
                    Intent intent1 = new Intent(activity, LoginActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    };
}
