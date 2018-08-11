package com.httputils.execption;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/31.
 */

public class WriteTimeOutException extends IOException {

    public WriteTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
