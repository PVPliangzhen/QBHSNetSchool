package com.qbhsnetschool.entity;

import java.io.Serializable;

public class OrderBean implements Serializable{


    /**
     * amount : 9900
     * order_no : 2018082414513642339941
     * status : 1
     * course_id_id : 2n3n4n3n2n3n4nrnend3
     * address : {"address_phone_number": "18815554145", "address": "\u5317\u4eac\u5e02 \u5317\u4eac\u5e02 \u4e1c\u57ce\u533a Aaa", "address_id": "17418", "name": "\u55ef\u54fc"}
     * charge_json : {"created": 1535093496, "time_expire": 1535100696, "channel": "wx", "reversed": false, "credential": {"wx": {"nonceStr": "56f3fd09183edcc67bb75e13988154f5", "prepayId": "1101000000180824o4smfhxftce5yzn1", "packageValue": "Sign=WXPay", "sign": "8BE0E213511155881C28D6472D4FB6F7", "partnerId": "5982742389", "appId": "wxuxz98ojh4kelgar9", "timeStamp": 1535093496}, "object": "credential"}, "client_ip": "127.0.0.1", "object": "charge", "amount_refunded": 0, "livemode": false, "app": "app_uTiDO4CarXnPSeTu", "extra": {}, "refunds": {}, "paid": false, "time_paid": null, "amount_settle": 9900, "id": "ch_nH0mvDWnn9S8qjbXfLqfj9uH", "description": null, "currency": "cny", "failure_code": null, "metadata": {"phone_number": "18701073115", "user_id": 23443}, "transaction_no": null, "refunded": false, "body": "\u4e09\u5e74\u7ea7\u6691\u5047\u7b2c\u56db\u671f\u76f4\u64ad\u5c16\u5b50\u73ed(\u5168\u56fd\u9002\u7528)\u5c16\u5b50\u73ed2\u73ed", "failure_msg": null, "order_no": "2018082414513642339941", "amount": 9900, "subject": "\u4e09\u5e74\u7ea7\u6691\u5047\u7b2c\u56db\u671f\u76f4\u64ad\u5c16\u5b50\u73ed(\u5168\u56fd\u9002\u7528)\u5c16\u5b50\u73ed2\u73ed", "time_settle": null}
     * course_data : {"chapter_times":"9次课18课时","items":4,"original_price":800,"product_id":"2n3n4n3n2n3n4nrnend3","price":99,"detail_title":"二升三年级直播尖子班(全国适用)","season":"x","course_time":"每天19:30 - 21:30上课","course_date":"8月21日-8月28日","teacher":"彭薏霖","teacher_headpic_small":"http://192.168.1.70:8888/media/teachers/teacher_Oval_彭3x.png"}
     * address_data : {"address":"北京市 北京市 东城区 Aaa","id":"17418","tel":"18815554145","name":"嗯哼"}
     * detail_title : 二升三年级直播尖子班(全国适用)
     * course_date : 8月21日-8月28日
     * chapter_times : 9次课18课时
     * charge : {"created":1535093496,"time_expire":1535100696,"channel":"wx","reversed":false,"credential":{"wx":{"nonceStr":"56f3fd09183edcc67bb75e13988154f5","appId":"wxuxz98ojh4kelgar9","packageValue":"Sign=WXPay","sign":"8BE0E213511155881C28D6472D4FB6F7","partnerId":"5982742389","prepayId":"1101000000180824o4smfhxftce5yzn1","timeStamp":1535093496},"object":"credential"},"client_ip":"127.0.0.1","object":"charge","amount_refunded":0,"livemode":false,"app":"app_uTiDO4CarXnPSeTu","extra":{},"refunds":{},"paid":false,"time_paid":null,"amount_settle":9900,"id":"ch_nH0mvDWnn9S8qjbXfLqfj9uH","description":null,"currency":"cny","failure_code":null,"metadata":{},"transaction_no":null,"refunded":false,"body":"三年级暑假第四期直播尖子班(全国适用)尖子班2班","failure_msg":null,"order_no":"2018082414513642339941","amount":9900,"subject":"三年级暑假第四期直播尖子班(全国适用)尖子班2班","time_settle":null}
     */

    private int amount;
    private String order_no;
    private int status;
    private String course_id_id;
    private String address;
    private String charge_json;
    private CourseDataBean course_data;
    private AddressDataBean address_data;
    private String detail_title;
    private String course_date;
    private String chapter_times;
    private ChargeBean charge;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCourse_id_id() {
        return course_id_id;
    }

