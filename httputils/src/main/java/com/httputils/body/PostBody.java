package com.httputils.body;

/**
 * Created by Administrator on 2018/1/30.
 */

public class PostBody implements Comparable<PostBody> {

    public static final int HEADER = 1;// 请求头部
    public static final int FEATURE = 3;// 请求特性
    public static final int PARAM = 5;// 参数
    public static final int STREAM = 7;// 上传文件
    public static final int EXTRA = 9;// 额外键值对


    public final String name;
    public final int type;
    public Object value;

    public PostBody next;

    public PostBody(String name, Object value, int type) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int compareTo(PostBody other) {
        if (other != null) {
            return (type - other.type);
        }
        return 0;
    }
}
