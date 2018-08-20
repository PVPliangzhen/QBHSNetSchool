package com.qbhsnetschool.protocol;

import android.content.Context;
import android.content.Intent;

import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.activity.LoginTrasitActivity;

import org.json.JSONObject;

import okhttp3.internal.http.RealResponseBody;

public abstract class StandardCallBack implements Callback{

    private Context context;

    public StandardCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(HttpResponse response) {
        try {
            if (response.code() == 200) {
                String result = (((RealResponseBody) response.body()).string());
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.optString("code");
                if (code.equalsIgnoreCase(ProtocolCode.CODE_1204.getValue())) {
                    if (context != null) {
                        Intent intent = new Intent(context, LoginTrasitActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                } else {
                    onSuccess(result);
                }
            }else{
                onFailure(response.code());
            }
        } catch (Exception e) {
            onError(e);
            e.printStackTrace();
        }
    }

    public abstract void onSuccess(String result);
    public void onFailure(int code){}
    public void onError(Exception e){}
}
