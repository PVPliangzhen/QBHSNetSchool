package com.qbhsnetschool.entity;

import java.io.Serializable;

public class CourseBean implements Serializable{

    /**
     * product_id : egzva390rnbepiw4f88x
     * detail_title : 初二年级直播培优班（人教版）
     * course_date : 9月9日-1月6日
     * chapter_times : 17次课34课时
     * chapter_lately : {"chapter_date":"2018.9.16 (周日)","chapter_time":"10:00－12:00","teacher":"李会平","chapter_name":"三角形的边和\u201c内部\u201d线段","chapter_expire_time":4,"is_live":false,"id":383,"measure":2,"has_upload_homework":false,"state":"future","cc_vedio":{"userId":"AA31D2BB588429C7","roomId":null,"viewerName":"18701073115","token":"939627","recordId":null}}
     * course_test_url : http://www.hualuogengshuxue.com/exam/m_begin_exam?exam=None&grade=8
     * couese_info : 共十七讲/已上1讲
     * teacher1 : {}
     * teacher2 : null
     * season : q
     * items : 13
     * title1 : 直播培优班（人教版）
     * title3 : 培优班2班（人教版）
     * course_sdate : 2018-09-09
     * course_edate : 2019-01-06
     * course_time : 周日10：00-12：00
     * exam_id : null
     */

    private String product_id;
    private String detail_title;
    private String course_date;
    private String chapter_times;
    private ChapterLatelyBean chapter_lately;
    private String course_test_url;
    private String couese_info;
    private Teacher1Bean teacher1;
    private Object teacher2;
    private String season;
    private int items;
    private String title1;
    private String title3;
    private String course_sdate;
    private String course_edate;
    private String course_time;
    private Object exam_id;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDetail_title() {
        return detail_title;
    }

    public void setDetail_title(String detail_title) {
        this.detail_title = detail_title;
    }

    public String getCourse_date() {
        return course_date;
    }

    public void setCourse_date(String course_date) {
        this.course_date = course_date;
    }

    public String getChapter_times() {
        return chapter_times;
    }

    public void setChapter_times(String chapter_times) {
        this.chapter_times = chapter_times;
    }

    public ChapterLatelyBean getChapter_lately() {
        return chapter_lately;
    }

    public void setChapter_lately(ChapterLatelyBean chapter_lately) {
        this.chapter_lately = chapter_lately;
    }

    public String getCourse_test_url() {
        return course_test_url;
    }

    public void setCourse_test_url(String course_test_url) {
        this.course_test_url = course_test_url;
    }

    public String getCouese_info() {
        return couese_info;
    }

    public void setCouese_info(String couese_info) {
        this.couese_info = couese_info;
    }

    public Teacher1Bean getTeacher1() {
        return teacher1;
    }

    public void setTeacher1(Teacher1Bean teacher1) {
        this.teacher1 = teacher1;
    }

    public Object getTeacher2() {
        return teacher2;
    }

    public void setTeacher2(Object teacher2) {
        this.teacher2 = teacher2;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getCourse_sdate() {
        return course_sdate;
    }

    public void setCourse_sdate(String course_sdate) {
        this.course_sdate = course_sdate;
    }

    public String getCourse_edate() {
        return course_edate;
    }

    public void setCourse_edate(String course_edate) {
        this.course_edate = course_edate;
    }

    public String getCourse_time() {
        return course_time;
    }

    public void setCourse_time(String course_time) {
        this.course_time = course_time;
    }

    public Object getExam_id() {
        return exam_id;
    }

    public void setExam_id(Object exam_id) {
        this.exam_id = exam_id;
    }

    public static class ChapterLatelyBean implements Serializable{
        /**
         * chapter_date : 2018.9.16 (周日)
         * chapter_time : 10:00－12:00
         * teacher : 李会平
         * chapter_name : 三角形的边和“内部”线段
         * chapter_expire_time : 4
         * is_live : false
         * id : 383
         * measure : 2
         * has_upload_homework : false
         * state : future
         * cc_vedio : {"userId":"AA31D2BB588429C7","roomId":null,"viewerName":"18701073115","token":"939627","recordId":null}
         */

        private String chapter_date;
        private String chapter_time;
        private String teacher;
        private String chapter_name;
        private int chapter_expire_time;
        private boolean is_live;
        private int id;
        private int measure;
        private boolean has_upload_homework;
        private String state;
        private CcVedioBean cc_vedio;

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

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public int getChapter_expire_time() {
            return chapter_expire_time;
        }

        public void setChapter_expire_time(int chapter_expire_time) {
            this.chapter_expire_time = chapter_expire_time;
        }

        public boolean isIs_live() {
            return is_live;
        }

        public void setIs_live(boolean is_live) {
            this.is_live = is_live;
        }

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

        public boolean isHas_upload_homework() {
            return has_upload_homework;
        }

        public void setHas_upload_homework(boolean has_upload_homework) {
            this.has_upload_homework = has_upload_homework;
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

        public static class CcVedioBean implements Serializable{
            /**
             * userId : AA31D2BB588429C7
             * roomId : null
             * viewerName : 18701073115
             * token : 939627
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

    public static class Teacher1Bean implements Serializable{
    }
}
