package com.httputils;

import com.httputils.factory.HttpExecutorFactory;
import com.httputils.factory.HttpRequestFactory;
import com.httputils.handle.HttpRequestRecycler;
import com.httputils.handle.HttpRequestTracker;
import com.httputils.handle.impls.NativeExecutorFactory;
import com.httputils.handle.impls.NativeRequestFactory;
import com.httputils.handle.okhttp3.OKHttp3Utils;
import com.httputils.utils.DeviceId;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int a=1;
        int b=0;
        if(a==1&&(b=1)==1){
            System.out.println("11111");
        }
//        assertEquals(4,2+2);
    }

    @Test
    public void httpRequestTest() throws Exception{
        HttpExecutorFactory executorFactory= new NativeExecutorFactory();
        HttpRequestTracker requestTracker=new HttpRequestTracker();
        HttpUtils.setImpl(new OKHttp3Utils(new NativeRequestFactory(
                new HttpRequestRecycler(executorFactory,requestTracker))
                ,requestTracker,executorFactory));
        HttpContent request;
        request=new HttpContent.Builder("http://test.cooolar.com:8080")
                .setConnectTimeout(TimeUnit.SECONDS, 3)
                .setWriteTimeout(TimeUnit.SECONDS, 10)
                .setReadTimeOut(TimeUnit.SECONDS, 10).build();
        request.addFeature(HttpContent.F_PACKET,"only-params/json");
        request.addFeature(HttpContent.F_BODY,"{*}");
        request.addParameter("\"module\"","\"Other\"");
        request.addParameter("\"cmd\"","\"Nothing\"");
        request.addParameter("\"deviceId\"","\"0r8r02r808053253255\"");
        request.addParameter("\"reqId\"","\"02020334\"");
        request.addParameter("\"ver\"","\"0.9.1\"");
        request.addParameter("\"para\"","\"{}\"");
        HttpUtils.impl().request(request, new Callback() {
            @Override
            public void onResponse(HttpResponse response) {
                System.out.println(response.code());
                System.out.println(response.message());
                try {
                    System.out.println(((RealResponseBody)response.body()).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


//        Response response=new OkHttpClient().newCall(new Request.Builder().url("http://test.cooolar.com:8080")
//                .post(RequestBody.create( MediaType.parse("application/x-www-form-urlencoded")
//                        ,"{\"module\":\"Other\",\"cmd\":\"Nothing\",\"deviceId\":\"0r8r02r808053253255\",\"reqId\":\"02020334\",\"ver\":\"0.9.1\",\"para\":\"{}\"}")).build())
//        .execute();
//        System.out.println(response.body().string());
    }

    @Test
    public void newThread() throws  Exception{

    }


}