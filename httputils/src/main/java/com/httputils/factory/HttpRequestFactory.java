package com.httputils.factory;

import com.httputils.HttpRequest;

/**
 * Created by Administrator on 2018/1/26.
 */

public interface HttpRequestFactory {

    HttpRequest newInstance(String description);
}
