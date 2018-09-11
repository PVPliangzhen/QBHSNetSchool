package com.qbhsnetschool.entity;

public class ZhuanjieshaoBean {

    /**
     * code : 200
     * image : http://192.168.1.172:8888/media/recommend_qr/23445.jpg
     * coupon3 : {"coupon_type":3,"type":"秋季班","amount":"500元"}
     * coupon4 : {"coupon_type":4,"amount":"200元"}
     * cash : {"amount":"100元"}
     * wx : {"title":"您有一张500元优惠券待领取","title_detail":"华罗庚网校邀您享受新学员注册500元购课优惠立减","url":"http://www.hualuogengshuxue.com/recommend/?uid=23445"}
     */

    private String code;
    private String image;
    private Coupon3Bean coupon3;
    private Coupon4Bean coupon4;
    private CashBean cash;
    private WxBean wx;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Coupon3Bean getCoupon3() {
        return coupon3;
    }

    public void setCoupon3(Coupon3Bean coupon3) {
        this.coupon3 = coupon3;
    }

    public Coupon4Bean getCoupon4() {
        return coupon4;
    }

    public void setCoupon4(Coupon4Bean coupon4) {
        this.coupon4 = coupon4;
    }

    public CashBean getCash() {
        return cash;
    }

    public void setCash(CashBean cash) {
        this.cash = cash;
    }

    public WxBean getWx() {
        return wx;
    }

    public void setWx(WxBean wx) {
        this.wx = wx;
    }

    public static class Coupon3Bean {
        /**
         * coupon_type : 3
         * type : 秋季班
         * amount : 500元
         */

        private int coupon_type;
        private String type;
        private String amount;

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class Coupon4Bean {
        /**
         * coupon_type : 4
         * amount : 200元
         */

        private int coupon_type;
        private String amount;

        public int getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(int coupon_type) {
            this.coupon_type = coupon_type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class CashBean {
        /**
         * amount : 100元
         */

        private String amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public static class WxBean {
        /**
         * title : 您有一张500元优惠券待领取
         * title_detail : 华罗庚网校邀您享受新学员注册500元购课优惠立减
         * url : http://www.hualuogengshuxue.com/recommend/?uid=23445
         */

        private String title;
        private String title_detail;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle_detail() {
            return title_detail;
        }

        public void setTitle_detail(String title_detail) {
            this.title_detail = title_detail;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
