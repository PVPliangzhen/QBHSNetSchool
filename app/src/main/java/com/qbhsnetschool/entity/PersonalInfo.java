package com.qbhsnetschool.entity;

public class PersonalInfo {

    /**
     * id : 23443
     * nickname : 哈哈
     * tel : 18701073115
     * realname : You
     * sex : m
     * grade : 4
     * birthday : 2026-08-13
     * headpic : http://192.168.1.70:8888/media/headpic_app/%E5%A4%B4%E5%83%8F_B3x.png
     * code : 200
     */

    private int id;
    private String nickname;
    private String tel;
    private String realname;
    private String sex;
    private int grade;
    private String birthday;
    private String headpic;
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
