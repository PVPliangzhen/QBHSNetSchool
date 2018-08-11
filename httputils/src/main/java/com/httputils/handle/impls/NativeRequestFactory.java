package com.httputils.handle.impls;


import com.httputils.HttpRequest;
import com.httputils.factory.HttpRequestFactory;
import com.httputils.handle.HttpRequestRecycler;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NativeRequestFactory implements HttpRequestFactory {


private final HttpRequestRecycler requestRecycler;

    public NativeRequestFactory(HttpRequestRecycler requestRecycler) {
        this.requestRecycler = requestRecycler;
    }

    @Override
    public HttpRequest newInstance(String description) {
        return requestRecycler.obtainRequest(description,true);
    }


}
