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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.httputils.Callback;
import com.httputils.HttpResponse;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.BannerPagerAdapter;
import com.qbhsnetschool.adapter.CheapieAdapter;
import com.qbhsnetschool.adapter.HLGAdapter;
import com.qbhsnetschool.adapter.JianziAdapter;
import com.qbhsnetschool.adapter.PeiuAdapter;
import com.qbhsnetschool.entity.BannerBean;
import com.qbhsnetschool.entity.CheapieBean;
import com.qbhsnetschool.entity.HLGBean;
import com.qbhsnetschool.entity.JianziBean;
import com.qbhsnetschool.entity.PeiuBean;
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

public class CourseSelectionFragment extends Fragment {

    private HomeActivity activity;
    private View rootView;
    private ViewPagerSwipeRefreshLayout swipeRefreshLayout;

    private CourseSelectionHandler courseSelectionHandler;
    private ViewPager banner;

    private List<Integer> usingGrades = new ArrayList<>();
    private List<CheapieBean> cheapieBeans = new ArrayList<>();
    private List<PeiuBean> peiuBeans = new ArrayList<>();
    private List<HLGBean> hlgBeans = new ArrayList<>();
    private List<JianziBean> jianziBeans = new ArrayList<>();
    private RecyclerView discount_list_above;
    private LinearLayout discount_list_above_layout;
    private LinearLayout discount_list_bottom_layout;
    private RecyclerView discount_list_bottom;
    private RecyclerView peiu_list;
    private RecyclerView hlg_list;
    private RecyclerView jianzi_list;
    private BannerBean bannerBean;

    private static class CourseSelectionHandler extends Handler {

        WeakReference<CourseSelectionFragment> weakReference;

