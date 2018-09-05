package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.app.QBHSApplication;
import com.qbhsnetschool.uitls.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.zyyoona7.popup.EasyPopup;

import org.w3c.dom.Text;

import static com.qbhsnetschool.R.id.cancel;
import static com.qbhsnetschool.R.id.reward_progress;
import static com.qbhsnetschool.R.id.swipe;

public class ShareForComeActivity extends BaseActivity {

    private ShareForComeActivity activity;
    private EasyPopup sharePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_share_for_come, false, R.color.status_bar_bg_color, false);
        activity = this;
        initView();
    }

    public void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("转介绍活动");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        initPopup();
        TextView referal_txt1 = (TextView) findViewById(R.id.referal_txt1);
        referal_txt1.setText(Html.fromHtml("邀请新学员，新学员可享秋季班<font color =" + getResources().getColor(R.color.color_E20000) + ">500元</font>立减"));
        TextView referal_txt2 = (TextView) findViewById(R.id.referal_txt2);
        referal_txt2.setText(Html.fromHtml("您可获得<font color =" + getResources().getColor(R.color.color_E20000) + "><big>100元</big></font>现金或<font color =" + getResources().getColor(R.color.color_E20000) + "><big>200元</big></font>优惠券"));
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

    private void share(int scene){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.tiantiandongnao.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "清北华数邀您装B";
        msg.description = "清北华数邀您装B清北华数邀您装B清北华数邀您装B清北华数邀您装B清北华数邀您装B清北华数邀您装B";
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
