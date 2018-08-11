package com.httputils.handle.okhttp3;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/1/30.
 */

public interface OkHttp3ClientProxy {

    OkHttpClient newClient(int writeTimeOut, int readTimeOut, int connectTimeOut
            , OkHttpClient oldClient);

}
