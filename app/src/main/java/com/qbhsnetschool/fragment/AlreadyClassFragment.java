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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.AlreadyClassAdapter;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.CourseUtil;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class AlreadyClassFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private RecyclerView already_class_list;
    private AlreadyClassAdapter alreadyClassAdapter;
    private AlreadyClassHandler alreadyClassHandler;
    private ViewPagerSwipeRefreshLayout already_class_refresh;

    private static class AlreadyClassHandler extends Handler {

        WeakReference<AlreadyClassFragment> weakReference;

        public AlreadyClassHandler (AlreadyClassFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AlreadyClassFragment alreadyClassFragment = weakReference.get();
            if (alreadyClassFragment != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        alreadyClassFragment.handleAlreadyCourse(result);
                        break;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_already_class, container, false);
        initView(rootView);
        IntentFilter intentFilter = new IntentFilter("load_already_class_data");
        LoadAlreadyClassReceiver loadAlreadyClassReceiver = new LoadAlreadyClassReceiver();
        activity.registerReceiver(loadAlreadyClassReceiver, intentFilter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.getCurrentFragment() instanceof LearnFragment) {
            initData();
        }
    }

    public void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.myCourses(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                alreadyClassHandler.sendMessage(message);
            }

            @Override
            public void onFailure(int code) {
                super.onFailure(code);
                if (already_class_refresh.isRefreshing()){
                    already_class_refresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                if (already_class_refresh.isRefreshing()){
                    already_class_refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initView(View rootView) {
        already_class_list = rootView.findViewById(R.id.already_class_list);
        alreadyClassAdapter = new AlreadyClassAdapter(activity);
        LinearLayoutManager already_class_lm = new LinearLayoutManager(activity);
        already_class_lm.setOrientation(LinearLayoutManager.VERTICAL);
        already_class_list.setLayoutManager(already_class_lm);
        already_class_list.setAdapter(alreadyClassAdapter);
        already_class_refresh = rootView.findViewById(R.id.already_class_refresh);
        already_class_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        alreadyClassHandler = new AlreadyClassHandler(this);
    }

    private void handleAlreadyCourse(String result) {
        try{
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            if (already_class_refresh.isRefreshing()){
                already_class_refresh.setRefreshing(false);
            }
            if (!StringUtils.isEmpty(result)){
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.optString("code");
                if (code.equalsIgnoreCase("200")){
                    JSONArray past_courses = jsonObject.optJSONArray("past_courses");
                    Gson gson = new Gson();
                    List<CourseBean> past_courses_list = gson.fromJson(past_courses.toString(), new TypeToken<List<CourseBean>>() {}.getType());
                    JSONArray future_courses = jsonObject.optJSONArray("future_courses");
                    List<CourseBean> future_courses_list = gson.fromJson(future_courses.toString(), new TypeToken<List<CourseBean>>() {}.getType());
                    CourseUtil.setPastCourse(past_courses_list);
                    CourseUtil.setFutureCourse(future_courses_list);
                }else{
                    CourseUtil.clearData();
                    String msg = jsonObject.optString("msg");
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
                if (alreadyClassAdapter != null){
                    alreadyClassAdapter.setData(CourseUtil.getPastCourse());
                    alreadyClassAdapter.notifyDataSetChanged();
                }
                Intent intent = new Intent();
                intent.setAction("load_wait_class_data");
                activity.sendBroadcast(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("v" + isVisibleToUser);
//        if (CourseUtil.getPastCourse() != null){
//            if (alreadyClassAdapter != null){
//                alreadyClassAdapter.setData(CourseUtil.getPastCourse());
//                alreadyClassAdapter.notifyDataSetChanged();
//            }
//        }
    }

    public class LoadAlreadyClassReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (CourseUtil.getPastCourse() != null){
                if (alreadyClassAdapter != null){
                    alreadyClassAdapter.setData(CourseUtil.getPastCourse());
                    alreadyClassAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
