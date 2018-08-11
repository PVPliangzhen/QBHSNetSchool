package com.qbhsnetschool.test;

import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.UrlHelper;

import java.io.IOException;
import java.sql.SQLOutput;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestClient {

    public static void main(String args []){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = UrlHelper.messageVerify("18813155412");
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(response.body().string());
                    }
                });
            }
        }).start();
    }
}
