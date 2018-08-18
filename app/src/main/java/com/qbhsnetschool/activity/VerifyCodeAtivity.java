package com.qbhsnetschool.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.entity.User;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.http.RealResponseBody;

public class VerifyCodeAtivity extends BaseActivity{

    private String phonenumber;
    private TextView phone_number;
    private TextView count_timer_txt;
    private Button login_btn;
    private ImageView page_back;
    private EditText verify_code_edit;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private TextView txt4;
    private TextView txt5;
    private TextView txt6;
    private TextView [] textViews = new TextView [6];
    private ImageView pwd_show_img;
    private EditText pwd_content;
    private TextView register_issue;
    private TextView register_protocol;
    private VerifyCodeAtivity activity;
    private VerifyCodeHandler verifyCodeHandler;

    private static class VerifyCodeHandler extends Handler{

        WeakReference<VerifyCodeAtivity> weakReference;

        public VerifyCodeHandler(VerifyCodeAtivity ativity){
            weakReference = new WeakReference<>(ativity);
        }

        @Override
        public void handleMessage(Message msg) {
            VerifyCodeAtivity verifyCodeAtivity = weakReference.get();
            if (verifyCodeAtivity != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        handleUser(verifyCodeAtivity, result);
                        break;
                }
            }
        }
    }

    private static void handleUser(VerifyCodeAtivity verifyCodeAtivity, String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            User user = new User();
            user.setUserId(jsonObject.optInt("id"));
            user.setNickname(jsonObject.optString("nickname"));
            user.setResponseCode(jsonObject.optString("code"));
            user.setResponseMsg(jsonObject.optString("msg"));
            user.setUserTel(jsonObject.optString("tel"));
            user.setUserToken(jsonObject.optString("token"));
            QBHSApplication application = (QBHSApplication) verifyCodeAtivity.getApplicationContext();
            application.setUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_verify_code, true, R.color.status_bar_bg_color, false);
        activity = this;
        initView();
        verifyCodeHandler = new VerifyCodeHandler(activity);
    }

    private void initView() {
        phonenumber = getIntent().getStringExtra("phonenumber");
        phone_number = (TextView) findViewById(R.id.phone_number);
        phone_number.setText(phonenumber);
        count_timer_txt = (TextView) findViewById(R.id.count_timer_txt);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(clickListener);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("获取验证码");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        verify_code_edit = (EditText) findViewById(R.id.verify_code_edit);
        pwd_show_img = (ImageView) findViewById(R.id.pwd_show_img);
        pwd_show_img.setOnClickListener(clickListener);
        pwd_content = (EditText) findViewById(R.id.pwd_content);
        register_issue = (TextView) findViewById(R.id.register_issue);
        register_issue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register_protocol = (TextView) findViewById(R.id.register_protocol);
        register_protocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        textViews[0] = txt1;
        textViews[1] = txt2;
        textViews[2] = txt3;
        textViews[3] = txt4;
        textViews[4] = txt5;
        textViews[5] = txt6;

        verify_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String editNumber = "";

                if (editable.length() > 6){
                    return;
                }else{
                    editNumber = editable.toString() + " " + " " + " " + " " + " " + " ";
                }

                for(int i = 0; i < 6; i++){
                    textViews[i].setText(String.valueOf(editNumber.charAt(i)));
                }
            }
        });
        countDownTimer.start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(60 *1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            count_timer_txt.setText(millisUntilFinished / 1000 + "s后重新发送");
        }

        @Override
        public void onFinish() {
            count_timer_txt.setText("请重新获取验证码");
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.login_btn:
                    registerUser();
                    break;
                case R.id.page_back:
                    finish();
                    break;
                case R.id.pwd_show_img:
                    UIUtils.showPasswordExpress(pwd_content, pwd_show_img);
                    break;
            }
        }
    };

    private void registerUser() {
        if (UIUtils.isNetworkAvailable(activity)){
            Map<String, String> params = new HashMap<>();
            params.put("password", pwd_content.getText().toString().trim());
            params.put("tel", phonenumber);
            params.put("tel_code", verify_code_edit.getText().toString().trim());
            HttpHelper.httpRequest(UrlHelper.registerUser(), params, "POST", new Callback() {
                @Override
                public void onResponse(HttpResponse response) {
                    try {
                        if (response.code() == 200){
                            String result = (((RealResponseBody) response.body()).string());
                            Message message = Message.obtain();
                            message.what = 0x01;
                            message.obj = result;
                            verifyCodeHandler.sendMessage(message);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "请求错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else{
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
}
