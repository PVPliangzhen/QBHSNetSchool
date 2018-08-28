package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qbhsnetschool.R;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;

import java.util.HashMap;

/**
 * created by liangzhen at 2018/8/10
 */
public class SplashActivity extends BaseActivity{

    private SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, WebActivity.class));
//                HashMap<String, String> params = new HashMap<>();
//                params.put("tel", "18701073115");
//                params.put("password", "111111");
//                params.put("tel_code", "215548");
//                HttpHelper.httpRequest(UrlHelper.nopasswords(), params, "POST", new StandardCallBack(activity) {
//                    @Override
//                    public void onSuccess(String result) {
//                        System.out.println(result);
//                    }
//                });
            }
        });
        startActivity(new Intent(this, HomeActivity.class));
        //finish();
    }
}
