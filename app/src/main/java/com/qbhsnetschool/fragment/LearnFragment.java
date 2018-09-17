package com.qbhsnetschool.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.TabGroupLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LearnFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private List<Fragment> learnFragments = new ArrayList<>();
    private WaitClassFragment waitClassFragment;
    private AlreadyClassFragment alreadyClassFragment;

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
        initReceiver();
        return rootView;
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("refresh_learn_fragment_data");
        RefreshLearnFragmentData refreshTestFragmentData = new RefreshLearnFragmentData();
        activity.registerReceiver(refreshTestFragmentData, intentFilter);
    }

    public class RefreshLearnFragmentData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (waitClassFragment != null){
                waitClassFragment.initData();
            }
        }
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
        LinearLayout page_back = rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        TabGroupLayout tabGroupLayout = rootView.findViewById(R.id.tab_layout);
        ViewPager course_viewpager = rootView.findViewById(R.id.course_viewpager);
        waitClassFragment = new WaitClassFragment();
        alreadyClassFragment = new AlreadyClassFragment();
        learnFragments.add(waitClassFragment);
        learnFragments.add(alreadyClassFragment);
        course_viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (learnFragments != null){
                    return learnFragments.get(position);
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tabGroupLayout.setupViewPager(course_viewpager);
    }
}