        public CourseSelectionHandler(CourseSelectionFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseSelectionFragment courseSelectionFragment = weakReference.get();
            if (courseSelectionFragment != null) {
                switch (msg.what) {
                    case 0x01:
                        int currentItem = courseSelectionFragment.banner.getCurrentItem();
                        courseSelectionFragment.banner.setCurrentItem(currentItem + 1);
                        courseSelectionFragment.courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
                        break;
                    case 0x02:
                        String result = (String) msg.obj;
                        courseSelectionFragment.handleParseJson(result);
                        break;
                    case 0x03:
                        String bannerInfo = (String) msg.obj;
                        courseSelectionFragment.handleHomeBanner(bannerInfo);
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
        initBannerPic();
        return rootView;
    }

    private void initBannerPic() {
        if (UIUtils.isNetworkAvailable(activity)){
            HttpHelper.httpGetRequest(UrlHelper.homeBanner(), "GET", new Callback() {
                @Override
                public void onResponse(HttpResponse response) {
                    try {
                        if (response.code() == 200){
                            String result = (((RealResponseBody) response.body()).string());
                            Message message = Message.obtain();
                            message.what = 0x03;
                            message.obj = result;
                            courseSelectionHandler.sendMessage(message);
                        }
                    }catch (Exception e){}
                }
            });
        }
    }

    private void initData() {
        if (UIUtils.isNetworkAvailable(activity)) {
            HttpHelper.httpGetRequest(UrlHelper.homePage(3), "GET", new Callback() {
                @Override
                public void onResponse(HttpResponse response) {
                    try {
                        if (response.code() == 200) {
                            String result = (((RealResponseBody) response.body()).string());
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
        } else {
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View rootView) {
        courseSelectionHandler = new CourseSelectionHandler(this);
        swipeRefreshLayout = rootView.findViewById(R.id.home_swipe_layout);
        swipeRefreshLayout.setEnabled(false);
        discount_list_above_layout = rootView.findViewById(R.id.discount_list_above_layout);
        discount_list_bottom_layout = rootView.findViewById(R.id.discount_list_bottom_layout);
        initBanner(rootView);
        discount_list_above = rootView.findViewById(R.id.discount_list_above);
        LinearLayoutManager discount_list_above_lm = new LinearLayoutManager(activity);
        discount_list_above_lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        discount_list_above.setLayoutManager(discount_list_above_lm);
        discount_list_bottom = rootView.findViewById(R.id.discount_list_bottom);
        LinearLayoutManager discount_list_bottom_lm = new LinearLayoutManager(activity);
        discount_list_bottom_lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        discount_list_bottom.setLayoutManager(discount_list_bottom_lm);
        peiu_list = rootView.findViewById(R.id.peiu_list);
        LinearLayoutManager peiu_list_lm = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        peiu_list_lm.setOrientation(LinearLayoutManager.VERTICAL);
        peiu_list.setLayoutManager(peiu_list_lm);
        hlg_list = rootView.findViewById(R.id.hlg_list);
        LinearLayoutManager hlg_list_lm = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        hlg_list_lm.setOrientation(LinearLayoutManager.VERTICAL);
        hlg_list.setLayoutManager(hlg_list_lm);
        jianzi_list = rootView.findViewById(R.id.jianzi_list);
        LinearLayoutManager jianzi_list_lm = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        jianzi_list_lm.setOrientation(LinearLayoutManager.VERTICAL);
        jianzi_list.setLayoutManager(jianzi_list_lm);
    }

    private void initBanner(View rootView) {
        banner = rootView.findViewById(R.id.banner);
        banner.setCurrentItem(1000);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(activity);
        viewPagerScroller.setScrollDuration(500);
        viewPagerScroller.initViewPagerScroll(banner);
    }

    private void handleParseJson(String result){
        try {
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.optString("code");
                if (responseCode.equalsIgnoreCase("200")) {
                    JSONArray using_grade = jsonObject.optJSONArray("using_grade");
                    if (using_grade != null && using_grade.length() > 0) {
                        for (int i = 0; i < using_grade.length(); i++) {
                            int grade = (int) using_grade.get(i);
                            usingGrades.add(grade);
                        }
                    }
                    JSONArray cheapie = jsonObject.optJSONArray("cheapie");
                    Gson gson = new Gson();
                    cheapieBeans = gson.fromJson(cheapie.toString(), new TypeToken<List<CheapieBean>>() {
                    }.getType());
                    if (cheapieBeans != null && cheapieBeans.size() > 0) {
                        CheapieAdapter cheapieAdapter = new CheapieAdapter(activity, cheapieBeans);
                        discount_list_above.setAdapter(cheapieAdapter);
                        discount_list_above_layout.setVisibility(View.VISIBLE);
                    } else {
                        discount_list_above_layout.setVisibility(View.GONE);
                    }
//                  SanQiLianBaoAdapter sanQiLianBaoAdapter = new SanQiLianBaoAdapter(courseSelectionFragment.activity);
//                  courseSelectionFragment.discount_list_bottom.setAdapter(sanQiLianBaoAdapter);
                    JSONObject index_list = jsonObject.optJSONObject("index_list");
                    JSONArray pu = index_list.optJSONArray("pu");
                    peiuBeans = gson.fromJson(pu.toString(), new TypeToken<List<PeiuBean>>() {
                    }.getType());
                    if (peiuBeans != null && peiuBeans.size() > 0) {
                        PeiuAdapter peiuAdapter = new PeiuAdapter(activity, peiuBeans);
                        peiu_list.setAdapter(peiuAdapter);
                    }
                    JSONArray hlg = index_list.optJSONArray("hlg");
                    hlgBeans = gson.fromJson(hlg.toString(), new TypeToken<List<HLGBean>>() {
                    }.getType());
                    if (hlgBeans != null && hlgBeans.size() > 0) {
                        HLGAdapter hlgAdapter = new HLGAdapter(activity, hlgBeans);
                        hlg_list.setAdapter(hlgAdapter);
                    }
                    JSONArray jz = index_list.optJSONArray("jz");
                    jianziBeans = gson.fromJson(jz.toString(), new TypeToken<List<JianziBean>>() {
                    }.getType());
                    if (jianziBeans != null && jianziBeans.size() > 0) {
                        JianziAdapter jianziAdapter = new JianziAdapter(activity, jianziBeans);
                        jianzi_list.setAdapter(jianziAdapter);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleHomeBanner(String result){
        try {
            if (!StringUtils.isEmpty(result)){
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.optString("code");
                if (responseCode.equalsIgnoreCase("200")) {
                    JSONArray carousel = jsonObject.optJSONArray("carousel");
                    JSONObject bannerJson = (JSONObject) carousel.get(0);
                    Gson gson = new Gson();
                    bannerBean = gson.fromJson(bannerJson.toString(), BannerBean.class);
                    BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(activity, bannerBean);
                    banner.setAdapter(bannerPagerAdapter);
                    courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
