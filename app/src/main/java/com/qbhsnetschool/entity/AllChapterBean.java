package com.qbhsnetschool.entity;

import java.io.Serializable;

public class AllChapterBean implements Serializable{

    /**
     * id : 381
     * measure : 4
     * chapter_name : 全等三角形经典模型（1）
     * chapter_start_time : 2018-09-30T18:00:00+08:00
     * chapter_end_time : 2018-09-30T20:00:00+08:00
     * chapter_date : 2018.9.30 (周日)
     * chapter_time : 10:00－12:00
     * chapter_expire_time : 17
     * state : future
     * cc_vedio : {"userId":"AA31D2BB588429C7","roomId":null,"viewerName":"18701073115","token":"058129","recordId":null}
     * teacher : 李会平
     * is_live : false
     * user_id : 23443
     * has_upload_homework : false
     */

    private int id;
    private int measure;
    private String chapter_name;
    private String chapter_start_time;
    private String chapter_end_time;
    private String chapter_date;
    private String chapter_time;
    private int chapter_expire_time;
    private String state;
    private CcVedioBean cc_vedio;
    private String teacher;
    private boolean is_live;
    private int user_id;
    private boolean has_upload_homework;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getChapter_expire_time() {
        return chapter_expire_time;
    }

    public void setChapter_expire_time(int chapter_expire_time) {
        this.chapter_expire_time = chapter_expire_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CcVedioBean getCc_vedio() {
        return cc_vedio;
    }

    public void setCc_vedio(CcVedioBean cc_vedio) {
        this.cc_vedio = cc_vedio;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public boolean isIs_live() {
        return is_live;
    }

    public void setIs_live(boolean is_live) {
        this.is_live = is_live;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isHas_upload_homework() {
        return has_upload_homework;
    }

    public void setHas_upload_homework(boolean has_upload_homework) {
        this.has_upload_homework = has_upload_homework;
    }

    public static class CcVedioBean implements Serializable{
        /**
         * userId : AA31D2BB588429C7
         * roomId : null
         * viewerName : 18701073115
         * token : 058129
         * recordId : null
         */

        private String userId;
        private String roomId;
        private String viewerName;
        private String token;
        private String recordId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getViewerName() {
            return viewerName;
        }

        public void setViewerName(String viewerName) {
            this.viewerName = viewerName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }
    }
}
