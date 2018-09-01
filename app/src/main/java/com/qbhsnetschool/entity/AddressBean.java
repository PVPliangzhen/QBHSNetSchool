package com.qbhsnetschool.entity;

import java.io.Serializable;

public class AddressBean implements Serializable{

    /**
     * id : 4
     * name : will
     * tel : 18352860815
     * province : 北京市
     * city : 北京市
     * county : 昌平区
     * address : 鼎好大厦
     * uid : 10
     */

    private int id;
    private String name;
    private String tel;
    private String province;
    private String city;
    private String county;
    private String address;
    private int uid;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
