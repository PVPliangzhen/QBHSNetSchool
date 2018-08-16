package com.qbhsnetschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.widget.ccvideo.PcLivePlayActivity;

public class LearnFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(activity, PcLivePlayActivity.class);
            startActivity(intent);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_learn, container, false);
        initView(rootView);
        return rootView;
    }

    boolean isSuccessed = false;
    private void initView(View rootView) {
        rootView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "123", Toast.LENGTH_SHORT).show();
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 获取直播信息必须在登录成功之后再获取，否则为空
                                LiveInfo liveInfo = DWLive.getInstance().getLiveInfo();
                                if (liveInfo != null) {
                                    Toast.makeText(activity, "直播开始时间：" + liveInfo.getLiveStartTime() + "\n"
                                            + "直播持续时间：" +  liveInfo.getLiveDuration(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(activity, PcLivePlayActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @Override
                    public void onException(DWLiveException e) {
                        isSuccessed = false;
                        getActivity().runOnUiThread(new Runnable() {
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
        });
    }


}
