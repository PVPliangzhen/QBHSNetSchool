package com.httputils;

import com.httputils.factory.HttpExecutorFactory;
import com.httputils.factory.HttpRequestFactory;
import com.httputils.handle.HttpRequestRecycler;
import com.httputils.handle.HttpRequestTracker;
import com.httputils.handle.impls.NativeExecutorFactory;
import com.httputils.handle.impls.NativeRequestFactory;
import com.httputils.handle.okhttp3.OKHttp3Utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Http Request Utils
 * Created by Administrator on 2018/1/26.
 */

public abstract class HttpUtils {

    public static final String OK_HTTP_3 = "OkHttp3";

    private static HttpUtils httpUtilsImpl;


    public static void setImpl(HttpUtils utils) {
        httpUtilsImpl = utils;
    }

    public static HttpUtils impl() {
        if (httpUtilsImpl == null) {
            HttpExecutorFactory executorFactory = new NativeExecutorFactory();
            HttpRequestTracker requestTracker = new HttpRequestTracker();
            HttpUtils.setImpl(new OKHttp3Utils(new NativeRequestFactory(
                    new HttpRequestRecycler(executorFactory, requestTracker))
                    , requestTracker, executorFactory));
        }
        return httpUtilsImpl;
    }


    private final String description;
    protected HttpRequestFactory requestFactory;

    public HttpUtils(HttpRequestFactory requestFactory, String description) {
        this.requestFactory = requestFactory;
        this.description = description;
    }

    public abstract HttpRequest request(HttpContent content, String httpMethod, Callback callback);

    public abstract HttpResponse request(HttpContent content, HttpRequest request);

    public final HttpRequest newRequest() {
        return requestFactory().newInstance(description);
    }

    public HttpRequestFactory requestFactory() {
        return requestFactory;
    }


}
