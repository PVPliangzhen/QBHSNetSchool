package com.qbhsnetschool.uitls;

import android.content.Context;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qbhsnetschool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantUtil {

    public static final String data = "{    \"id\": \"ch_f5GaPO9WDePCSKurPGyr5i1C\",    \"object\": \"charge\",    \"created\": 1535200696,    \"livemode\": true,    \"paid\": false,    \"refunded\": false,    \"reversed\": false,    \"app\": \"app_1azb18LK84u5iDSW\",    \"channel\": \"wx\",    \"order_no\": \"16b7844216ce09a4\",    \"client_ip\": \"222.128.11.87\",    \"amount\": 1,    \"amount_settle\": 1,    \"currency\": \"cny\",    \"subject\": \"测试订单 by demo 20180825-203815\",    \"body\": \"Your Body - 订单详情\",    \"extra\": {},    \"time_paid\": null,    \"time_expire\": 1535200995,    \"time_settle\": null,    \"transaction_no\": null,    \"refunds\": {        \"object\": \"list\",        \"url\": \"/v1/charges/ch_f5GaPO9WDePCSKurPGyr5i1C/refunds\",        \"has_more\": false,        \"data\": []    },    \"amount_refunded\": 0,    \"failure_code\": null,    \"failure_msg\": null,    \"metadata\": {        \"ori_channel\": \"wx\"    },    \"credential\": {        \"object\": \"credential\",        \"wx\": {            \"appId\": \"wx3eba2286c6acb2b6\",            \"partnerId\": \"1250015001\",            \"prepayId\": \"wx2520381628286276eacc1ef10504528232\",            \"nonceStr\": \"6c9df93ae742f61c64d1fb6f187b1e90\",            \"timeStamp\": 1535200696,            \"packageValue\": \"Sign=WXPay\",            \"sign\": \"921D6897F3B597067BE7CE22001225E8\"        }    },    \"description\": null}";

    public static SparseArray<String> getSanqiItems() {
        SparseArray<String> sanqiItems = new SparseArray<>(13);
        sanqiItems.put(1, "第一期");
        sanqiItems.put(2, "第二期");
        sanqiItems.put(3, "第三期");
        sanqiItems.put(4, "第四期");
        sanqiItems.put(5, "系统班");
        sanqiItems.put(6, "专题课");
        sanqiItems.put(7, "星期一");
        sanqiItems.put(8, "星期二");
        sanqiItems.put(9, "星期三");
        sanqiItems.put(10, "星期四");
        sanqiItems.put(11, "星期五");
        sanqiItems.put(12, "星期六");
        sanqiItems.put(13, "星期天");
        return sanqiItems;
    }

    public static SparseArray<String> getGradeItems() {
        SparseArray<String> gradeItems = new SparseArray<>(12);
        gradeItems.put(1, "一年级");
        gradeItems.put(2, "二年级");
        gradeItems.put(3, "三年级");
        gradeItems.put(4, "四年级");
        gradeItems.put(5, "五年级");
        gradeItems.put(6, "六年级");
        gradeItems.put(7, "初一年级");
        gradeItems.put(8, "初二年级");
        gradeItems.put(9, "初三年级");
        gradeItems.put(10, "高一");
        gradeItems.put(11, "高二");
        gradeItems.put(12, "高三");
        return gradeItems;
    }

    public static SparseArray<String> getGradeItemsPopUp() {
        SparseArray<String> gradeItems = new SparseArray<>(12);
        gradeItems.put(1, "一年级");
        gradeItems.put(2, "二年级");
        gradeItems.put(3, "三年级");
        gradeItems.put(4, "四年级");
        gradeItems.put(5, "五年级");
        gradeItems.put(6, "六年级");
        gradeItems.put(7, "初一");
        gradeItems.put(8, "初二");
        gradeItems.put(9, "初三");
        gradeItems.put(10, "高一");
        gradeItems.put(11, "高二");
        gradeItems.put(12, "高三");
        return gradeItems;
    }

    public static List<String> getGrades() {
        List<String> grades = new ArrayList<>(12);
        grades.add("一年级");
        grades.add("二年级");
        grades.add("三年级");
        grades.add("四年级");
        grades.add("五年级");
        grades.add("六年级");
        grades.add("初一");
        grades.add("初二");
        grades.add("初三");
        grades.add("高一");
        grades.add("高二");
        grades.add("高三");
        return grades;
    }

    public static Map<String, Integer> getGradeIndex() {
        Map<String, Integer> gradeIndexs = new HashMap<>(12);
        gradeIndexs.put("一年级", 1);
        gradeIndexs.put("二年级", 2);
        gradeIndexs.put("三年级", 3);
        gradeIndexs.put("四年级", 4);
        gradeIndexs.put("五年级", 5);
        gradeIndexs.put("六年级", 6);
        gradeIndexs.put("初一", 7);
        gradeIndexs.put("初二", 8);
        gradeIndexs.put("初三", 9);
        gradeIndexs.put("高一", 10);
        gradeIndexs.put("高二", 11);
        gradeIndexs.put("高三", 12);
        return gradeIndexs;
    }

    public static void handleSeason(Context context, String season, ImageView imageView, boolean isLargeImage) {
        switch (season) {
            case "c":
                if (isLargeImage){
                    Glide.with(context).load(R.mipmap.spring2).asBitmap().into(imageView);
                }else{
                    Glide.with(context).load(R.mipmap.spring).asBitmap().into(imageView);
                }
                break;
            case "x":
                if (isLargeImage){
                    Glide.with(context).load(R.mipmap.summer2).asBitmap().into(imageView);
                }else{
                    Glide.with(context).load(R.mipmap.summer).asBitmap().into(imageView);
                }
                break;
            case "q":
                if (isLargeImage){
                    Glide.with(context).load(R.mipmap.fall2).asBitmap().into(imageView);
                }else{
                    Glide.with(context).load(R.mipmap.fall).asBitmap().into(imageView);
                }
                break;
            case "d":
                if (isLargeImage){
                    Glide.with(context).load(R.mipmap.winter2).asBitmap().into(imageView);
                }else{
                    Glide.with(context).load(R.mipmap.winter).asBitmap().into(imageView);
                }
                break;
            default:
                break;
        }
    }

    public static Map<String, Integer> handleScore(){
        Map<String, Integer> scoreMap = new HashMap<>(10);
        scoreMap.put("0", R.mipmap.g0);
        scoreMap.put("1", R.mipmap.g1);
        scoreMap.put("2", R.mipmap.g2);
        scoreMap.put("3", R.mipmap.g3);
        scoreMap.put("4", R.mipmap.g4);
        scoreMap.put("5", R.mipmap.g5);
        scoreMap.put("6", R.mipmap.g6);
        scoreMap.put("7", R.mipmap.g7);
        scoreMap.put("8", R.mipmap.g8);
        scoreMap.put("9", R.mipmap.g9);
        return scoreMap;
    }
}