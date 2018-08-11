package com.httputils.handle.impls;


import com.httputils.HttpUtils;
import com.httputils.factory.HttpExecutorFactory;
import com.httputils.handle.HttpExecutor;
import com.httputils.handle.okhttp3.OkHttp3Executor;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/1/30.
 */

public class NativeExecutorFactory implements HttpExecutorFactory {

    private final HashMap<String,HttpExecutor> cachedExecutors=new HashMap<>();


    @Override
    public HttpExecutor getExecutor(String description) {
        HttpExecutor executor=null;
        if(cachedExecutors.containsKey(description)&&(executor=cachedExecutors.get(description))!=null){
            return executor;
        }
        switch (description){
            case HttpUtils.OK_HTTP_3:
                executor=new OkHttp3Executor();
                break;
        }
        return executor;
    }
}
