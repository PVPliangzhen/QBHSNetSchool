package com.httputils.handle.impls;


import com.httputils.HttpRequest;
import com.httputils.handle.HttpExecutor;
import com.httputils.handle.HttpRequestRecycler;
import com.httputils.handle.HttpRequestTracker;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NativeHttpRequest implements HttpRequest {

    public HttpRequestTracker requestTracker;
    public HttpExecutor requestExecutor;
    public HttpRequestRecycler owner;

    private volatile boolean cancel = false;//是否已经取消
    public volatile boolean recycle = false;//是否已经回收掉

    public NativeHttpRequest(HttpRequestTracker tracker
            , HttpExecutor executor, HttpRequestRecycler o) {
        this.requestTracker = tracker;
        this.requestExecutor = executor;
        this.owner = o;
    }

    @Override
    public boolean cancel() {
        if (!cancel && null != requestTracker && null != requestExecutor) {
            requestTracker.cancelRequest(this, requestExecutor);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCancel() {
        return cancel;
    }

    @Override
    public boolean recycle() {
        if (owner == null)
            return nativeRecycle();
        return owner.recycleRequest(this);
    }

    @Override
    public boolean isRecycled() {
        return recycle;
    }

    public boolean nativeRecycle() {
        if (recycle) {
            return false;
        }
        this.requestExecutor = null;
        this.requestTracker = null;
        this.owner = null;
        cancel = false;
        recycle = true;
        return true;
    }
}
