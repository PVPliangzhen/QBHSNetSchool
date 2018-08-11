package com.httputils.handle;


import com.httputils.HttpRequest;

/**
 * Created by Administrator on 2018/1/26.
 */

public final class HttpCanceller implements HttpRequest {

    private HttpRequest referent;

    public HttpCanceller(HttpRequest request) {
        this.referent = request;
    }


    @Override
    public boolean cancel() {
        HttpRequest request;
        if (null != (request = get())) {
            return request.cancel();
        }
        return false;
    }

    @Override
    public boolean isCancel() {
        HttpRequest request;
        if (null != (request = get())) {
            return request.isCancel();
        }
        return true;
    }

    @Override
    public boolean recycle() {
        HttpRequest request;
        if (null != (request = get())) {
            return request.recycle();
        }
        return false;
    }

    @Override
    public boolean isRecycled() {
        HttpRequest request;
        if (null != (request = get())) {
            return request.isRecycled();
        }
        return true;
    }

    public final HttpRequest clear() {
        final HttpRequest old = referent;
        referent = null;
        return old;
    }

    private HttpRequest get() {
        return referent;
    }

}
