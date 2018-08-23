package com.qbhsnetschool.uitls;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static SparseArray<String> getGradeItemsPopUp(){
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

    public static List<String> getGrades(){
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

    public static Map<String, Integer> getGradeIndex(){
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
}