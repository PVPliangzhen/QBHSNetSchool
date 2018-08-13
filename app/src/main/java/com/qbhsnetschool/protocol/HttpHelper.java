package com.qbhsnetschool.protocol;

import android.text.TextUtils;

import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpUtils;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.uitls.EnvirUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpHelper {

    private static String appUserAgent;
    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 10;

    public static String getUserAgent(QBHSApplication context) {
        if (TextUtils.isEmpty(appUserAgent)) {
            appUserAgent = EnvirUtils.getAppVersionName(context) + '_'
                    + EnvirUtils.getAppVersionCode(context) +// App版本
                    "/Android" +// 手机系统平台
                    "/" + EnvirUtils.getPhoneSysVersion(context) +// 手机系统版本
                    "/" + EnvirUtils.getPhoneType(context);
        }
        return appUserAgent;
    }

    public static void httpRequest(String url, Map<String, String> params, String httpMethod, Callback callback){
        OkHttpBuilder requestBuilder = new OkHttpBuilder(url);
        final HttpContent request = requestBuilder
                .setConnectTimeout(TimeUnit.SECONDS, CONNECT_TIMEOUT)
                .setReadTimeOut(TimeUnit.SECONDS, READ_TIMEOUT)
                .setWriteTimeout(TimeUnit.SECONDS, WRITE_TIMEOUT)
                .build();
        requestBuilder.setParams(request, params);
        HttpUtils.impl().request(request, httpMethod, callback);
    }

    public static void httpGetRequest(String url, String httpMethod, Callback callback){
        OkHttpBuilder requestBuilder = new OkHttpBuilder(url);
        final HttpContent request = requestBuilder
                .setConnectTimeout(TimeUnit.SECONDS, CONNECT_TIMEOUT)
                .setReadTimeOut(TimeUnit.SECONDS, READ_TIMEOUT)
                .setWriteTimeout(TimeUnit.SECONDS, WRITE_TIMEOUT)
                .build();
        HttpUtils.impl().request(request, httpMethod, callback);
    }
}
