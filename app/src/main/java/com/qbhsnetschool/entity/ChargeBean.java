package com.qbhsnetschool.entity;

import java.util.List;

public class ChargeBean {

    /**
     * id : ch_nbj9a9ezDCq1PG4iL4q5i1S8
     * object : charge
     * created : 1535957602
     * livemode : false
     * paid : false
     * refunded : false
     * reversed : false
     * app : app_uTiDO4CarXnPSeTu
     * channel : wx
     * order_no : 2018090314532155932329
     * client_ip : 192.168.1.165
     * amount : 169900
     * amount_settle : 169900
     * currency : cny
     * subject : 四年级秋季星期天直播培优班(全国适用)培优班2班
     * body : 四年级秋季星期天直播培优班(全国适用)培优班2班
     * extra : {}
     * time_paid : null
     * time_expire : 1535964802
     * time_settle : null
     * transaction_no : null
     * refunds : {"object":"list","url":"/v1/charges/ch_nbj9a9ezDCq1PG4iL4q5i1S8/refunds","has_more":false,"data":[]}
     * amount_refunded : 0
     * failure_code : null
     * failure_msg : null
     * metadata : {"phone_number":"18701073115","user_id":23443}
     * credential : {"object":"credential","wx":{"appId":"wxn1cga9nfdepohou1","partnerId":"1802348934","prepayId":"1101000000180903k40alsgm98q1ca5y","nonceStr":"8210298fc74025ba232ba9875ecffd8b","timeStamp":1535957602,"packageValue":"Sign=WXPay","sign":"7FC78083617CD0B266761333E642F8C3"}}
     * description : null
     */

    private String id;
    private String object;
    private int created;
    private boolean livemode;
    private boolean paid;
    private boolean refunded;
    private boolean reversed;
    private String app;
    private String channel;
    private String order_no;
    private String client_ip;
    private int amount;
    private int amount_settle;
    private String currency;
    private String subject;
    private String body;
    private ExtraBean extra;
    private Object time_paid;
    private int time_expire;
    private Object time_settle;
    private Object transaction_no;
    private RefundsBean refunds;
    private int amount_refunded;
    private Object failure_code;
    private Object failure_msg;
    private MetadataBean metadata;
    private CredentialBean credential;
    private Object description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_settle() {
        return amount_settle;
    }

    public void setAmount_settle(int amount_settle) {
        this.amount_settle = amount_settle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public Object getTime_paid() {
        return time_paid;
    }

    public void setTime_paid(Object time_paid) {
        this.time_paid = time_paid;
    }

    public int getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(int time_expire) {
        this.time_expire = time_expire;
    }

    public Object getTime_settle() {
        return time_settle;
    }

    public void setTime_settle(Object time_settle) {
        this.time_settle = time_settle;
    }

    public Object getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(Object transaction_no) {
        this.transaction_no = transaction_no;
    }

    public RefundsBean getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsBean refunds) {
        this.refunds = refunds;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public Object getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(Object failure_code) {
        this.failure_code = failure_code;
    }

    public Object getFailure_msg() {
        return failure_msg;
    }

    public void setFailure_msg(Object failure_msg) {
        this.failure_msg = failure_msg;
    }

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public CredentialBean getCredential() {
        return credential;
    }

    public void setCredential(CredentialBean credential) {
        this.credential = credential;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public static class ExtraBean {
    }

    public static class RefundsBean {
        /**
         * object : list
         * url : /v1/charges/ch_nbj9a9ezDCq1PG4iL4q5i1S8/refunds
         * has_more : false
         * data : []
         */

        private String object;
        private String url;
        private boolean has_more;
        private List<?> data;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public List<?> getData() {
            return data;
        }

        public void setData(List<?> data) {
            this.data = data;
        }
    }

    public static class MetadataBean {
        /**
         * phone_number : 18701073115
         * user_id : 23443
         */

        private String phone_number;
        private int user_id;

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

    public static class CredentialBean {
        /**
         * object : credential
         * wx : {"appId":"wxn1cga9nfdepohou1","partnerId":"1802348934","prepayId":"1101000000180903k40alsgm98q1ca5y","nonceStr":"8210298fc74025ba232ba9875ecffd8b","timeStamp":1535957602,"packageValue":"Sign=WXPay","sign":"7FC78083617CD0B266761333E642F8C3"}
         */

        private String object;
        private WxBean wx;

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public WxBean getWx() {
            return wx;
        }

        public void setWx(WxBean wx) {
            this.wx = wx;
        }

        public static class WxBean {
            /**
             * appId : wxn1cga9nfdepohou1
             * partnerId : 1802348934
             * prepayId : 1101000000180903k40alsgm98q1ca5y
             * nonceStr : 8210298fc74025ba232ba9875ecffd8b
             * timeStamp : 1535957602
             * packageValue : Sign=WXPay
             * sign : 7FC78083617CD0B266761333E642F8C3
             */

            private String appId;
            private String partnerId;
            private String prepayId;
            private String nonceStr;
            private int timeStamp;
            private String packageValue;
            private String sign;

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
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

            public String getNonceStr() {
                return nonceStr;
            }

            public void setNonceStr(String nonceStr) {
                this.nonceStr = nonceStr;
            }

            public int getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(int timeStamp) {
                this.timeStamp = timeStamp;
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
        }
    }
}
