package com.qbhsnetschool.entity;

public class AlreadyTestBean {

    /**
     * id : 991
     * title : 新初一摸底考试
     * wrong_items : 错题数： 2题
     * before_title : 2018－秋
     * items : 总题数： 5题
     * datetime : 2018年 8月10日
     * total_score : 6
     * exam_type : 2
     */

    private int id;
    private String title;
    private String wrong_items;
    private String before_title;
    private String items;
    private String datetime;
    private int total_score;
    private int exam_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrong_items() {
        return wrong_items;
    }

    public void setWrong_items(String wrong_items) {
        this.wrong_items = wrong_items;
    }

    public String getBefore_title() {
        return before_title;
    }

    public void setBefore_title(String before_title) {
        this.before_title = before_title;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public int getExam_type() {
        return exam_type;
    }

    public void setExam_type(int exam_type) {
        this.exam_type = exam_type;
    }
}
