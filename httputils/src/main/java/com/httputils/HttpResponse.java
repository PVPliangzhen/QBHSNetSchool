package com.httputils;

import com.httputils.body.PostBody;
import com.httputils.handle.ResponseGetter;

import java.util.Map;

public class HttpResponse {


    public static final int CANCELED = 10001;
    public static final int RECYCLED = 10002;

    private static final String EMPTY_STR = "";
    protected int code;
    private String message;
    private Object body;
    private Map<String, Object> bundles;
    private int modCount = 0;

    public HttpResponse() {

    }

    public HttpResponse(HttpResponse orig) {
        if (orig != null) {
            this.code = orig.code;
            this.message = orig.message;
            this.body = orig.body;
            this.bundles = orig.bundles;
            this.modCount = orig.modCount;
        }
    }


    public int code() {
        return code;
    }

    public void setCode(int code) {
        if (modCount == 0) {
            this.code = code;
        }
        modCount++;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public void setBundles(Map<String, Object> bundles) {
        this.bundles = bundles;
    }

    public Object getTag() {
        return getTag(null);
    }

    public Object getTag(String tag) {
        return ResponseGetter.getExtrasValue(bundles, tag, PostBody.EXTRA);
    }

    public Object getParams(String key) {
        return ResponseGetter.getExtrasValue(bundles, key, PostBody.PARAM);
    }

    public boolean isSuccessful() {
        return code == 0;
    }

    public String message() {
        return (null == message ? EMPTY_STR : message);
    }

    public Object body() {
        return body;
    }


}
