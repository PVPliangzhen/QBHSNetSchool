package com.qbhsnetschool.entity;

public class ChapterBean {

    /**
     * measure : 1
     * chapter_name : 加减巧算
     * chapter_start_time : 2018-08-22T03:30:00+08:00
     * chapter_end_time : 2018-08-22T05:30:00+08:00
     * chapter_date : 2018.8.21 (周二)
     * chapter_time : 19:30－21:30
     */

    private int measure;
    private String chapter_name;
    private String chapter_start_time;
    private String chapter_end_time;
    private String chapter_date;
    private String chapter_time;

    public int getMeasure() {
        return measure;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_start_time() {
        return chapter_start_time;
    }

    public void setChapter_start_time(String chapter_start_time) {
        this.chapter_start_time = chapter_start_time;
    }

    public String getChapter_end_time() {
        return chapter_end_time;
    }

    public void setChapter_end_time(String chapter_end_time) {
        this.chapter_end_time = chapter_end_time;
    }

    public String getChapter_date() {
        return chapter_date;
    }

    public void setChapter_date(String chapter_date) {
        this.chapter_date = chapter_date;
    }

    public String getChapter_time() {
        return chapter_time;
    }

    public void setChapter_time(String chapter_time) {
        this.chapter_time = chapter_time;
    }
}
