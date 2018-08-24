package com.qbhsnetschool.entity;

public class CouponBean {

    /**
     * couponid : 3800458336596
     * amount : 500
     * start_time : 2018-08-16T21:01:19.778589+08:00
     * end_time : 2018-08-23T21:01:21.954733+08:00
     * coupon_type : 1
     * is_used : true
     * desc : 暑期班学员立减500元
     * is_active : false
     * expiry_date : 2018.08.16－2018.08.23
     */

    private String couponid;
    private int amount;
    private String start_time;
    private String end_time;
    private int coupon_type;
    private boolean is_used;
    private String desc;
    private boolean is_active;
    private String expiry_date;

    public String getRest_date() {
        return rest_date;
    }

    public void setRest_date(String rest_date) {
        this.rest_date = rest_date;
    }

    private String rest_date;

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    public boolean isIs_used() {
        return is_used;
    }

    public void setIs_used(boolean is_used) {
        this.is_used = is_used;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
