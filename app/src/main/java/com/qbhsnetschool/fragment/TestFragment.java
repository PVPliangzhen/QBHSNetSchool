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
import com.qbhsnetschool.uitls.CourseUtil;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.TabGroupLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment{
    
    private HomeActivity activity;
    private View rootView;
    private List<Fragment> testFragments = new ArrayList<>();
    private WaitTestFragment waitTestFragment;
    private AlreadyTestFragment alreadyTestFragment;

    private static class TestHandler extends Handler {

        WeakReference<TestFragment> weakReference;

        public TestHandler(TestFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            TestFragment testFragment = weakReference.get();
            if (testFragment != null) {
                switch (msg.what) {

                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_test, container, false);
        initView(rootView);
        initReceiver();
        return rootView;
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("refresh_test_fragment_data");
        RefreshTestFragmentData refreshTestFragmentData = new RefreshTestFragmentData();
        activity.registerReceiver(refreshTestFragmentData, intentFilter);
    }

    public class RefreshTestFragmentData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (waitTestFragment != null){
                waitTestFragment.initData();
            }
            if (alreadyTestFragment != null){
                alreadyTestFragment.initData();
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
        page_title.setText("测试");
        LinearLayout page_back = rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        TabGroupLayout tabGroupLayout = rootView.findViewById(R.id.test_tab_layout);
        ViewPager test_viewpager = rootView.findViewById(R.id.test_viewpager);
        waitTestFragment = new WaitTestFragment();
        alreadyTestFragment = new AlreadyTestFragment();
        testFragments.add(waitTestFragment);
        testFragments.add(alreadyTestFragment);
        test_viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (testFragments != null){
                    return testFragments.get(position);
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tabGroupLayout.setupViewPager(test_viewpager);
    }
}
