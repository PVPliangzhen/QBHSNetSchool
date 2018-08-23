package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.ProtocolCode;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.internal.http.RealResponseBody;

public class RegisterActivity extends BaseActivity {

    private RegisterActivity activity;
    private Button verify_code_btn;
    private EditText phone_number_imput;
    private RegisterHandler registerHandler;
    private String phonenumber;
    private ImageView num_delete;
    private ImageView page_back;

    private static class RegisterHandler extends Handler {

        WeakReference<RegisterActivity> weakReference;

        public RegisterHandler(RegisterActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity registerActivity = weakReference.get();
            if (registerActivity != null) {
                switch (msg.what) {
                    case 0x01:
                        try {
                            if (!LoadingDialog.isDissMissLoading()) {
                                LoadingDialog.dismissLoading();
                            }
                            String result = (String) msg.obj;
                            JSONObject jsonObject = new JSONObject(result);
                            String code = jsonObject.optString("code");
                            String responseMsg = jsonObject.optString("msg");
                            if (code.equalsIgnoreCase(ProtocolCode.CODE_1100.getValue())) {
                                Intent intent = new Intent(registerActivity, VerifyCodeAtivity.class);
                                intent.putExtra("phonenumber", registerActivity.phonenumber);
                                registerActivity.startActivity(intent);
                            } else {
                                Toast.makeText(registerActivity, responseMsg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 0x02:
                        try {
                            if (!LoadingDialog.isDissMissLoading()) {
                                LoadingDialog.dismissLoading();
                            }
                            String result = (String) msg.obj;
                            JSONObject jsonObject = new JSONObject(result);
                            String code = jsonObject.optString("code");
                            String responseMsg = jsonObject.optString("msg");
                            if (code.equalsIgnoreCase(ProtocolCode.CODE_1202.getValue())) {
                                Toast.makeText(registerActivity, responseMsg, Toast.LENGTH_SHORT).show();
                            } else {
                                registerActivity.requestVerifyCode();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_register, true, R.color.status_bar_bg_color, false);
        activity = this;
        registerHandler = new RegisterHandler(activity);
        verify_code_btn = (Button) findViewById(R.id.verify_code_btn);
        verify_code_btn.setOnClickListener(clickListener);
        phone_number_imput = (EditText) findViewById(R.id.phone_number_imput);
        phone_number_imput.setOnFocusChangeListener(focusChangeListener);
        num_delete = (ImageView) findViewById(R.id.num_delete);
        num_delete.setOnClickListener(clickListener);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("注册");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        phone_number_imput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phone_number_imput.getText().toString().trim().length() > 0) {
                    num_delete.setVisibility(View.VISIBLE);
                } else {
                    num_delete.setVisibility(View.GONE);
                }
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.verify_code_btn:
                    judgePhoneRegister();
                    break;
                case R.id.num_delete:
                    phone_number_imput.setText("");
                    phonenumber = "";
                    break;
                case R.id.page_back:
                    finish();
                    break;
            }
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                switch (view.getId()) {
                    case R.id.phone_number_imput:
                        if (StringUtils.isEmpty(phone_number_imput.getText().toString().trim())) {
                            num_delete.setVisibility(View.GONE);
                        } else {
                            num_delete.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (view.getId()) {
                    case R.id.phone_number_imput:
                        num_delete.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };

    private void judgePhoneRegister() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!judgePhoneNumberFormat()) {
            Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.isPhoneRegister(phonenumber), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x02;
                    message.obj = response;
                    registerHandler.sendMessage(message);
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

    private void requestVerifyCode() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!judgePhoneNumberFormat()) {
            Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getVerifyCode(phonenumber), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = response;
                    registerHandler.sendMessage(message);
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

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat() {
        phonenumber = phone_number_imput.getText().toString().trim();
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(phonenumber);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }
}
