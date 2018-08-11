package com.qbhsnetschool.protocol;

import android.text.TextUtils;
import android.widget.Toast;

import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpResponse;
import com.httputils.HttpUtils;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.mvp.activity.SplashActivity;
import com.qbhsnetschool.uitls.EnvirUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.http.RealResponseBody;

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
