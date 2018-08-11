package com.qbhsnetschool.protocol;

import com.qbhsnetschool.BuildConfig;

public class UrlHelper {

    public static final class BaseUrl {
        public static String BASE_HTTP_URL = "online";

        static {
            if (BuildConfig.DEBUG) {
                BASE_HTTP_URL = "http://192.168.1.17:8888/";
            }
        }
    }

    public static String messageVerify(String tel){
        return BaseUrl.BASE_HTTP_URL + "app/tel_codes/" + tel + "/";
    }
}
