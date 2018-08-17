package com.qbhsnetschool.protocol;

public enum  ProtocolCode {

    CODE_1100("短信验证码发送成功", "1100"),
    CODE_1101("短信发送过于频繁", "1101"),
    CODE_1102("验证码发送失败", "1102"),
    CODE_1103("短信验证码过期", "1103"),
    CODE_1104("短信验证码过期", "1104"),
    CODE_1105("短信验证码输入错误", "1105"),
    CODE_1106("密码需是6 - 20个字符", "1106"),
    CODE_1110("修改密码成功", "1110"),
    CODE_1200("登陆成功", "1200"),
    CODE_1201("手机号或密码输入错误", "1201"),
    CODE_1202("您的手机号已注册，请直接登陆", "1202"),
    CODE_1203("手机号未注册", "1203"),
    CODE_1204("token过期", "1204"),
    CODE_1205("token错误", "1205"),
    CODE_1300("删除地址成功", "1300"),
    CODE_1301("用户保存地址数超过上限", "1301"),
    CODE_4000("参数错误", "4000"),
    CODE_507("系统错误", "507");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    ProtocolCode(String name, String value){
        this.name = name;
        this.value = value;
    }


}
