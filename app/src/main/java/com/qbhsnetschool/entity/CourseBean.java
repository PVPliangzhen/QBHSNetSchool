package com.qbhsnetschool.entity;

public class CourseBean {

    /**
     * product_id : 2n3n4n3n2n3n4nrnend3
     * detail_title : 二升三年级直播尖子班(全国适用)
     * course_date : 8月21日-8月28日
     * chapter_times : 9次课18课时
     * chapter_lately : {"chapter_name":"除法竖式","chapter_expire_time":0,"chapter_date":"2018.8.23 (周四)","chapter_time":"19:30－21:30","cc_vedio":{"recordId":null,"viewerName":"18352860820","roomId":null,"token":"298665","userId":"AA31D2BB588429C7"},"state":"future","is_live":false,"measure":3,"teacher":"彭薏霖"}
     * course_test_url : http://www.hualuogengshuxue.com/exam/m_begin_exam?exam=21&grade=3
     * couese_info : 共九讲/已上2讲
     * teacher1 : {"name":"彭薏霖"}
     * season : x
     * items : 4
     * title1 : 直播尖子班(全国适用)
     * title3 : 尖子班2班
     * course_sdate : 2018-08-21
     * course_edate : 2018-08-28
     * course_time : 每天19:30 - 21:30上课
     * exam_id : 21
     */

    private String product_id;
    private String detail_title;
    private String course_date;
    private String chapter_times;
    private ChapterLatelyBean chapter_lately;
    private String course_test_url;
    private String couese_info;
    private Teacher1Bean teacher1;
    private String season;
    private int items;
    private String title1;
    private String title3;
    private String course_sdate;
    private String course_edate;
    private String course_time;
    private int exam_id;

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

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public static class ChapterLatelyBean {
        /**
         * chapter_name : 除法竖式
         * chapter_expire_time : 0
         * chapter_date : 2018.8.23 (周四)
         * chapter_time : 19:30－21:30
         * cc_vedio : {"recordId":null,"viewerName":"18352860820","roomId":null,"token":"298665","userId":"AA31D2BB588429C7"}
         * state : future
         * is_live : false
         * measure : 3
         * teacher : 彭薏霖
         */

        private String chapter_name;
        private int chapter_expire_time;
        private String chapter_date;
        private String chapter_time;
        private CcVedioBean cc_vedio;
        private String state;
        private boolean is_live;
        private int measure;
        private String teacher;

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

        public CcVedioBean getCc_vedio() {
            return cc_vedio;
        }

        public void setCc_vedio(CcVedioBean cc_vedio) {
            this.cc_vedio = cc_vedio;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isIs_live() {
            return is_live;
        }

        public void setIs_live(boolean is_live) {
            this.is_live = is_live;
        }

        public int getMeasure() {
            return measure;
        }

        public void setMeasure(int measure) {
            this.measure = measure;
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
             * viewerName : 18352860820
             * roomId : null
             * token : 298665
             * userId : AA31D2BB588429C7
             */

            private Object recordId;
            private String viewerName;
            private Object roomId;
            private String token;
            private String userId;

            public Object getRecordId() {
                return recordId;
            }

            public void setRecordId(Object recordId) {
                this.recordId = recordId;
            }

            public String getViewerName() {
                return viewerName;
            }

            public void setViewerName(String viewerName) {
                this.viewerName = viewerName;
            }

            public Object getRoomId() {
                return roomId;
            }

            public void setRoomId(Object roomId) {
                this.roomId = roomId;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }

    public static class Teacher1Bean {
        /**
         * name : 彭薏霖
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
