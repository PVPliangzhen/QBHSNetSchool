package com.qbhsnetschool.uitls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayLoginListener;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayLiveInfo;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayLoginInfo;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.widget.ccvideo.PcLivePlayActivity;
import com.qbhsnetschool.widget.ccvideo.ReplayActivity;

public class CCVideoUtil {

    private boolean isSuccessed;
    private Context context;

    private static volatile CCVideoUtil ccVideoUtil;

    private CCVideoUtil(Context context){
        this.context = context;
    }

    public static CCVideoUtil getInstance(Context context){
        if (ccVideoUtil == null){
            synchronized (CCVideoUtil.class){
                if (ccVideoUtil == null){
                    ccVideoUtil = new CCVideoUtil(context);
                }
            }
        }
        return ccVideoUtil;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x01:
                    // 获取直播信息必须在登录成功之后再获取，否则为空
                    LiveInfo liveInfo = DWLive.getInstance().getLiveInfo();
                    if (liveInfo != null) {
                        Toast.makeText(context, "直播开始时间：" + liveInfo.getLiveStartTime() + "\n"
                                + "直播持续时间：" + liveInfo.getLiveDuration(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, PcLivePlayActivity.class);
                        context.startActivity(intent);
                    }
                    break;
                case 0x02:
                    Toast.makeText(context, "exception", Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    // 回放的直播开始时间和结束时间必须在登录成功后再获取，否则为空
                    ReplayLiveInfo replayLiveInfo = DWLiveReplay.getInstance().getReplayLiveInfo();
                    if (replayLiveInfo != null) {
                        Toast.makeText(context, "直播开始时间：" + replayLiveInfo.getStartTime() + "\n"
                                + "直播结束时间：" + replayLiveInfo.getEndTime(), Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(context, ReplayActivity.class);
                    context.startActivity(intent);
                    break;
            }
        }
    };

    public void startCCVideo(String roomId, String userId, String viewerName, String token) {
        isSuccessed = false;
        LoginInfo loginInfo = new LoginInfo();
//        loginInfo.setRoomId(roomId);
//        loginInfo.setUserId(userId);
//        loginInfo.setViewerName(viewerName);
//        loginInfo.setViewerToken(token);

        loginInfo.setRoomId("49866F9D3D04F76E9C33DC5901307461");
        loginInfo.setUserId("AA31D2BB588429C7");
        loginInfo.setViewerName("pvplz");
        loginInfo.setViewerToken("123456");

        DWLive.getInstance().setDWLiveLoginParams(new DWLiveLoginListener() {
            @Override
            public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
                isSuccessed = true;
                handler.sendEmptyMessage(0x01);
            }

            @Override
            public void onException(DWLiveException e) {
                isSuccessed = false;
                handler.sendEmptyMessage(0x02);
            }
        }, loginInfo);

        DWLive.getInstance().setSecure(true);
        DWLive.getInstance().startLogin();
    }

    public void startCCPlayBack(String roomId, String userId, String viewerName, String token, String recordId){
        // 创建登录信息
        ReplayLoginInfo replayLoginInfo = new ReplayLoginInfo();
        replayLoginInfo.setUserId(userId);
        replayLoginInfo.setRoomId(roomId);
        //replayLoginInfo.setLiveId(liveId);
        replayLoginInfo.setRecordId(recordId);
        replayLoginInfo.setViewerName(viewerName);
        replayLoginInfo.setViewerToken(token);

//        replayLoginInfo.setUserId("B27039502337407C");
//        replayLoginInfo.setRoomId("080D04CB846F0FB29C33DC5901307461");
//        replayLoginInfo.setLiveId("50743DD69A9B2C60");
//        replayLoginInfo.setRecordId("3804F642D564BE78");
//        replayLoginInfo.setViewerName("111");
//        replayLoginInfo.setViewerToken("111");

        // 设置登录参数
        DWLiveReplay.getInstance().setLoginParams(new DWLiveReplayLoginListener() {
            @Override
            public void onException(final DWLiveException exception) {
                handler.sendEmptyMessage(0x02);
            }

            @Override
            public void onLogin(TemplateInfo templateInfo) {
                handler.sendEmptyMessage(0x03);
            }
        }, replayLoginInfo);

        // 设置是否使用Https
        DWLiveReplay.getInstance().setSecure(false);

        // 执行登录操作
        DWLiveReplay.getInstance().startLogin();
    }
}
