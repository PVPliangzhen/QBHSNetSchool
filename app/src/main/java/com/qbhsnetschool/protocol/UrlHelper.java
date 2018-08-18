package com.qbhsnetschool.protocol;

import com.qbhsnetschool.BuildConfig;

public class UrlHelper {

    public static final class BaseUrl {
        public static String BASE_HTTP_URL = "online";

        static {
            if (BuildConfig.DEBUG) {
                BASE_HTTP_URL = "http://192.168.1.70:8888/";
            }
        }
    }

    public static String messageVerify(String tel){
        return BaseUrl.BASE_HTTP_URL + "app/tel_codes/" + tel + "/";
    }

    public static String homePage(int grade){
        return BaseUrl.BASE_HTTP_URL + "app/courses/" + grade + "/";
    }

    public static String homeBanner(){
        return BaseUrl.BASE_HTTP_URL + "app/carousels/";
    }

    public static String registerUser(){
        return BaseUrl.BASE_HTTP_URL + "app/users";
    }

    public static String getVerifyCode(String phoneNumber){
        return BaseUrl.BASE_HTTP_URL + "app/tel_codes/" + phoneNumber + "/";
    }

    public static String isPhoneRegister(String phoneNumber){
        return BaseUrl.BASE_HTTP_URL + "app/tels/" +phoneNumber + "/";
    }

    public static String login(){
        return BaseUrl.BASE_HTTP_URL +"app/authorizations/";
    }
}
