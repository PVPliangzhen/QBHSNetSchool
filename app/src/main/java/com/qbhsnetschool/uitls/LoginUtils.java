package com.qbhsnetschool.uitls;

import android.content.Context;
import android.content.Intent;

public class LoginUtils {

    private static volatile LoginUtils loginUtils;
    private Context context;

    private LoginUtils(Context context){
        this.context = context;
    }

    public static LoginUtils getInstance(Context context){
        if (loginUtils == null){
            synchronized (LoginUtils.class){
                loginUtils = new LoginUtils(context);
            }
        }
        return loginUtils;
    }

    //我的页面 刷新用户信息
    public void refreshUserInfo(){
        Intent intent = new Intent();
        intent.setAction("fresh_user_after_login");
        context.sendBroadcast(intent);
    }

    public void refreshLearnFragmentData(){
        Intent intent = new Intent();
        intent.setAction("refresh_learn_fragment_data");
        context.sendBroadcast(intent);
    }

    public void refreshTestFragmentData(){
        Intent intent = new Intent();
        intent.setAction("refresh_test_fragment_data");
        context.sendBroadcast(intent);
    }
}
