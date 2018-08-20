package com.qbhsnetschool.entity;

import android.content.Context;

import com.qbhsnetschool.uitls.SpUtils;
import com.qbhsnetschool.uitls.StringUtils;

public class UserManager {

    private User user;

    private static UserManager userManager = null;

    private UserManager(){}

    public static UserManager getInstance(){
        if (userManager == null){
            synchronized (UserManager.class){
                if (userManager == null){
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public void setUser(Context context, User user){
        this.user = user;
        SpUtils spUtils = SpUtils.getInstance(context);
        spUtils.put("user_id", user.getUserId());
        spUtils.put("nickname", user.getNickname());
        spUtils.put("code", user.getResponseCode());
        spUtils.put("msg", user.getResponseMsg());
        spUtils.put("tel", user.getUserTel());
        spUtils.put("token", user.getUserToken());
    }

    public User getUser(Context context){
        SpUtils spUtils = SpUtils.getInstance(context);
        if (user == null){
            String token = spUtils.get("token", "");
            if (!StringUtils.isEmpty(token)){
                User user = new User();
                user.setUserId(spUtils.get("user_id", -1));
                user.setNickname(spUtils.get("nickname", ""));
                user.setResponseCode(spUtils.get("code", ""));
                user.setResponseMsg(spUtils.get("msg", ""));
                user.setUserTel(spUtils.get("tel", ""));
                user.setUserToken(spUtils.get("token", ""));
                this.user = user;
            }
        }
        return user;
    }

    public boolean isLogin(Context context){
        return getUser(context) != null;
    }
}
