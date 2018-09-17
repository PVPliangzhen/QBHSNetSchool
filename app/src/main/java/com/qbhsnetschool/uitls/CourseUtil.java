package com.qbhsnetschool.uitls;

import com.qbhsnetschool.entity.CourseBean;

import java.util.ArrayList;
import java.util.List;

public class CourseUtil {

    public static List<CourseBean> futureCourse = new ArrayList<>();
    public static List<CourseBean> pastCourse = new ArrayList<>();

    public static List<CourseBean> getFutureCourse() {
        return futureCourse;
    }

    public static void setFutureCourse(List<CourseBean> futureCourse) {
        CourseUtil.futureCourse = futureCourse;
    }

    public static List<CourseBean> getPastCourse() {
        return pastCourse;
    }

    public static void setPastCourse(List<CourseBean> pastCourse) {
        CourseUtil.pastCourse = pastCourse;
    }

    public static void clearData(){
        if (futureCourse != null) {
            futureCourse.clear();
        }
        if (pastCourse != null) {
            pastCourse.clear();
        }
    }
}