    public void setCourse_id_id(String course_id_id) {
        this.course_id_id = course_id_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharge_json() {
        return charge_json;
    }

    public void setCharge_json(String charge_json) {
        this.charge_json = charge_json;
    }

    public CourseDataBean getCourse_data() {
        return course_data;
    }

    public void setCourse_data(CourseDataBean course_data) {
        this.course_data = course_data;
    }

    public AddressDataBean getAddress_data() {
        return address_data;
    }

    public void setAddress_data(AddressDataBean address_data) {
        this.address_data = address_data;
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

    public ChargeBean getCharge() {
        return charge;
    }

    public void setCharge(ChargeBean charge) {
        this.charge = charge;
    }

    public static class CourseDataBean implements Serializable{
        /**
         * chapter_times : 9次课18课时
         * items : 4
         * original_price : 800
         * product_id : 2n3n4n3n2n3n4nrnend3
         * price : 99
         * detail_title : 二升三年级直播尖子班(全国适用)
         * season : x
         * course_time : 每天19:30 - 21:30上课
         * course_date : 8月21日-8月28日
         * teacher : 彭薏霖
         * teacher_headpic_small : http://192.168.1.70:8888/media/teachers/teacher_Oval_彭3x.png
         */

        private String chapter_times;
        private int items;
        private int original_price;
        private String product_id;
        private int price;
        private String detail_title;
        private String season;
        private String course_time;
        private String course_date;
        private String teacher;
        private String teacher_headpic_small;

        public String getChapter_times() {
            return chapter_times;
        }

        public void setChapter_times(String chapter_times) {
            this.chapter_times = chapter_times;
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

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDetail_title() {
            return detail_title;
        }

        public void setDetail_title(String detail_title) {
            this.detail_title = detail_title;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getCourse_time() {
            return course_time;
        }

        public void setCourse_time(String course_time) {
            this.course_time = course_time;
        }

        public String getCourse_date() {
            return course_date;
        }

        public void setCourse_date(String course_date) {
            this.course_date = course_date;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getTeacher_headpic_small() {
            return teacher_headpic_small;
        }

        public void setTeacher_headpic_small(String teacher_headpic_small) {
            this.teacher_headpic_small = teacher_headpic_small;
        }
    }

    public static class AddressDataBean implements Serializable{
        /**
         * address : 北京市 北京市 东城区 Aaa
         * id : 17418
         * tel : 18815554145
         * name : 嗯哼
         */

        private String address;
        private String id;
        private String tel;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ChargeBean implements Serializable{
        /**
         * created : 1535093496
         * time_expire : 1535100696
         * channel : wx
         * reversed : false
         * credential : {"wx":{"nonceStr":"56f3fd09183edcc67bb75e13988154f5","appId":"wxuxz98ojh4kelgar9","packageValue":"Sign=WXPay","sign":"8BE0E213511155881C28D6472D4FB6F7","partnerId":"5982742389","prepayId":"1101000000180824o4smfhxftce5yzn1","timeStamp":1535093496},"object":"credential"}
         * client_ip : 127.0.0.1
         * object : charge
         * amount_refunded : 0
         * livemode : false
         * app : app_uTiDO4CarXnPSeTu
         * extra : {}
         * refunds : {}
         * paid : false
         * time_paid : null
         * amount_settle : 9900
         * id : ch_nH0mvDWnn9S8qjbXfLqfj9uH
         * description : null
         * currency : cny
         * failure_code : null
         * metadata : {}
         * transaction_no : null
         * refunded : false
         * body : 三年级暑假第四期直播尖子班(全国适用)尖子班2班
         * failure_msg : null
         * order_no : 2018082414513642339941
         * amount : 9900
         * subject : 三年级暑假第四期直播尖子班(全国适用)尖子班2班
         * time_settle : null
         */

        private int created;
        private int time_expire;
        private String channel;
        private boolean reversed;
        private CredentialBean credential;
        private String client_ip;
        private String object;
        private int amount_refunded;
        private boolean livemode;
        private String app;
        private ExtraBean extra;
        private RefundsBean refunds;
        private boolean paid;
        private Object time_paid;
        private int amount_settle;
        private String id;
        private Object description;
        private String currency;
        private Object failure_code;
        private MetadataBean metadata;
        private Object transaction_no;
        private boolean refunded;
        private String body;
        private Object failure_msg;
        private String order_no;
        private int amount;
        private String subject;
        private Object time_settle;

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public int getTime_expire() {
            return time_expire;
        }

        public void setTime_expire(int time_expire) {
            this.time_expire = time_expire;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public boolean isReversed() {
            return reversed;
        }

        public void setReversed(boolean reversed) {
            this.reversed = reversed;
        }

        public CredentialBean getCredential() {
            return credential;
        }

        public void setCredential(CredentialBean credential) {
            this.credential = credential;
        }

        public String getClient_ip() {
            return client_ip;
        }

        public void setClient_ip(String client_ip) {
            this.client_ip = client_ip;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public int getAmount_refunded() {
            return amount_refunded;
        }

        public void setAmount_refunded(int amount_refunded) {
            this.amount_refunded = amount_refunded;
        }

        public boolean isLivemode() {
            return livemode;
        }

        public void setLivemode(boolean livemode) {
            this.livemode = livemode;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public RefundsBean getRefunds() {
            return refunds;
        }

        public void setRefunds(RefundsBean refunds) {
            this.refunds = refunds;
        }

        public boolean isPaid() {
            return paid;
        }

        public void setPaid(boolean paid) {
            this.paid = paid;
        }

        public Object getTime_paid() {
            return time_paid;
        }

        public void setTime_paid(Object time_paid) {
            this.time_paid = time_paid;
        }

        public int getAmount_settle() {
            return amount_settle;
        }

        public void setAmount_settle(int amount_settle) {
            this.amount_settle = amount_settle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Object getFailure_code() {
            return failure_code;
        }

        public void setFailure_code(Object failure_code) {
            this.failure_code = failure_code;
        }

        public MetadataBean getMetadata() {
            return metadata;
        }

        public void setMetadata(MetadataBean metadata) {
            this.metadata = metadata;
        }

        public Object getTransaction_no() {
            return transaction_no;
        }

        public void setTransaction_no(Object transaction_no) {
            this.transaction_no = transaction_no;
        }

        public boolean isRefunded() {
            return refunded;
        }

        public void setRefunded(boolean refunded) {
            this.refunded = refunded;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Object getFailure_msg() {
            return failure_msg;
        }

        public void setFailure_msg(Object failure_msg) {
            this.failure_msg = failure_msg;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public Object getTime_settle() {
            return time_settle;
        }

        public void setTime_settle(Object time_settle) {
            this.time_settle = time_settle;
        }

        public static class CredentialBean implements Serializable{
            /**
             * wx : {"nonceStr":"56f3fd09183edcc67bb75e13988154f5","appId":"wxuxz98ojh4kelgar9","packageValue":"Sign=WXPay","sign":"8BE0E213511155881C28D6472D4FB6F7","partnerId":"5982742389","prepayId":"1101000000180824o4smfhxftce5yzn1","timeStamp":1535093496}
             * object : credential
             */

            private WxBean wx;
            private String object;

            public WxBean getWx() {
                return wx;
            }

            public void setWx(WxBean wx) {
                this.wx = wx;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public static class WxBean implements Serializable{
                /**
                 * nonceStr : 56f3fd09183edcc67bb75e13988154f5
                 * appId : wxuxz98ojh4kelgar9
                 * packageValue : Sign=WXPay
                 * sign : 8BE0E213511155881C28D6472D4FB6F7
                 * partnerId : 5982742389
                 * prepayId : 1101000000180824o4smfhxftce5yzn1
                 * timeStamp : 1535093496
                 */

                private String nonceStr;
                private String appId;
                private String packageValue;
                private String sign;
                private String partnerId;
                private String prepayId;
                private int timeStamp;

                public String getNonceStr() {
                    return nonceStr;
                }

                public void setNonceStr(String nonceStr) {
                    this.nonceStr = nonceStr;
                }

                public String getAppId() {
                    return appId;
                }

                public void setAppId(String appId) {
                    this.appId = appId;
                }

                public String getPackageValue() {
                    return packageValue;
                }

                public void setPackageValue(String packageValue) {
                    this.packageValue = packageValue;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public String getPartnerId() {
                    return partnerId;
                }

                public void setPartnerId(String partnerId) {
                    this.partnerId = partnerId;
                }

                public String getPrepayId() {
                    return prepayId;
                }

                public void setPrepayId(String prepayId) {
                    this.prepayId = prepayId;
                }

                public int getTimeStamp() {
                    return timeStamp;
                }

                public void setTimeStamp(int timeStamp) {
                    this.timeStamp = timeStamp;
                }
            }
        }

        public static class ExtraBean implements Serializable{
        }

        public static class RefundsBean implements Serializable{
        }

        public static class MetadataBean implements Serializable{
        }
    }
}
