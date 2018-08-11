package com.httputils.handle;


import com.httputils.HttpRequest;
import com.httputils.factory.HttpExecutorFactory;
import com.httputils.handle.impls.NativeHttpRequest;

/**
 * Created by Administrator on 2018/1/26.
 */

public class HttpRequestRecycler {
    private final HttpExecutorFactory executorFactory;
    private final HttpRequestTracker requestTracker;
    Node next;

    static class Node {
        HttpRequest request;
        Node next;

        public Node(HttpRequest request) {
            this.request = request;
        }
    }

    public HttpRequestRecycler(HttpExecutorFactory executorFactory
            , HttpRequestTracker requestTracker) {
        this.executorFactory = executorFactory;
        this.requestTracker = requestTracker;
    }


    public HttpRequest obtainRequest(String description, boolean handle) {
        synchronized (this) {
            if (next != null) {
                Node result = next;
                next = result.next;
                result.next = null;
                reset(result, description);
                return handle ? new HttpCanceller(result.request) : result.request;
            }
        }
        return new HttpCanceller(new NativeHttpRequest(requestTracker
                , executorFactory.getExecutor(description), this));
    }

    private void reset(Node node, String description) {
        if (null != node && node.request != null) {
            NativeHttpRequest request = (NativeHttpRequest) node.request;
            request.requestExecutor = executorFactory.getExecutor(description);
            request.requestTracker = requestTracker;
            request.owner = this;
            request.recycle=false;
        }
    }

    public boolean recycleRequest(HttpRequest request) {
        if (request == null) {
            return false;
        }
        HttpRequest realRequest = request;
        if (request instanceof HttpCanceller) {
            realRequest = ((HttpCanceller) request).clear();
        }
        boolean ret=false;
        if (realRequest != null) {
            if (realRequest instanceof NativeHttpRequest) {
                ret= ((NativeHttpRequest) request).nativeRecycle();
            }
            if (realRequest != null) {
                synchronized (this) {
                    Node nd = new Node(request);
                    nd.next = next;
                    next = nd;
                }
            }
        }
        return ret;
    }

}
