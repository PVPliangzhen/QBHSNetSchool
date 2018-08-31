package com.qbhsnetschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.activity.MyCouponActivity;
import com.qbhsnetschool.activity.MyOrderActivity;
import com.qbhsnetschool.activity.UserInfoActivity;
import com.qbhsnetschool.entity.PersonalInfo;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.qbhsnetschool.uitls.GlideCircleTransform;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.xiaoneng.api.Ntalker;
import cn.xiaoneng.manager.bean.ChatParamsBody;
import cn.xiaoneng.manager.inf.outer.NtalkerCoreCallback;

public class MineFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private CardView user_card;
    private Button login_out;
    private MineHandler mineHandler;
    private ImageView user_avatar;
    private TextView user_phone;

    private static class MineHandler extends Handler{

        WeakReference<MineFragment> weakReference;

        public MineHandler(MineFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MineFragment mineFragment = weakReference.get();
            if (mineFragment != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        mineFragment.handlePersonalInfo(result);
                        break;
                }
            }
        }
    }

    private void handlePersonalInfo(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            PersonalInfo personalInfo = gson.fromJson(jsonObject.toString(), PersonalInfo.class);
            user_phone.setText(subStringPhoneNumber(personalInfo.getTel()));
            Glide.with(activity).load(personalInfo.getHeadpic()).asBitmap().placeholder(R.mipmap.avatars).error(R.mipmap.avatars).transform(new GlideCircleTransform(activity, personalInfo.getHeadpic())).into(user_avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_mine, container, false);
        mineHandler = new MineHandler(this);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        LoadingDialog.loading(activity);
        if (UIUtils.isNetworkAvailable(activity)){
            HttpHelper.httpGetRequest(UrlHelper.getPersonalInfo(), "GET", new StandardCallBack(activity) {
                @Override
                public void onSuccess(String result) {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = result;
                    mineHandler.sendMessage(message);
                }
            });
        }else{
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View rootView) {
        TextView page_title = (TextView) rootView.findViewById(R.id.page_title);
        page_title.setText("我的");
        ImageView page_back = (ImageView) rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        user_card = rootView.findViewById(R.id.user_card);
        user_card.setOnClickListener(clickListener);
        RelativeLayout order_layout = rootView.findViewById(R.id.order_layout);
        order_layout.setOnClickListener(clickListener);
        RelativeLayout coupon_layout = rootView.findViewById(R.id.coupon_layout);
        coupon_layout.setOnClickListener(clickListener);
        RelativeLayout service_layout = rootView.findViewById(R.id.service_layout);
        service_layout.setOnClickListener(clickListener);
        login_out = rootView.findViewById(R.id.login_out);
        login_out.setOnClickListener(clickListener);
        ViewPagerSwipeRefreshLayout mine_swipe_layout = rootView.findViewById(R.id.mine_swipe_layout);
        mine_swipe_layout.setEnabled(false);
        user_avatar = rootView.findViewById(R.id.user_avatar);
        user_phone = rootView.findViewById(R.id.user_phone);
        user_phone.setText(subStringPhoneNumber(UserManager.getInstance().getUser().getUserTel()));
    }

    private String subStringPhoneNumber(String mobile){
        String maskNumber = mobile;
        if (judgePhoneNumberFormat(mobile)) {
            maskNumber = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        }
        return maskNumber;
    }

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat(String mobile) {
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(mobile);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.user_card:
                    Intent intent = new Intent(activity, UserInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.order_layout:
                    Intent intent1 = new Intent(activity, MyOrderActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.coupon_layout:
                    Intent intent2 = new Intent(activity, MyCouponActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.service_layout:
                    String userId = String.valueOf(UserManager.getInstance().getUser().getUserId());
                    String userName = UserManager.getInstance().getUser().getUserTel();
                    Ntalker.getInstance().login(userId, userName, new NtalkerCoreCallback() {

                        @Override
                        public void successed() {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ChatParamsBody chatParamsBody = new ChatParamsBody();
                                    chatParamsBody.settingId = "kf_20013_template_5";
                                    Ntalker.getInstance().startChat(activity, chatParamsBody);
                                    //Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void failed(final int errorcode) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "登录失败" + errorcode, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    break;
                case R.id.login_out:
                    UserManager.getInstance().clearUser();
                    activity.clickCourseTab();
                    break;
            }
        }
    };
}
