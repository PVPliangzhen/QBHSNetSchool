package com.qbhsnetschool.activity;

import android.content.Intent;
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

import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.User;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.ProtocolCode;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.internal.http.RealResponseBody;

public class VerifyCodeAtivity extends BaseActivity {

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
    private TextView[] textViews = new TextView[6];
    private ImageView pwd_show_img;
    private EditText pwd_content;
    private TextView register_issue;
    private TextView register_protocol;
    private VerifyCodeAtivity activity;
    private VerifyCodeHandler verifyCodeHandler;
    private boolean isRegister;

    private static class VerifyCodeHandler extends Handler {

        WeakReference<VerifyCodeAtivity> weakReference;

        public VerifyCodeHandler(VerifyCodeAtivity ativity) {
            weakReference = new WeakReference<>(ativity);
        }

        @Override
        public void handleMessage(Message msg) {
            VerifyCodeAtivity verifyCodeAtivity = weakReference.get();
            if (verifyCodeAtivity != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        verifyCodeAtivity.handleUser(result);
                        break;
                }
            }
        }
    }

    private void login() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!judgePhoneNumberFormat()) {
            Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        Map<String, String> params = new HashMap<>();
        params.put("username", phonenumber);
        params.put("password", pwd_content.getText().toString().trim());
        HttpHelper.httpRequest(UrlHelper.login(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = response;
                    verifyCodeHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!LoadingDialog.isDissMissLoading()) {
                            LoadingDialog.dismissLoading();
                        }
                        Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat() {
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(phonenumber);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }

    private void handleUser(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()) {
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            String responseMsg = jsonObject.optString("msg");
            if (code.equalsIgnoreCase("200")) {
                int userId = jsonObject.optInt("user_id");
                String nickName = jsonObject.optString("nickname");
                String tel = jsonObject.optString("tel");
                String token = jsonObject.optString("token");
                User user = new User();
                user.setUserId(userId);
                user.setNickname(nickName);
                user.setResponseCode(code);
                user.setResponseMsg(responseMsg);
                user.setUserTel(tel);
                user.setUserToken(token);
                UserManager.getInstance().setUser(user);
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.putExtra("home_tab", "2");
                startActivity(intent);
            } else {
                Toast.makeText(activity, responseMsg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
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
        isRegister = getIntent().getBooleanExtra("is_register", true);
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

                if (editable.length() > 6) {
                    return;
                } else {
                    editNumber = editable.toString() + " " + " " + " " + " " + " " + " ";
                }

                for (int i = 0; i < 6; i++) {
                    textViews[i].setText(String.valueOf(editNumber.charAt(i)));
                }
            }
        });
        countDownTimer.start();
    }

    CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
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
            switch (view.getId()) {
                case R.id.login_btn:
                    if (isRegister) {
                        registerUser();
                    } else {
                        forgetPassword();
                    }
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

    private void forgetPassword() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("password", pwd_content.getText().toString().trim());
        params.put("tel", phonenumber);
        params.put("tel_code", verify_code_edit.getText().toString().trim());
        LoadingDialog.loading(activity);
        HttpHelper.httpRequest(UrlHelper.nopasswords(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    if (code.equalsIgnoreCase(ProtocolCode.CODE_1110.getValue())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity.login();
                            }
                        });
                    } else {
                        if (!LoadingDialog.isDissMissLoading()) {
                            LoadingDialog.dismissLoading();
                        }
                        final String msg = jsonObject.optString("msg");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                if (!LoadingDialog.isDissMissLoading()) {
                    LoadingDialog.dismissLoading();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void registerUser() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
        }
        Map<String, String> params = new HashMap<>();
        params.put("password", pwd_content.getText().toString().trim());
        params.put("tel", phonenumber);
        params.put("tel_code", verify_code_edit.getText().toString().trim());
        LoadingDialog.loading(activity);
        HttpHelper.httpRequest(UrlHelper.registerUser(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = response;
                    verifyCodeHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                if (!LoadingDialog.isDissMissLoading()) {
                    LoadingDialog.dismissLoading();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
