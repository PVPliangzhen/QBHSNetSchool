package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.qbhsnetschool.R;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.entity.ZhuanjieshaoBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.uitls.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.zyyoona7.popup.EasyPopup;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import static com.qbhsnetschool.R.id.cancel;
import static com.qbhsnetschool.R.id.reward_progress;
import static com.qbhsnetschool.R.id.swipe;

public class ShareForComeActivity extends BaseActivity {

    private ShareForComeActivity activity;
    private EasyPopup sharePopup;
    private ShareForComeHandler shareForComeHandler;
    private ZhuanjieshaoBean zhuanjieshaoBean;
    private TextView referal_txt1;
    private TextView referal_txt2;
    private EasyPopup erweimaPopup;
    private ImageView erweima_img;

    private static class ShareForComeHandler extends Handler {
        WeakReference<ShareForComeActivity> weakReference;

        public ShareForComeHandler(ShareForComeActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShareForComeActivity shareForComeActivity = weakReference.get();
            if (shareForComeActivity != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        shareForComeActivity.handleZhuanjieshao(result);
                        break;
                }
            }
        }
    }

    private void handleZhuanjieshao(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()) {
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")) {
                Gson gson = new Gson();
                zhuanjieshaoBean = gson.fromJson(jsonObject.toString(), ZhuanjieshaoBean.class);
                referal_txt1.setText(Html.fromHtml("邀请新学员，新学员可享" + zhuanjieshaoBean.getCoupon3().getType() + "<font color =" + getResources().getColor(R.color.color_E20000) + ">" + zhuanjieshaoBean.getCoupon3().getAmount() + "</font>立减"));
                referal_txt2.setText(Html.fromHtml("您可获得<font color =" + getResources().getColor(R.color.color_E20000) + "><big>" + zhuanjieshaoBean.getCash().getAmount() + "</big></font>现金或<font color =" + getResources().getColor(R.color.color_E20000) + "><big>" + zhuanjieshaoBean.getCoupon4().getAmount() + "</big></font>优惠券"));
                Glide.with(activity).load(zhuanjieshaoBean.getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        erweima_img.setImageBitmap(resource);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_share_for_come, false, R.color.status_bar_bg_color, false);
        activity = this;
        shareForComeHandler = new ShareForComeHandler(activity);
        initView();
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.zhuanjieshao(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                shareForComeHandler.sendMessage(message);
            }
        });
    }

    public void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("转介绍活动");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        initPopup();
        initErweimaPopup();
        referal_txt1 = (TextView) findViewById(R.id.referal_txt1);
        //referal_txt1.setText(Html.fromHtml("邀请新学员，新学员可享秋季班<font color =" + getResources().getColor(R.color.color_E20000) + ">500元</font>立减"));
        referal_txt2 = (TextView) findViewById(R.id.referal_txt2);
        //referal_txt2.setText(Html.fromHtml("您可获得<font color =" + getResources().getColor(R.color.color_E20000) + "><big>100元</big></font>现金或<font color =" + getResources().getColor(R.color.color_E20000) + "><big>200元</big></font>优惠券"));
        final Button invite_friend = (Button) findViewById(R.id.invite_friend);
        invite_friend.setOnClickListener(clickListener);
        TextView reward_rule = (TextView) findViewById(R.id.reward_rule);
        reward_rule.setOnClickListener(clickListener);
        TextView reward_progress = (TextView) findViewById(R.id.reward_progress);
        reward_progress.setOnClickListener(clickListener);
    }

    private void initPopup() {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWith = outMetrics.widthPixels;
        sharePopup = EasyPopup.create().setContentView(activity, R.layout.share_popup).
                setFocusAndOutsideEnable(true).setBackgroundDimEnable(true).
                setDimValue(0.4f).setWidth(screenWith).apply();
        TextView cancel_share = sharePopup.findViewById(R.id.cancel_share);
        cancel_share.setOnClickListener(clickListener);
        LinearLayout share_erweima_layout = sharePopup.findViewById(R.id.share_erweima_layout);
        share_erweima_layout.setOnClickListener(clickListener);
        LinearLayout share_pengyouquan_layout = sharePopup.findViewById(R.id.share_pengyouquan_layout);
        share_pengyouquan_layout.setOnClickListener(clickListener);
        LinearLayout share_weixin_layout = sharePopup.findViewById(R.id.share_weixin_layout);
        share_weixin_layout.setOnClickListener(clickListener);
    }

    private void initErweimaPopup() {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWith = outMetrics.widthPixels;
        erweimaPopup = EasyPopup.create().setContentView(activity, R.layout.erweima_layout).
                setFocusAndOutsideEnable(true).setBackgroundDimEnable(true).
                setDimValue(0.4f).setWidth((int) (screenWith - getResources().getDimension(R.dimen.dp150)))
                .setHeight((int) (screenWith - getResources().getDimension(R.dimen.dp150))).apply();
        erweima_img = erweimaPopup.findViewById(R.id.erweima_img);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.page_back:
                    finish();
                    break;
                case R.id.invite_friend:
                    sharePopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.cancel_share:
                    sharePopup.dismiss();
                    break;
                case R.id.share_erweima_layout:
                    sharePopup.dismiss();
                    erweimaPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
                    break;
                case R.id.share_pengyouquan_layout:
                    share(wxSceneTimeline);
                    break;
                case R.id.share_weixin_layout:
                    share(wxSceneSession);
                    break;
                case R.id.reward_progress:
                    Intent intent = new Intent(activity, RewardProgressActivity.class);
                    startActivity(intent);
                    break;
                case R.id.reward_rule:
                    break;
            }
        }
    };

    private void share(int scene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = zhuanjieshaoBean.getWx().getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = zhuanjieshaoBean.getWx().getTitle();
        msg.description = zhuanjieshaoBean.getWx().getTitle_detail();
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.share_icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = scene;
        QBHSApplication application = (QBHSApplication) getApplicationContext();
        application.iwxapi.sendReq(req);
        sharePopup.dismiss();
    }

    private int wxSceneSession = SendMessageToWX.Req.WXSceneSession;
    private int wxSceneTimeline = SendMessageToWX.Req.WXSceneTimeline;

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
