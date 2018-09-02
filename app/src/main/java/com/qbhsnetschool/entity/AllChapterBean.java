package com.qbhsnetschool.entity;

public class AllChapterBean {

    /**
     * measure : 1
     * chapter_name : 代数的形式
     * chapter_start_time : 2018-09-09T03:00:00+08:00
     * chapter_end_time : 2018-09-09T05:00:00+08:00
     * chapter_date : 2018.9.8 (周六)
     * chapter_time : 19:00－21:00
     * chapter_expire_time : 17
     * state : future
     * cc_vedio : {"recordId":null,"roomId":null,"userId":"AA31D2BB588429C7","viewerName":"18352860817","token":"109475"}
     * teacher : 李会平
     */

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

    public static class CcVedioBean {
        /**
         * recordId : null
         * roomId : null
         * userId : AA31D2BB588429C7
         * viewerName : 18352860817
         * token : 109475
         */

        private Object recordId;
        private Object roomId;
        private String userId;
        private String viewerName;
        private String token;

        public Object getRecordId() {
            return recordId;
        }

        public void setRecordId(Object recordId) {
            this.recordId = recordId;
        }

        public Object getRoomId() {
            return roomId;
        }

        public void setRoomId(Object roomId) {
            this.roomId = roomId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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
    }
}
