package com.qbhsnetschool.protocol;

import com.qbhsnetschool.BuildConfig;

public class UrlHelper {

    public static final class BaseUrl {
        public static String BASE_HTTP_URL = "http://b.hualuogengshuxue.com/";

        static {
            if (BuildConfig.DEBUG) {
                BASE_HTTP_URL = "http://192.168.1.172:8888/";
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
        return BaseUrl.BASE_HTTP_URL + "app/users/";
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

    public static String getPersonalInfo(){
        return BaseUrl.BASE_HTTP_URL + "app/user/";
    }

    public static String myCourses(){
        return BaseUrl.BASE_HTTP_URL + "app/mycourses/";
    }

    public static String getCoupons(){
        return BaseUrl.BASE_HTTP_URL + "app/coupons/";
    }

    public static String getOrders(){
        return BaseUrl.BASE_HTTP_URL + "app/myorders/";
    }

    public static String getChapters(String product_id){
        return BaseUrl.BASE_HTTP_URL + "app/chapters/" + product_id + "/";
    }

    public static String nopasswords(){
        return BaseUrl.BASE_HTTP_URL + "app/nopasswords/";
    }

    public static String getAddresses(){
        return BaseUrl.BASE_HTTP_URL + "app/addresses/";
    }

    public static String getOrderCoupon(){
        return BaseUrl.BASE_HTTP_URL + "app/coupon/";
    }

    public static String createOrder(){
        return BaseUrl.BASE_HTTP_URL + "app/orders/";
    }

    public static String getWaitExam(){
        return BaseUrl.BASE_HTTP_URL + "app/exams/";
    }

    public static String getAlreadyExam(){
        return BaseUrl.BASE_HTTP_URL + "app/exam_results/";
    }

    public static String upLoadHeads(){
        return BaseUrl.BASE_HTTP_URL + "app/headpics/";
    }

    public static String addAddress(){
        return BaseUrl.BASE_HTTP_URL + "app/addresses/";
    }

    public static String modifyAddress(int addressId){
        return BaseUrl.BASE_HTTP_URL + "app/addresses/" + addressId +"/";
    }

    public static String deleteAddress(int addressId){
        return BaseUrl.BASE_HTTP_URL + "app/addresses/" + addressId + "/";
    }
    public static String getAllChapters(String courseId){
        return BaseUrl.BASE_HTTP_URL + "app/mychapters/" +courseId + "/";
    }

    public static String checkOrder(String pingId){
        return BaseUrl.BASE_HTTP_URL + "app/order/" + pingId + "/";
    }

    public static String cancelOrder(String order_no){
        return BaseUrl.BASE_HTTP_URL + "app/cancle_orders/" + order_no + "/";
    }

    public static String submitHomework(){
        return BaseUrl.BASE_HTTP_URL + "app/homeworks/";
    }
}
