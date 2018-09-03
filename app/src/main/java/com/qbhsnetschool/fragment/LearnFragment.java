package com.qbhsnetschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.TabGroupLayout;

import java.lang.ref.WeakReference;

public class LearnFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;

    private static class LearnHandler extends Handler{

        WeakReference<LearnFragment> weakReference;

        public LearnHandler(LearnFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            LearnFragment learnFragment = weakReference.get();
            if (learnFragment != null) {
                switch (msg.what) {

                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_learn, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        RelativeLayout status_bar_mask = rootView.findViewById(R.id.status_bar_mask);
        int height = UIUtils.getStatusBarHeight(activity);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) status_bar_mask.getLayoutParams();
        lp.height = height;
        status_bar_mask.setLayoutParams(lp);
        status_bar_mask.setVisibility(View.VISIBLE);
        TextView page_title = rootView.findViewById(R.id.page_title);
        page_title.setText("上课");
        ImageView page_back = rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        TabGroupLayout tabGroupLayout = rootView.findViewById(R.id.tab_layout);
        ViewPager course_viewpager = rootView.findViewById(R.id.course_viewpager);
        course_viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new WaitClassFragment();
                        break;
                    case 1:
                        fragment = new AlreadyClassFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tabGroupLayout.setupViewPager(course_viewpager);
    }

//    boolean isSuccessed = false;
//    private void test(View rootView) {
//        rootView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, WebActivity.class);
//                startActivity(intent);
//                Toast.makeText(activity, "123", Toast.LENGTH_SHORT).show();
//                isSuccessed = false;
//
//                LoginInfo loginInfo = new LoginInfo();
//                loginInfo.setRoomId("49866F9D3D04F76E9C33DC5901307461");
//                loginInfo.setUserId("AA31D2BB588429C7");
//                loginInfo.setViewerName("pvplz");
//                loginInfo.setViewerToken("123");
//
//                DWLive.getInstance().setDWLiveLoginParams(new DWLiveLoginListener() {
//                    @Override
//                    public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
//                        isSuccessed = true;
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 获取直播信息必须在登录成功之后再获取，否则为空
//                                LiveInfo liveInfo = DWLive.getInstance().getLiveInfo();
//                                if (liveInfo != null) {
//                                    Toast.makeText(activity, "直播开始时间：" + liveInfo.getLiveStartTime() + "\n"
//                                            + "直播持续时间：" +  liveInfo.getLiveDuration(), Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(activity, PcLivePlayActivity.class);
//                                    startActivity(intent);
//                                }
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onException(DWLiveException e) {
//                        isSuccessed = false;
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity, "exception", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }, loginInfo);
//
//                DWLive.getInstance().setSecure(true);
//                DWLive.getInstance().startLogin();
//            }
//        });
//    }


}
