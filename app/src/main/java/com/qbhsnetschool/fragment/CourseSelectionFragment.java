package com.qbhsnetschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.BannerPagerAdapter;
import com.qbhsnetschool.adapter.CheapieAdapter;
import com.qbhsnetschool.adapter.HLGAdapter;
import com.qbhsnetschool.adapter.JianziAdapter;
import com.qbhsnetschool.adapter.PeiuAdapter;
import com.qbhsnetschool.entity.BannerBean;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerScroller;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;
import com.zyyoona7.popup.EasyPopup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CourseSelectionFragment extends Fragment {

    private HomeActivity activity;
    private View rootView;
    private ViewPagerSwipeRefreshLayout swipeRefreshLayout;

    private CourseSelectionHandler courseSelectionHandler;
    private ViewPager banner;

    private List<Integer> usingGrades = new ArrayList<>();
    private List<HomeCourseBean> cheapieBeans = new ArrayList<>();
    private List<HomeCourseBean> peiuBeans = new ArrayList<>();
    private List<HomeCourseBean> hlgBeans = new ArrayList<>();
    private List<HomeCourseBean> jianziBeans = new ArrayList<>();
    private RecyclerView discount_list_above;
    private LinearLayout discount_list_above_layout;
    private LinearLayout discount_list_bottom_layout;
    private RecyclerView discount_list_bottom;
    private RecyclerView peiu_list;
    private RecyclerView hlg_list;
    private RecyclerView jianzi_list;
    private BannerBean bannerBean;
    private TextView grade_shown_txt;
    private int screenWith;
    private int screenHeight;
    private EasyPopup gradePopup;
    private TextView[] grade_textView;
    private int currentGradeIndex = 3;
    private LinearLayout peiu_list_layout;
    private LinearLayout jianzi_list_layout;
    private LinearLayout hlg_list_layout;
    private BannerPagerAdapter bannerPagerAdapter;
    private static final int REFRESH_BANNER = 0;
    private static final int NO_REFRESH_BANNER = 1;

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
                        if (courseSelectionFragment.swipeRefreshLayout.isRefreshing()) {
                            courseSelectionFragment.swipeRefreshLayout.setRefreshing(false);
                        }
                        if (!LoadingDialog.isDissMissLoading()) {
                            LoadingDialog.dismissLoading();
                        }
                        String result = (String) msg.obj;
                        courseSelectionFragment.handleParseJson(result);
                        break;
                    case 0x03:
                        String bannerInfo = (String) msg.obj;
                        int isRefresh = msg.arg1;
                        courseSelectionFragment.handleHomeBanner(bannerInfo, isRefresh);
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
        initData(currentGradeIndex, false);
        initBannerPic(NO_REFRESH_BANNER);
        return rootView;
    }

    private void initBannerPic(final int isRefresh) {
        if (UIUtils.isNetworkAvailable(activity)) {
            HttpHelper.httpGetRequest(UrlHelper.homeBanner(), "GET", new StandardCallBack(activity) {
                @Override
                public void onSuccess(String response) {
                    try {
                        Message message = Message.obtain();
                        message.what = 0x03;
                        message.arg1 = isRefresh;
                        message.obj = response;
                        courseSelectionHandler.sendMessage(message);
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    private void initData(int gradeIndex, final boolean isRefresh) {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isRefresh) {
            LoadingDialog.loading(activity);
        }
        HttpHelper.httpGetRequest(UrlHelper.homePage(gradeIndex), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String response) {
                try {
                    Message message = Message.obtain();
                    message.what = 0x02;
                    message.obj = response;
                    courseSelectionHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code) {
                super.onFailure(code);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void initView(View rootView) {
        RelativeLayout status_bar_mask = rootView.findViewById(R.id.status_bar_mask);
        int height = UIUtils.getStatusBarHeight(activity);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) status_bar_mask.getLayoutParams();
        lp.height = height;
        status_bar_mask.setLayoutParams(lp);
        status_bar_mask.setVisibility(View.VISIBLE);
        courseSelectionHandler = new CourseSelectionHandler(this);
        swipeRefreshLayout = rootView.findViewById(R.id.home_swipe_layout);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initBannerPic(REFRESH_BANNER);
                initData(currentGradeIndex, true);
            }
        });
        discount_list_above_layout = rootView.findViewById(R.id.discount_list_above_layout);
        discount_list_bottom_layout = rootView.findViewById(R.id.discount_list_bottom_layout);
        peiu_list_layout = rootView.findViewById(R.id.peiu_list_layout);
        jianzi_list_layout = rootView.findViewById(R.id.jianzi_list_layout);
        hlg_list_layout = rootView.findViewById(R.id.hlg_list_layout);
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
        TextView page_title = rootView.findViewById(R.id.page_title);
        page_title.setText("选课");
        LinearLayout page_back = rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        LinearLayout grade_select_layout = rootView.findViewById(R.id.grade_select_layout);
        grade_select_layout.setOnClickListener(clickListener);
        grade_shown_txt = rootView.findViewById(R.id.grade_shown_txt);

        initPopup();
    }

    private void initPopup() {
        try {
            WindowManager manager = activity.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            screenWith = outMetrics.widthPixels;
            screenHeight = outMetrics.heightPixels;
            gradePopup = EasyPopup.create().setContentView(activity, R.layout.grade_select_popupp).setFocusAndOutsideEnable(true)
                    .setBackgroundDimEnable(true).setDimValue(0.4f)
                    .setWidth((int) (screenWith - getResources().getDimension(R.dimen.dp140))).apply();

            grade_textView = new TextView[12];
            grade_textView[0] = gradePopup.findViewById(R.id.yinianji);
            grade_textView[1] = gradePopup.findViewById(R.id.ernianji);
            grade_textView[2] = gradePopup.findViewById(R.id.sannianji);
            grade_textView[3] = gradePopup.findViewById(R.id.sinianji);
            grade_textView[4] = gradePopup.findViewById(R.id.wunianji);
            grade_textView[5] = gradePopup.findViewById(R.id.liunianji);
            grade_textView[6] = gradePopup.findViewById(R.id.chuyi);
            grade_textView[7] = gradePopup.findViewById(R.id.chuer);
            grade_textView[8] = gradePopup.findViewById(R.id.chusan);
            grade_textView[9] = gradePopup.findViewById(R.id.gaoyi);
            grade_textView[10] = gradePopup.findViewById(R.id.gaoer);
            grade_textView[11] = gradePopup.findViewById(R.id.gaosan);
            for (int i = 0; i < grade_textView.length; i++) {
                final int index = i;
                grade_textView[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int j = 0; j < grade_textView.length; j++) {
                            if (j == index) {
                                grade_textView[j].setBackgroundResource(R.drawable.grade_enable);
                                grade_textView[j].setTextColor(getResources().getColor(R.color.color_D40000));
                                grade_textView[j].setEnabled(true);
                                currentGradeIndex = index + 1;
                                initData(currentGradeIndex, true);
                            } else if (usingGrades.contains(j)) {
                                grade_textView[j].setBackgroundResource(R.drawable.grade_normal);
                                grade_textView[j].setTextColor(getResources().getColor(R.color.color_333333));
                                grade_textView[j].setEnabled(true);
                            } else {
                                grade_textView[j].setBackgroundResource(R.drawable.grade_normal);
                                grade_textView[j].setTextColor(getResources().getColor(R.color.color_999999));
                                grade_textView[j].setEnabled(false);
                            }
                        }

                        gradePopup.dismiss();
                        grade_shown_txt.setText(ConstantUtil.getGradeItemsPopUp().get(currentGradeIndex));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.grade_select_layout:
                    gradePopup.showAtLocation(view, Gravity.CENTER, 0, 0);
                    break;
            }
        }
    };

    private void initBanner(View rootView) {
        banner = rootView.findViewById(R.id.banner);
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(activity);
        viewPagerScroller.setScrollDuration(500);
        viewPagerScroller.initViewPagerScroll(banner);
        bannerPagerAdapter = new BannerPagerAdapter(activity);
        banner.setAdapter(bannerPagerAdapter);
        banner.setCurrentItem(1000);
    }

    private void handleParseJson(String result) {
        try {
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.optString("code");
                if (responseCode.equalsIgnoreCase("200")) {
                    JSONArray using_grade = jsonObject.optJSONArray("using_grade");
                    if (using_grade != null) {
                        usingGrades.clear();
                        for (int i = 0; i < using_grade.length(); i++) {
                            int grade = (int) using_grade.get(i);
                            usingGrades.add(grade - 1);
                        }
                        for (int i = 0; i < grade_textView.length; i++) {
                            if (usingGrades.contains(i)) {
                                grade_textView[i].setBackgroundResource(R.drawable.grade_normal);
                                grade_textView[i].setTextColor(getResources().getColor(R.color.color_333333));
                                grade_textView[i].setEnabled(true);
                            } else {
                                grade_textView[i].setBackgroundResource(R.drawable.grade_normal);
                                grade_textView[i].setTextColor(getResources().getColor(R.color.color_999999));
                                grade_textView[i].setEnabled(false);
                            }
                        }
                    }
                    grade_textView[currentGradeIndex - 1].setBackgroundResource(R.drawable.grade_enable);
                    grade_textView[currentGradeIndex - 1].setTextColor(getResources().getColor(R.color.color_D40000));
                    grade_textView[currentGradeIndex - 1].setEnabled(true);
                    JSONArray cheapie = jsonObject.optJSONArray("cheapie");
                    Gson gson = new Gson();
                    cheapieBeans = gson.fromJson(cheapie.toString(), new TypeToken<List<HomeCourseBean>>() {
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
                    peiuBeans = gson.fromJson(pu.toString(), new TypeToken<List<HomeCourseBean>>() {
                    }.getType());
                    if (peiuBeans != null && peiuBeans.size() > 0) {
                        PeiuAdapter peiuAdapter = new PeiuAdapter(activity, peiuBeans);
                        peiu_list.setAdapter(peiuAdapter);
                        peiu_list_layout.setVisibility(View.VISIBLE);
                    } else {
                        peiu_list_layout.setVisibility(View.GONE);
                    }
                    JSONArray hlg = index_list.optJSONArray("hlg");
                    hlgBeans = gson.fromJson(hlg.toString(), new TypeToken<List<HomeCourseBean>>() {
                    }.getType());
                    if (hlgBeans != null && hlgBeans.size() > 0) {
                        HLGAdapter hlgAdapter = new HLGAdapter(activity, hlgBeans);
                        hlg_list.setAdapter(hlgAdapter);
                        hlg_list_layout.setVisibility(View.VISIBLE);
                    } else {
                        hlg_list_layout.setVisibility(View.GONE);
                    }
                    JSONArray jz = index_list.optJSONArray("jz");
                    jianziBeans = gson.fromJson(jz.toString(), new TypeToken<List<HomeCourseBean>>() {
                    }.getType());
                    if (jianziBeans != null && jianziBeans.size() > 0) {
                        JianziAdapter jianziAdapter = new JianziAdapter(activity, jianziBeans);
                        jianzi_list.setAdapter(jianziAdapter);
                        jianzi_list_layout.setVisibility(View.VISIBLE);
                    } else {
                        jianzi_list_layout.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleHomeBanner(String result, int isRefresh) {
        try {
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = new JSONObject(result);
                String responseCode = jsonObject.optString("code");
                if (responseCode.equalsIgnoreCase("200")) {
                    JSONArray carousel = jsonObject.optJSONArray("carousel");
                    JSONObject bannerJson = (JSONObject) carousel.get(0);
                    Gson gson = new Gson();
                    bannerBean = gson.fromJson(bannerJson.toString(), BannerBean.class);
                    if (bannerPagerAdapter != null) {
                        bannerPagerAdapter.setBanner(bannerBean);
                        bannerPagerAdapter.notifyDataSetChanged();
                    }
                    if (isRefresh == 1) {
                        courseSelectionHandler.sendEmptyMessageDelayed(0x01, 3000);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
