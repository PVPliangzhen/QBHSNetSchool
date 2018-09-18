package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.entity.User;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.LoginUtils;
import com.qbhsnetschool.uitls.SpUtils;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.FormBody;

public class LoginActivity extends BaseActivity {

    private EditText account_number_input;
    private ImageView account_num_delete;
    private EditText login_password;
    private ImageView img_pwd_show;
    private Button login_button;
    private TextView to_register;
    private TextView forget_pwd;
    private String phonenNumber;
    private LoginActivity activity;
    private LinearLayout page_back;
    private LoginHandler loginHandler;
    boolean go_to_main = false;

    private static class LoginHandler extends Handler {

        WeakReference<LoginActivity> weakReference;

        public LoginHandler(LoginActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity loginActivity = weakReference.get();
            if (loginActivity != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        loginActivity.saveUser(result);
                        break;
                }
            }
        }
    }

    private void saveUser(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
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
                String headpic = jsonObject.optString("headpic");
                User user = new User();
                user.setUserId(userId);
                user.setNickname(nickName);
                user.setResponseCode(code);
                user.setResponseMsg(responseMsg);
                user.setUserTel(tel);
                user.setUserToken(token);
                user.setHeadpic(headpic);
                UserManager.getInstance().setUser(user);
                if (go_to_main){
                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra("home_tab", "2");
                    startActivity(intent);
                }else{
                    finish();
                }
                LoginUtils.getInstance(activity).refreshUserInfo();
                LoginUtils.getInstance(activity).refreshTestFragmentData();
                LoginUtils.getInstance(activity).refreshLearnFragmentData();
            }else{
                Toast.makeText(activity, responseMsg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_login, true, R.color.status_bar_bg_color, false);
        activity = this;
        go_to_main = getIntent().getBooleanExtra("go_to_main", false);
        initView();
    }

    private void initView() {
        account_number_input = (EditText) findViewById(R.id.account_number_input);
        account_number_input.setOnFocusChangeListener(focusChangeListener);
        account_num_delete = (ImageView) findViewById(R.id.account_num_delete);
        account_num_delete.setOnClickListener(clickListener);
        login_password = (EditText) findViewById(R.id.login_password);
        img_pwd_show = (ImageView) findViewById(R.id.img_pwd_show);
        img_pwd_show.setOnClickListener(clickListener);
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(clickListener);
        to_register = (TextView) findViewById(R.id.to_register);
        to_register.setOnClickListener(clickListener);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        forget_pwd.setOnClickListener(clickListener);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("登录");
        page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        loginHandler = new LoginHandler(activity);
        account_number_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (account_number_input.getText().toString().trim().length() > 0) {
                    account_num_delete.setVisibility(View.VISIBLE);
                } else {
                    account_num_delete.setVisibility(View.GONE);
                }
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.account_num_delete:
                    phonenNumber = "";
                    account_number_input.setText("");
                    break;
                case R.id.img_pwd_show:
                    UIUtils.showPasswordExpress(login_password, img_pwd_show);
                    break;
                case R.id.login_button:
                    login();
                    break;
                case R.id.to_register:
                    Intent intent = new Intent(activity, RegisterActivity.class);
                    intent.putExtra("is_register", true);
                    startActivity(intent);
                    break;
                case R.id.forget_pwd:
                    Intent intent1 = new Intent(activity, RegisterActivity.class);
                    intent1.putExtra("is_register", false);
                    startActivity(intent1);
                    break;
                case R.id.page_back:
                    finish();
                    break;
            }
        }
    };

    private void login() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!judgePhoneNumberFormat()) {
            Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_password.getText().toString().trim().length() < 6){
            Toast.makeText(activity, "密码长度不小于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        Map<String, String> params = new HashMap<>();
        params.put("username", phonenNumber);
        params.put("password", login_password.getText().toString().trim());
        HttpHelper.httpRequest(UrlHelper.login(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = response;
                    loginHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!LoadingDialog.isDissMissLoading()){
                            LoadingDialog.dismissLoading();
                        }
                        Toast.makeText(activity, "服务器异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                switch (view.getId()) {
                    case R.id.account_number_input:
                        if (StringUtils.isEmpty(account_number_input.getText().toString().trim())) {
                            account_num_delete.setVisibility(View.GONE);
                        } else {
                            account_num_delete.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (view.getId()) {
                    case R.id.phone_number_imput:
                        account_num_delete.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat() {
        phonenNumber = account_number_input.getText().toString().trim();
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(phonenNumber);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }
}
