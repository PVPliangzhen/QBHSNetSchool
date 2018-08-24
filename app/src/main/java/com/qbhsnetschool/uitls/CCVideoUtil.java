package com.qbhsnetschool.uitls;

import android.content.Intent;
import android.widget.Toast;

import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.LiveInfo;
import com.bokecc.sdk.mobile.live.pojo.LoginInfo;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.widget.ccvideo.PcLivePlayActivity;

public class CCVideoUtil {

    private boolean isSuccessed;
    private HomeActivity activity;

    private static volatile CCVideoUtil ccVideoUtil;

    private CCVideoUtil(HomeActivity activity){
        this.activity = activity;
    }

    public static CCVideoUtil getInstance(HomeActivity activity){
        if (ccVideoUtil == null){
            synchronized (CCVideoUtil.class){
                if (ccVideoUtil == null){
                    ccVideoUtil = new CCVideoUtil(activity);
                }
            }
        }
        return ccVideoUtil;
    }


    public void startCCVideo() {
        isSuccessed = false;
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setRoomId("49866F9D3D04F76E9C33DC5901307461");
        loginInfo.setUserId("AA31D2BB588429C7");
        loginInfo.setViewerName("pvplz");
        loginInfo.setViewerToken("123");

        DWLive.getInstance().setDWLiveLoginParams(new DWLiveLoginListener() {
            @Override
            public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
                isSuccessed = true;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取直播信息必须在登录成功之后再获取，否则为空
                        LiveInfo liveInfo = DWLive.getInstance().getLiveInfo();
                        if (liveInfo != null) {
                            Toast.makeText(activity, "直播开始时间：" + liveInfo.getLiveStartTime() + "\n"
                                    + "直播持续时间：" + liveInfo.getLiveDuration(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, PcLivePlayActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onException(DWLiveException e) {
                isSuccessed = false;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "exception", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, loginInfo);

        DWLive.getInstance().setSecure(true);
        DWLive.getInstance().startLogin();
    }
}