package com.httputils.body;


import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpRequest;
import com.httputils.HttpResponse;

/**
 * Created by Administrator on 2018/1/26.
 */

public final class HttpPost {

    public static final int OK_HTTP_3=0x10;
    public static final int REQ_ONE_WAY=1;

    public final HttpContent origContent;//the original HTTP request content
    public final HttpResponse response;// response for HTTP Requesting
    public final HttpRequest request;//self-defined Request,has no relationship with HTTP
    public final Callback callback;
    public final int flags;//HTTP request's flags

    public HttpPost(HttpContent content, HttpResponse response,
                    HttpRequest request, Callback callback, int flags) {
        this.origContent = content;
        this.response = response;
        this.request = request;
        this.callback = callback;
        this.flags = flags;
    }





}
