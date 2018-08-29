package com.qbhsnetschool.entity;

public class TestBean {

    /**
     * id : 21
     * title : 三年级摸底考试
     * start_time : 2018-08-09T18:39:00+08:00
     * end_time : 2018-08-31T18:39:00+08:00
     * item_no : 5
     * url : http://www.hualuogengshuxue.com/exam/m_begin_exam?exam=21&grade=3
     * before_title : 2018－暑
     * items : 总题数： 5题
     */

    private int id;
    private String title;
    private String start_time;
    private String end_time;
    private int item_no;
    private String url;
    private String before_title;
    private String items;

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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getItem_no() {
        return item_no;
    }

    public void setItem_no(int item_no) {
        this.item_no = item_no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
