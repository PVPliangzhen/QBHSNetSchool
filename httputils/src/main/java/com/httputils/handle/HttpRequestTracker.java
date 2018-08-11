package com.httputils.handle;


import com.httputils.Callback;
import com.httputils.HttpContent;
import com.httputils.HttpRequest;
import com.httputils.HttpResponse;
import com.httputils.HttpUtils;
import com.httputils.body.HttpPost;

/**
 * Created by Administrator on 2018/1/24.
 */

public class HttpRequestTracker {


    public void beginRequest(HttpRequest request, HttpContent content, HttpResponse response
            , Callback callback, HttpExecutor executor, String description
            , boolean async, String httpMethod) {
        if (request.isCancel()) {
            ResponseGetter.fill(response, content, HttpResponse.CANCELED
                    , "Request Cancelled", null);
            return;
        }
        int flags = 0;
        if (description == HttpUtils.OK_HTTP_3) {
            flags |= HttpPost.OK_HTTP_3;
        }
        if (!async) flags |= HttpPost.REQ_ONE_WAY;
        executor.processRequest(new HttpPost(content, response, request, callback, flags), httpMethod);
    }

    public void cancelRequest(HttpRequest request, HttpExecutor executor) {
        if (request == null) {
            throw new IllegalStateException("request==null");
        }
        if (request.isRecycled()) {
            return;
        }
        if (null != executor && !request.isCancel()) {
            executor.cancelRequest(request);
        }
    }
}
