package com.qbhsnetschool.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RewardProgressActivity extends BaseActivity{

    private RewardProgressActivity activity;
    private ImageView cash_img;
    private ImageView coupon_img;
    private RewardProgressHandler rewardProgressHandler;
    private TextView yiyaoqing;
    private TextView yiwancheng;
    private TextView release_times;
    private TextView yilingquyouhuiquan;
    private TextView yilingqujine;
    private String left_times;

    private static class RewardProgressHandler extends Handler{
        WeakReference<RewardProgressActivity> weakReference;

        public RewardProgressHandler(RewardProgressActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RewardProgressActivity rewardProgressActivity = weakReference.get();
            if (rewardProgressActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        rewardProgressActivity.handleJianglijindu(result);
                        break;
                }
            }
        }
    }

    private void handleJianglijindu(String result){
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                left_times = jsonObject.optString("left_times");
                yiyaoqing.setText("已邀请" + jsonObject.optString("has_called"));
                yiwancheng.setText("已完成" + jsonObject.optString("has_done"));
                release_times.setText(Html.fromHtml("领取次数还剩<font color =" + getResources().getColor(R.color.color_E20000) + "><big>" + left_times + "</big></font>次"));
                yilingqujine.setText("￥" + jsonObject.optString("cash"));
                yilingquyouhuiquan.setText("￥" + jsonObject.optString("coupon"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reward_progress, true, R.color.status_bar_bg_color, false);
        activity = this;
        rewardProgressHandler = new RewardProgressHandler(activity);
        initView();
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.jianglijindu(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                rewardProgressHandler.sendMessage(message);
            }
        });
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("奖励进度");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        release_times = (TextView) findViewById(R.id.release_times);
        //release_times.setText(Html.fromHtml("领取次数还剩<font color =" + getResources().getColor(R.color.color_E20000) + "><big>0</big></font>次"));
        coupon_img = (ImageView) findViewById(R.id.coupon_img);
        coupon_img.setOnClickListener(clickListener);
        cash_img = (ImageView) findViewById(R.id.cash_img);
        cash_img.setOnClickListener(clickListener);
        yiyaoqing = (TextView) findViewById(R.id.yiyaoqing);
        yiwancheng = (TextView) findViewById(R.id.yiwancheng);
        yilingqujine = (TextView) findViewById(R.id.yilingqujine);
        yilingquyouhuiquan = (TextView) findViewById(R.id.yilingquyouhuiquan);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.coupon_img:
                    if (Integer.parseInt(left_times) <= 0){
                        setAnimation(coupon_img, false);
                        Toast.makeText(activity, "您没有可用的领取次数", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        setAnimation(coupon_img, true);
                        lingquJiangli("coupon");
                    }
                    break;
                case R.id.cash_img:
                    if (Integer.parseInt(left_times) <= 0){
                        setAnimation(cash_img, false);
                        Toast.makeText(activity, "您没有可用的领取次数", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        setAnimation(cash_img, true);
                        lingquJiangli("cash");
                    }
                    break;
            }
        }
    };

    private void lingquJiangli(String choice) {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        Map<String, String> params = new HashMap<>();
        params.put("choice", choice);
        HttpHelper.httpRequest(UrlHelper.jianglijindu(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.optString("code");
                    if (code.equalsIgnoreCase("200")){
                        initData();
                    }else{
                        if (!LoadingDialog.isDissMissLoading()){
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setAnimation(ImageView imageView, boolean haveLeftTimes){
        if (!haveLeftTimes){
            AnimatorSet animatorSet1 = new AnimatorSet();
            ObjectAnimator translateX = ObjectAnimator.ofFloat(imageView, "translationX", 0f, -20f, 0, 20f, -10f, 0, 10f, 0);
            animatorSet1.setDuration(800);
            animatorSet1.play(translateX);
            animatorSet1.start();
        }else{
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.7f, 1.3f, 1.0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.7f, 1.3f, 1.0f);
            animatorSet.setDuration(800);
            animatorSet.play(scaleX).with(scaleY);
            animatorSet.start();
        }
    }
}
