package com.qbhsnetschool.entity;

import java.io.Serializable;
import java.util.List;

public class CourseDetailBean implements Serializable{

    /**
     * code : 200
     * chapter : [{"measure":1,"chapter_name":"分数从产生到计算","chapter_start_time":"2018-10-06T03:00:00+08:00","chapter_end_time":"2018-10-06T05:00:00+08:00","chapter_date":"2018.10.5 (周五)","chapter_time":"19:00－21:00"},{"measure":2,"chapter_name":"分数的特殊计算","chapter_start_time":"2018-10-07T03:00:00+08:00","chapter_end_time":"2018-10-07T05:00:00+08:00","chapter_date":"2018.10.6 (周六)","chapter_time":"19:00－21:00"},{"measure":3,"chapter_name":"分数的经典应用题","chapter_start_time":"2018-10-08T03:00:00+08:00","chapter_end_time":"2018-10-08T05:00:00+08:00","chapter_date":"2018.10.7 (周日)","chapter_time":"19:00－21:00"}]
     * course : {"course_date":"10月5日-10月7日","course_time":"晚上19:00-21:00","chapter_times":"3次课6课时","stars":3,"detail_title":"六年级国庆分数短期班","items":14,"original_price":99,"price":99,"isspecial":1,"course_type":3,"season":"q","product_id":"bskrkhz3eulk7ads5ark","course_status":3,"teacher1":{"name":"吴正享","intro1":"毕业于清华大学，高考数学148分","intro2":"初中联赛一等奖，高中联赛二等奖","intro3":"9年教龄，优才奥数集训队教练","app_head_pic":"http://www.hualuogengshuxue.com/media/teachers/teacher_wu3x.png","app_head_pic_small":"http://www.hualuogengshuxue.com/media/teachers/teacher_Oval_wu3x.png"}}
     */

    private String code;
    private CourseBean course;
    private List<ChapterBean> chapter;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public List<ChapterBean> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterBean> chapter) {
        this.chapter = chapter;
    }

    public static class CourseBean implements Serializable{
        /**
         * course_date : 10月5日-10月7日
         * course_time : 晚上19:00-21:00
         * chapter_times : 3次课6课时
         * stars : 3
         * detail_title : 六年级国庆分数短期班
         * items : 14
         * original_price : 99
         * price : 99
         * isspecial : 1
         * course_type : 3
         * season : q
         * product_id : bskrkhz3eulk7ads5ark
         * course_status : 3
         * teacher1 : {"name":"吴正享","intro1":"毕业于清华大学，高考数学148分","intro2":"初中联赛一等奖，高中联赛二等奖","intro3":"9年教龄，优才奥数集训队教练","app_head_pic":"http://www.hualuogengshuxue.com/media/teachers/teacher_wu3x.png","app_head_pic_small":"http://www.hualuogengshuxue.com/media/teachers/teacher_Oval_wu3x.png"}
         */

        private String course_date;
        private String course_time;
        private String chapter_times;
        private int stars;
        private String detail_title;
        private int items;
        private int original_price;
        private int price;
        private int isspecial;
        private int course_type;
        private String season;
        private String product_id;
        private int course_status;
        private Teacher1Bean teacher1;

        public String getCourse_date() {
            return course_date;
        }

        public void setCourse_date(String course_date) {
            this.course_date = course_date;
        }

        public String getCourse_time() {
            return course_time;
        }

        public void setCourse_time(String course_time) {
            this.course_time = course_time;
        }

        public String getChapter_times() {
            return chapter_times;
        }

        public void setChapter_times(String chapter_times) {
            this.chapter_times = chapter_times;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getDetail_title() {
            return detail_title;
        }

        public void setDetail_title(String detail_title) {
            this.detail_title = detail_title;
        }

        public int getItems() {
            return items;
        }

        public void setItems(int items) {
            this.items = items;
        }

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getIsspecial() {
            return isspecial;
        }

        public void setIsspecial(int isspecial) {
            this.isspecial = isspecial;
        }

        public int getCourse_type() {
            return course_type;
        }

        public void setCourse_type(int course_type) {
            this.course_type = course_type;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public int getCourse_status() {
            return course_status;
        }

        public void setCourse_status(int course_status) {
            this.course_status = course_status;
        }

        public Teacher1Bean getTeacher1() {
            return teacher1;
        }

        public void setTeacher1(Teacher1Bean teacher1) {
            this.teacher1 = teacher1;
        }

        public static class Teacher1Bean implements Serializable{
            /**
             * name : 吴正享
             * intro1 : 毕业于清华大学，高考数学148分
             * intro2 : 初中联赛一等奖，高中联赛二等奖
             * intro3 : 9年教龄，优才奥数集训队教练
             * app_head_pic : http://www.hualuogengshuxue.com/media/teachers/teacher_wu3x.png
             * app_head_pic_small : http://www.hualuogengshuxue.com/media/teachers/teacher_Oval_wu3x.png
             */

            private String name;
            private String intro1;
            private String intro2;
            private String intro3;
            private String app_head_pic;
            private String app_head_pic_small;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIntro1() {
                return intro1;
            }

            public void setIntro1(String intro1) {
                this.intro1 = intro1;
            }

            public String getIntro2() {
                return intro2;
            }

            public void setIntro2(String intro2) {
                this.intro2 = intro2;
            }

            public String getIntro3() {
                return intro3;
            }

            public void setIntro3(String intro3) {
                this.intro3 = intro3;
            }

            public String getApp_head_pic() {
                return app_head_pic;
            }

            public void setApp_head_pic(String app_head_pic) {
                this.app_head_pic = app_head_pic;
            }

            public String getApp_head_pic_small() {
                return app_head_pic_small;
            }

            public void setApp_head_pic_small(String app_head_pic_small) {
                this.app_head_pic_small = app_head_pic_small;
            }
        }
    }

    public static class ChapterBean implements Serializable{
        /**
         * measure : 1
         * chapter_name : 分数从产生到计算
         * chapter_start_time : 2018-10-06T03:00:00+08:00
         * chapter_end_time : 2018-10-06T05:00:00+08:00
         * chapter_date : 2018.10.5 (周五)
         * chapter_time : 19:00－21:00
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
}
