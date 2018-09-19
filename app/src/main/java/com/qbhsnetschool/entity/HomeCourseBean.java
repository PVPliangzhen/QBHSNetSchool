package com.qbhsnetschool.entity;

import java.io.Serializable;

public class HomeCourseBean implements Serializable{

    /**
     * product_id : 3t5le3zo8hf934m4fkdy
     * teacher1 : {"id":1,"name":"张永福","app_head_pic":"http://www.hualuogengshuxue.com/media/teachers/teacher_zhang3x.png","intro1":"清华数学系基科班毕业","intro2":"高考福建省第八名，数学149分","intro3":"9年教学经验，30万人次高考直播教学经验","school":"清华","app_head_pic_small":"http://www.hualuogengshuxue.com/media/teachers/teacher_Oval_zhang3x.png"}
     * teacher2 : null
     * course_type : 2
     * grade : 3
     * season : q
     * items : 13
     * title1 : 华罗庚领航班
     * title3 : 三年级华罗庚领航班
     * course_sdate : 2018-09-09
     * course_edate : 2019-01-06
     * course_time : 周日10：00-12：00
     * stars : 5
     * original_price : 1699
     * price : 1699
     * limit_numbers : 2000
     * sign_numbers : 537
     * course_status : 1
     * fill_numbers : 25
     * course_outline : null
     * room_id : 7CA806C9BDDB399D9C33DC5901307461
     * exam_id : 5
     * isspecial : 0
     * detail_title : 三年级华罗庚领航班
     * course_date : 9月9日-1月6日
     * chapter_times : 17次课34课时
     */

    private String product_id;
    private Teacher1Bean teacher1;
    private Object teacher2;
    private int course_type;
    private int grade;
    private String season;
    private int items;
    private String title1;
    private String title3;
    private String course_sdate;
    private String course_edate;
    private String course_time;
    private int stars;
    private int original_price;
    private int price;
    private int limit_numbers;
    private int sign_numbers;
    private int course_status;
    private int fill_numbers;
    private Object course_outline;
    private String room_id;
    private int exam_id;
    private int isspecial;
    private String detail_title;
    private String course_date;
    private String chapter_times;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public int getCourse_type() {
        return course_type;
    }

    public void setCourse_type(int course_type) {
        this.course_type = course_type;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
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

    public int getLimit_numbers() {
        return limit_numbers;
    }

    public void setLimit_numbers(int limit_numbers) {
        this.limit_numbers = limit_numbers;
    }

    public int getSign_numbers() {
        return sign_numbers;
    }

    public void setSign_numbers(int sign_numbers) {
        this.sign_numbers = sign_numbers;
    }

    public int getCourse_status() {
        return course_status;
    }

    public void setCourse_status(int course_status) {
        this.course_status = course_status;
    }

    public int getFill_numbers() {
        return fill_numbers;
    }

    public void setFill_numbers(int fill_numbers) {
        this.fill_numbers = fill_numbers;
    }

    public Object getCourse_outline() {
        return course_outline;
    }

    public void setCourse_outline(Object course_outline) {
        this.course_outline = course_outline;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getIsspecial() {
        return isspecial;
    }

    public void setIsspecial(int isspecial) {
        this.isspecial = isspecial;
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

    public static class Teacher1Bean implements Serializable{
        /**
         * id : 1
         * name : 张永福
         * app_head_pic : http://www.hualuogengshuxue.com/media/teachers/teacher_zhang3x.png
         * intro1 : 清华数学系基科班毕业
         * intro2 : 高考福建省第八名，数学149分
         * intro3 : 9年教学经验，30万人次高考直播教学经验
         * school : 清华
         * app_head_pic_small : http://www.hualuogengshuxue.com/media/teachers/teacher_Oval_zhang3x.png
         */

        private int id;
        private String name;
        private String app_head_pic;
        private String intro1;
        private String intro2;
        private String intro3;
        private String school;
        private String app_head_pic_small;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_head_pic() {
            return app_head_pic;
        }

        public void setApp_head_pic(String app_head_pic) {
            this.app_head_pic = app_head_pic;
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

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getApp_head_pic_small() {
            return app_head_pic_small;
        }

        public void setApp_head_pic_small(String app_head_pic_small) {
            this.app_head_pic_small = app_head_pic_small;
        }
    }
}
