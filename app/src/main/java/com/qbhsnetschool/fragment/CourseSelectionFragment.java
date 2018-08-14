package com.qbhsnetschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.BannerPagerAdapter;
import com.qbhsnetschool.adapter.CheapieAdapter;
import com.qbhsnetschool.entity.CheapieBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerScroller;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http.RealResponseBody;

public class CourseSelectionFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private ViewPagerSwipeRefreshLayout swipeRefreshLayout;

    private CourseSelectionHandler courseSelectionHandler;
    private ViewPager banner;

    private List<Integer> usingGrades = new ArrayList<>();
    private List<CheapieBean> cheapieBeans = new ArrayList<>();
    private RecyclerView discount_list_above;

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
                    case 0x02:
                        try {
                            String result = (String) msg.obj;
                            if (!StringUtils.isEmpty(result)) {
                                JSONObject jsonObject = new JSONObject(result);
                                String responseCode = jsonObject.optString("code");
                                if (responseCode.equalsIgnoreCase("200")){
                                    JSONArray using_grade = jsonObject.optJSONArray("using_grade");
                                    if (using_grade != null && using_grade.length() > 0){
                                        for (int i = 0; i < using_grade.length(); i++){
                                            int grade = (int) using_grade.get(i);
                                            courseSelectionFragment.usingGrades.add(grade);
                                        }
                                    }
                                    JSONArray cheapie = jsonObject.optJSONArray("cheapie");
                                    Gson gson = new Gson();
                                    courseSelectionFragment.cheapieBeans = gson.fromJson(cheapie.toString(), new TypeToken<List<CheapieBean>>() {}.getType());
                                    CheapieAdapter cheapieAdapter = new CheapieAdapter(courseSelectionFragment.activity, courseSelectionFragment.cheapieBeans);
                                    courseSelectionFragment.discount_list_above.setAdapter(cheapieAdapter);
                                }
                            }
                        }catch (Exception e){}
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
        initData();
        return rootView;
    }

    private void initData() {
        if (UIUtils.isNetworkAvailable(activity)){
            HttpHelper.httpGetRequest(UrlHelper.homePage(3), "GET", new Callback() {
                @Override
                public void onResponse(HttpResponse response) {
                    try {
                        if (response.code() == 200){
                            String result = (((RealResponseBody)response.body()).string());
                            Message message = Message.obtain();
                            message.what = 0x02;
                            message.obj = result;
                            courseSelectionHandler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View rootView) {
        courseSelectionHandler = new CourseSelectionHandler(this);
        swipeRefreshLayout = rootView.findViewById(R.id.home_swipe_layout);
        swipeRefreshLayout.setEnabled(false);
        initBanner(rootView);
        discount_list_above = rootView.findViewById(R.id.discount_list_above);
        LinearLayoutManager discount_list_above_lm = new LinearLayoutManager(activity);
        discount_list_above_lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        discount_list_above.setLayoutManager(discount_list_above_lm);
    }

    private void initBanner(View rootView) {
        banner = rootView.findViewById(R.id.banner);
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(activity);
        banner.setAdapter(bannerPagerAdapter);
        banner.setCurrentItem(1000);
        courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(activity);
        viewPagerScroller.setScrollDuration(500);
        viewPagerScroller.initViewPagerScroll(banner);
    }
}
