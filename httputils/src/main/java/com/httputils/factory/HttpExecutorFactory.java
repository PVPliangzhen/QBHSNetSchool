package com.httputils.factory;

import com.httputils.handle.HttpExecutor;

/**
 * Created by Administrator on 2018/1/26.
 */

public interface HttpExecutorFactory {

    HttpExecutor getExecutor(String description);
}
