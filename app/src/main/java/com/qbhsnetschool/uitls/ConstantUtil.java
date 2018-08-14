package com.qbhsnetschool.uitls;

import android.util.SparseArray;

public class ConstantUtil {

    public static SparseArray<String> getSanqiItems(){
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

    public static SparseArray<String> getGradeItems(){
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
}
