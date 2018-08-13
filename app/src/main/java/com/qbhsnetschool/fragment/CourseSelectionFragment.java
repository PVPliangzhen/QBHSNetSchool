package com.qbhsnetschool.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.BannerPagerAdapter;
import com.qbhsnetschool.widget.ViewPagerScroller;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import java.lang.ref.WeakReference;

public class CourseSelectionFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private ViewPagerSwipeRefreshLayout swipeRefreshLayout;

    private int banners [] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};
    private CourseSelectionHandler courseSelectionHandler;
    private ViewPager banner;

    private static class CourseSelectionHandler extends Handler{

        WeakReference<CourseSelectionFragment> weakReference;

        public CourseSelectionHandler(CourseSelectionFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseSelectionFragment courseSelectionFragment = weakReference.get();
            if (courseSelectionFragment != null){
                switch (msg.what){
                    case 0x01:
                        int currentItem = courseSelectionFragment.banner.getCurrentItem();
                        courseSelectionFragment.banner.setCurrentItem(currentItem + 1);
                        courseSelectionFragment.courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
                        break;
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_course_selection, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        courseSelectionHandler = new CourseSelectionHandler(this);
        swipeRefreshLayout = rootView.findViewById(R.id.home_swipe_layout);
        swipeRefreshLayout.setEnabled(false);
        initBanner(rootView);
        ScrollView home_scroll_view = rootView.findViewById(R.id.home_scroll_view);
        LinearLayout home_root_lne = rootView.findViewById(R.id.home_root_lne);
    }

    private void initBanner(View rootView) {
        banner = rootView.findViewById(R.id.banner);
        LinearLayout banner_dot = rootView.findViewById(R.id.banner_dot);
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(activity);
        banner.setAdapter(bannerPagerAdapter);
        banner.setCurrentItem(1000);
        courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(activity);
        viewPagerScroller.setScrollDuration(500);
        viewPagerScroller.initViewPagerScroll(banner);
    }
}
