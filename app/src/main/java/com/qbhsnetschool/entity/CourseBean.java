package com.qbhsnetschool.entity;

import java.io.Serializable;

public class CourseBean implements Serializable{

    /**
     * product_id : urx6ydwcipvknbo57flt
     * detail_title : 四年级直播尖子班(全国适用)
     * course_date : 9月8日-1月5日
     * chapter_times : 17次课34课时
     * chapter_lately : {"chapter_date":"2018.9.22 (周六)","chapter_time":"14:00－16:00","teacher":"张永福","chapter_name":"幻方","chapter_expire_time":3,"is_live":false,"id":186,"measure":3,"has_upload_homework":true,"state":"future","cc_vedio":{"userId":"AA31D2BB588429C7","roomId":"EDE07B96161FDC789C33DC5901307461","viewerName":"15801163287","token":"586545","recordId":null}}
     * course_test_url : http://www.hualuogengshuxue.com/exam/m_begin_exam?exam=22&grade=4
     * couese_info : 共十七讲/已上2讲
     * teacher1 : {"name":"张永福"}
     * teacher2 : null
     * grade : 4
     * season : q
     * items : 12
     * title1 : 直播尖子班(全国适用)
     * title3 : 尖子班1班
     * course_sdate : 2018-09-08
     * course_edate : 2019-01-05
     * course_time : 周六14：00-16：00
     * exam_id : 22
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
    private int grade;
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

    public Object getTeacher2() {
        return teacher2;
    }

    public void setTeacher2(Object teacher2) {
        this.teacher2 = teacher2;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public static class ChapterLatelyBean implements Serializable{
        /**
         * chapter_date : 2018.9.22 (周六)
         * chapter_time : 14:00－16:00
         * teacher : 张永福
         * chapter_name : 幻方
         * chapter_expire_time : 3
         * is_live : false
         * id : 186
         * measure : 3
         * has_upload_homework : true
         * state : future
         * cc_vedio : {"userId":"AA31D2BB588429C7","roomId":"EDE07B96161FDC789C33DC5901307461","viewerName":"15801163287","token":"586545","recordId":null}
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
             * roomId : EDE07B96161FDC789C33DC5901307461
             * viewerName : 15801163287
             * token : 586545
             * recordId : null
             */

            private String userId;
            private String roomId;
            private String viewerName;
            private String token;
            private Object recordId;

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

            public Object getRecordId() {
                return recordId;
            }

            public void setRecordId(Object recordId) {
                this.recordId = recordId;
            }
        }
    }

    public static class Teacher1Bean implements Serializable{
        /**
         * name : 张永福
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
