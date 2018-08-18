package com.qbhsnetschool.protocol;

import android.text.TextUtils;

import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpUtils;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.uitls.EnvirUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

    public static String doCookiePost(String url,
                                      Map<String, String> params) {
        String result = null;
        HttpURLConnection conn = null;
        URL urlCoon;
        try {
            urlCoon = new URL(url);
            conn = (HttpURLConnection) urlCoon.openConnection();
            conn.setRequestProperty("Accept-Encoding", "*");
            // String cookie =
            // SpUtils.getInstance(AppContext.mContext).get("Cookie", "");
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            try {
                conn.connect();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            }

            if (params != null) {
                StringBuilder par = new StringBuilder();
                Set<String> keys = params.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                    String key = i.next();
                    String value = params.get(key);
                    if (i.hasNext()) {
                        par.append(key).append("=").append(value).append("&");
                    } else {
                        par.append(key).append("=").append(value);
                    }
                }
                byte[] bytes = par.toString().getBytes();

                conn.getOutputStream().write(bytes);
            }
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream inputStream = conn.getInputStream();
            result = convertStreamToString(inputStream);
        } catch (IOException e) {
            // 网络异常
            result = null;
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect(); // 中断连接
            }
        }
        return result;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
