package com.qbhsnetschool.entity;

import java.io.Serializable;

public class AddressBean implements Serializable{

    /**
     * id : 18646
     * uid : 300
     * name : 这么大
     * tel : 15131138388
     * province : 北京市
     * city : 北京市
     * county : 东城区
     * address : 这么多
     * default_flag : false
     */

    private int id;
    private int uid;
    private String name;
    private String tel;
    private String province;
    private String city;
    private String county;
    private String address;
    private boolean default_flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public boolean isDefault_flag() {
        return default_flag;
    }

    public void setDefault_flag(boolean default_flag) {
        this.default_flag = default_flag;
    }
}
