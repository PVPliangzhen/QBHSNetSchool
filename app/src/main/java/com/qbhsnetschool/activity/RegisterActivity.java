package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.ProtocolCode;
import com.qbhsnetschool.protocol.UrlHelper;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import okhttp3.internal.http.RealResponseBody;

public class RegisterActivity extends BaseActivity{

    private RegisterActivity activity;
    private Button verify_code_btn;
    private EditText editText;
    private RegisterHandler registerHandler;

    private static class RegisterHandler extends Handler{

        WeakReference<RegisterActivity> weakReference;

        public RegisterHandler(RegisterActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity registerActivity = weakReference.get();
            if (registerActivity != null){
                switch (msg.what){
                    case 0x01:
                        try {
                            String result = (String) msg.obj;
                            JSONObject jsonObject = new JSONObject(result);
                            String code = jsonObject.optString("code");
                            String responseMsg = jsonObject.optString("msg");
                            if (code.equalsIgnoreCase(ProtocolCode.CODE_1100.name())){

                            }else{
                                Toast.makeText(registerActivity, responseMsg, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_register);
        activity = this;
        registerHandler = new RegisterHandler(activity);
        verify_code_btn = (Button) findViewById(R.id.verify_code_btn);
        verify_code_btn.setOnClickListener(clickListener);
        editText = (EditText) findViewById(R.id.phone_number_imput);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.verify_code_btn:
                    String phoneNumber = editText.getText().toString().trim();
                    HttpHelper.httpGetRequest(UrlHelper.getVerifyCode(phoneNumber), "GET", new Callback() {
                        @Override
                        public void onResponse(HttpResponse response) {
                            try {
                                if (response.code() == 200){
                                    String result = (((RealResponseBody) response.body()).string());
                                    Message message = Message.obtain();
                                    message.what = 0x01;
                                    message.obj = result;
                                    registerHandler.sendMessage(message);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
            }
        }
    };
}
