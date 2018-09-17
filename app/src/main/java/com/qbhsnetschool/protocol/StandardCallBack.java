package com.qbhsnetschool.protocol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.activity.LoginTrasitActivity;
import com.qbhsnetschool.uitls.LoadingDialog;

import org.json.JSONObject;

import okhttp3.internal.http.RealResponseBody;

public abstract class StandardCallBack implements Callback {

    private Context context;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    if (!LoadingDialog.isDissMissLoading()) {
                        LoadingDialog.dismissLoading();
                    }
                    String message = (String) msg.obj;
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginTrasitActivity.class);
                    context.startActivity(intent);
                    break;
                case 0x02:
                    if (!LoadingDialog.isDissMissLoading()) {
                        LoadingDialog.dismissLoading();
                    }
                    Toast.makeText(context, "请求错误，请重试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public StandardCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(HttpResponse response) {
        try {
            if (response.code() == 200 || response.code() == 201) {
                String result = (((RealResponseBody) response.body()).string());
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.optString("code");
                String msg = jsonObject.optString("msg");
                if (code.equalsIgnoreCase(ProtocolCode.CODE_1204.getValue())) {
                    if (context != null) {
                        Message message = Message.obtain();
                        message.what = 0x01;
                        message.obj = msg;
                        handler.sendMessage(message);
                    }
                } else {
                    onSuccess(result);
                }
            } else {
                onFailure(response.code());
            }
        } catch (Exception e) {
            onError(e);
            e.printStackTrace();
        }
    }

    public abstract void onSuccess(String result);

    public void onFailure(int code) {
        Message message = Message.obtain();
        message.what = 0x02;
        message.obj = code;
        handler.sendMessage(message);
    }

    public void onError(Exception e) {
        handler.sendEmptyMessage(0x02);
    }
}
