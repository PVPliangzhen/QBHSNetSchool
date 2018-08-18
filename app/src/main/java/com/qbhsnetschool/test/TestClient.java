package com.qbhsnetschool.test;

import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpResponse;
import com.httputils.HttpUtils;
import com.httputils.factory.HttpExecutorFactory;
import com.httputils.handle.HttpRequestRecycler;
import com.httputils.handle.HttpRequestTracker;
import com.httputils.handle.impls.NativeExecutorFactory;
import com.httputils.handle.impls.NativeRequestFactory;
import com.httputils.handle.okhttp3.OKHttp3Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.http.RealResponseBody;

public class TestClient {

    public static void main(String args []){
//        HttpExecutorFactory executorFactory= new NativeExecutorFactory();
//        HttpRequestTracker requestTracker=new HttpRequestTracker();
//        HttpUtils.setImpl(new OKHttp3Utils(new NativeRequestFactory(
//                new HttpRequestRecycler(executorFactory,requestTracker))
//                ,requestTracker,executorFactory));
//        HttpContent request;
//        request=new HttpContent.Builder("http://192.168.1.70:8888/app/nopasswords/")
//                .setConnectTimeout(TimeUnit.SECONDS, 3)
//                .setWriteTimeout(TimeUnit.SECONDS, 10)
//                .setReadTimeOut(TimeUnit.SECONDS, 10).build();
//        request.addFeature(HttpContent.F_PACKET,"only-params/json");
//        request.addFeature(HttpContent.F_BODY,"{*}");
//        HttpUtils.impl().request(request, "POST", new Callback() {
//            @Override
//            public void onResponse(HttpResponse response) {
//                System.out.println(response.code());
//                System.out.println(response.message());
//                try {
//                    System.out.println(((RealResponseBody)response.body()).string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        NorHttpContentBuilder requestBuilder = new NorHttpContentBuilder("http://192.168.1.17:8888/app/nopasswords");
//        final HttpContent request = requestBuilder
//                .setConnectTimeout(TimeUnit.SECONDS, 3)
//                .setReadTimeOut(TimeUnit.SECONDS, 10)
//                .setWriteTimeout(TimeUnit.SECONDS, 10)
//                .build();
//        requestBuilder.setParams(request, new PostParams(3).add("tel", "18515010456").add("password", "123456").add("tel_code", "532576"));
//        HttpUtils.impl().request(request, new Callback() {
//            @Override
//            public void onResponse(HttpResponse response) {
//                try {
//                    System.out.println(((RealResponseBody)response.body()).string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
