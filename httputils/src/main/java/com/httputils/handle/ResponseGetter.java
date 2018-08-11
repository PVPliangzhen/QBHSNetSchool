package com.httputils.handle;


import com.httputils.HttpContent;
import com.httputils.HttpResponse;
import com.httputils.body.PostBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ResponseGetter {

    private static final String NULL_MAP_PRE = "NIL";
    public static final Object EMPTY_BODY = new Object();

    public static HttpResponse get(){
        return new HttpResponse();
    }


    public static final void fill(HttpResponse response
            , HttpContent content, int code, String message, Object body) {
        //System.out.println("ResponseGetter fill");
        if (response != null) {
            response.setCode(code);
            response.setMessage(message);
            response.setBody(body == null ? EMPTY_BODY : body);
            if (null != content) {
                if (content.extraCount() == 0) {
                    response.setBundles(Collections.<String, Object>emptyMap());
                } else {
                    fillExtras(content, response, content.extraCount());
                }
            }
        }


    }

    private static void fillExtras(HttpContent content, HttpResponse response, int count) {
        HashMap<String, Object> extra = new HashMap<>(
                roundUpToPowerOf2(count), 1
        );
        PostBody[] values = content.extras();
        for (int i = 0; i < count; i++) {
            extra.put(getBundleName(values[i]), values[i].value);
        }
        response.setBundles(Collections.unmodifiableMap(extra));
    }

    public static Object getExtrasValue(Map<String, Object> extras,String name,int valueType){
        if(extras==null||extras==Collections.<String, Object>emptyMap()){
            return EMPTY_BODY;
        }
        final Object value= extras.get(getBundleName(name,valueType));
        return value==null?EMPTY_BODY:value;

    }

    private static String getBundleName(PostBody value) {
        return getBundleName(value.name,value.type);
    }

    private static String getBundleName(String name,int type){
        return ((name==null?NULL_MAP_PRE:name)+ "_" + type);
    }

    private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        int rounded = (rounded = Integer.highestOneBit(number)) != 0
                ? (Integer.bitCount(number) > 1) ? rounded << 1 : rounded
                : 1;

        return rounded;
    }
}
