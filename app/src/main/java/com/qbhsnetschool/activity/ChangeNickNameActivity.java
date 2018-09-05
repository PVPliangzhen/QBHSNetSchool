package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangeNickNameActivity extends BaseActivity {

    private ImageView delete_nickname;
    private EditText change_nickname_txt;
    private ChangeNickNameActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_change_nickname, true, R.color.status_bar_bg_color, false);
        activity = this;
        TextView page_title = (TextView) findViewById(R.id.page_title);
        TextView title_right_txt = (TextView) findViewById(R.id.title_right_txt);
        page_title.setText("修改昵称");
        title_right_txt.setText("保存");
        title_right_txt.setVisibility(View.VISIBLE);
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_right_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isEmpty(change_nickname_txt.getText().toString().trim())) {
                    Toast.makeText(activity, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!UIUtils.isNetworkAvailable(activity)) {
                    Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingDialog.loading(activity);
                Map<String, String> params = new HashMap<>();
                params.put("nickname", change_nickname_txt.getText().toString().trim());
                HttpHelper.httpRequest(UrlHelper.getPersonalInfo(), params, "PATCH", new StandardCallBack(activity) {
                    @Override
                    public void onSuccess(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    String responseCode = jsonObject.optString("code");
                                    if (responseCode.equalsIgnoreCase("200")) {
                                        if (!LoadingDialog.isDissMissLoading()) {
                                            LoadingDialog.dismissLoading();
                                        }
                                        Toast.makeText(activity, "修改成功", Toast.LENGTH_SHORT).show();
                                        setResult(0x12);
                                        activity.finish();
                                    } else {
                                        Toast.makeText(activity, "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });

        delete_nickname = (ImageView) findViewById(R.id.delete_nickname);
        delete_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_nickname_txt.setText("");
            }
        });
        change_nickname_txt = (EditText) findViewById(R.id.change_nickname_txt);
        change_nickname_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (change_nickname_txt.getText().toString().trim().length() > 0) {
                    delete_nickname.setVisibility(View.VISIBLE);
                } else {
                    delete_nickname.setVisibility(View.GONE);
                }
            }
        });

        change_nickname_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    switch (view.getId()) {
                        case R.id.change_nickname_txt:
                            if (StringUtils.isEmpty(change_nickname_txt.getText().toString().trim())) {
                                delete_nickname.setVisibility(View.GONE);
                            } else {
                                delete_nickname.setVisibility(View.VISIBLE);
                            }
                            break;
                    }
                } else {
                    switch (view.getId()) {
                        case R.id.change_nickname_txt:
                            delete_nickname.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }
}
