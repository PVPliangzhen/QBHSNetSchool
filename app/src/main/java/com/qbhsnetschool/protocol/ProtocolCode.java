package com.qbhsnetschool.protocol;

public enum  ProtocolCode {

    CODE_1100("短信验证码发送成功"),
    CODE_1101("短信发送过于频繁"),
    CODE_1102("验证码发送失败"),
    CODE_1103("短信验证码过期"),
    CODE_1104("短信验证码过期"),
    CODE_1105("短信验证码输入错误"),
    CODE_1106("密码需是6 - 20个字符"),
    CODE_1110("修改密码成功"),
    CODE_1200("登陆成功"),
    CODE_1201("手机号或密码输入错误"),
    CODE_1202("您的手机号已注册，请直接登陆"),
    CODE_1203("手机号未注册"),
    CODE_1204("token过期"),
    CODE_1205("token错误"),
    CODE_1300("删除地址成功"),
    CODE_1301("用户保存地址数超过上限"),
    CODE_4000("参数错误"),
    CODE_507("系统错误");

    private String name;

    private ProtocolCode(String name){
        this.name = name;
    }
}
