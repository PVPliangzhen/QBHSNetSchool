package com.httputils.handle.okhttp3;


import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpRequest;
import com.httputils.HttpResponse;
import com.httputils.HttpUtils;
import com.httputils.factory.HttpExecutorFactory;
import com.httputils.factory.HttpRequestFactory;
import com.httputils.handle.HttpRequestTracker;
import com.httputils.handle.ResponseGetter;

/**
 * Created by Administrator on 2018/1/26.
 */

public class OKHttp3Utils extends HttpUtils {

    public final HttpRequestTracker requestTracker;
    private final HttpExecutorFactory executorFactory;


    public OKHttp3Utils(HttpRequestFactory requestFactory
            , HttpRequestTracker requestTracker, HttpExecutorFactory executorFactory) {
        super(requestFactory,HttpUtils.OK_HTTP_3);
        this.requestTracker = requestTracker;
        this.executorFactory = executorFactory;
    }

    @Override
    public HttpRequest request(HttpContent content, String httpMethod, Callback callback) {
        final HttpRequest request = requestFactory.newInstance(OK_HTTP_3);
        System.out.println("OKHttp3Utils request");
        requestTracker.beginRequest(request, content, ResponseGetter.get()
                , callback,executorFactory.getExecutor(OK_HTTP_3), OK_HTTP_3, true, httpMethod);
        return request;
    }


    @Override
    public HttpResponse request(HttpContent content, HttpRequest request) {
        HttpResponse response;
        requestTracker.beginRequest(request, content, response = ResponseGetter.get()
                , null ,executorFactory.getExecutor(OK_HTTP_3), OK_HTTP_3, false, null);
        return response;
    }
}
