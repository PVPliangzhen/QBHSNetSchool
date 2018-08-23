package com.qbhsnetschool.protocol;

import android.content.Context;

import com.httputils.HttpContent;
import com.qbhsnetschool.entity.User;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.uitls.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpBuilder {

    private HttpContent.Builder innerBuilder;

    public OkHttpBuilder(String url){
        innerBuilder = new HttpContent.Builder(url);
    }

    public OkHttpBuilder setConnectTimeout(TimeUnit unit, long timeout) {
        innerBuilder.setConnectTimeout(unit, timeout);
        return this;
    }

    public OkHttpBuilder setWriteTimeout(TimeUnit unit, long timeout) {
        innerBuilder.setWriteTimeout(unit, timeout);
        return this;
    }

    public OkHttpBuilder setReadTimeOut(TimeUnit unit, long timeout) {
        innerBuilder.setReadTimeOut(unit, timeout);
        return this;
    }

    public OkHttpBuilder addHeader(HttpContent request, String name, String content) {
        if (request != null) {
            request.addHeader(name, content);
        }
        return this;
    }

    public OkHttpBuilder setParams(HttpContent request, Map<String, String> params){
        if (null != request && null != params){
//            if (UserManager.getInstance().getUser(context) != null){
//                request.addParameter("Authorization", "JWT" + UserManager.getInstance().getUser(context).getUserToken());
//            }
            for (Map.Entry<String, String> entry : params.entrySet()){
                request.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public OkHttpBuilder setTag(HttpContent request, Object value) {
        return setTag(request, null, value);
    }

    public OkHttpBuilder setTag(HttpContent request, String name, Object value) {
        if (null != request) {
            request.addTag(name, value);
        }
        return this;
    }

    public HttpContent build() {
        HttpContent request = innerBuilder.build();
        if (UserManager.getInstance().getUser() != null && !StringUtils.isEmpty(UserManager.getInstance().getUser().getUserToken())){
            String token = UserManager.getInstance().getUser().getUserToken();
            addHeader(request, "Authorization", "JWT " + token);
        }
        //request.addFeature(HttpContent.F_PACKET, "only-params/json");
        //request.addFeature(HttpContent.F_BODY, "{*}");
        return request;
    }
}
