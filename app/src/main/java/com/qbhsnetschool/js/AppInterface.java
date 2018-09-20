package com.qbhsnetschool.js;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.qbhsnetschool.activity.CourseDetailActivity;

public class AppInterface {

    private Context context;

    public AppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void back_to_app(String courseId){
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("product_id", courseId);
        context.startActivity(intent);
    }
}
