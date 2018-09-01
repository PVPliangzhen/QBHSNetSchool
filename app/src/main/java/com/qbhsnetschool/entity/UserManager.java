package com.qbhsnetschool.entity;

import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.uitls.SpUtils;
import com.qbhsnetschool.uitls.StringUtils;

public class UserManager {

    private User user;

    private String token;

    private static UserManager userManager = null;

    private QBHSApplication qbhsApplication;

    public void registerApplication(QBHSApplication application){
        qbhsApplication = application;
    }

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

    public void setUser(User user){
        this.user = user;
        if (user != null) {
            SpUtils spUtils = SpUtils.getInstance(qbhsApplication);
            spUtils.put("user_id", user.getUserId());
            spUtils.put("nickname", user.getNickname());
            spUtils.put("code", user.getResponseCode());
            spUtils.put("msg", user.getResponseMsg());
            spUtils.put("tel", user.getUserTel());
            spUtils.put("token", user.getUserToken());
        }
    }

    public User getUser(){
        SpUtils spUtils = SpUtils.getInstance(qbhsApplication);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clearUser(){
        setUser(null);
        SpUtils spUtils = SpUtils.getInstance(qbhsApplication);
        spUtils.put("user_id", -1);
        spUtils.put("nickname", "");
        spUtils.put("code", "");
        spUtils.put("msg", "");
        spUtils.put("tel", "");
        spUtils.put("token", "");
    }

    public boolean isLogin(){
        //return getUser() != null;
        return true;
    }
}
