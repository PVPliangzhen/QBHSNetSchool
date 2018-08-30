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
import com.qbhsnetschool.adapter.WaitingClassAdapter;
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

public class WaitClassFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private RecyclerView wait_class_list;
    private WaitClassHandler waitClassHandler;
    private WaitingClassAdapter waitingClassAdapter;
    private ViewPagerSwipeRefreshLayout wait_class_refresh;

    private static class WaitClassHandler extends Handler{

        WeakReference<WaitClassFragment> weakReference;

        public WaitClassHandler (WaitClassFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            WaitClassFragment waitClassFragment = weakReference.get();
            if (waitClassFragment != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        waitClassFragment.handleWaitCourse(result);
                        break;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_wait_class, container, false);
        initView(rootView);
        initData();
        initReceiver();
        return rootView;
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter("load_wait_class_data");
        LoadWaitClassReceiver loadWaitClassReceiver = new LoadWaitClassReceiver();
        activity.registerReceiver(loadWaitClassReceiver, intentFilter);
    }

    private void initData() {
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
                waitClassHandler.sendMessage(message);
            }
        });
    }

    private void initView(View rootView) {
        wait_class_list = rootView.findViewById(R.id.wait_class_list);
        waitingClassAdapter = new WaitingClassAdapter(activity);
        LinearLayoutManager wait_class_lm = new LinearLayoutManager(activity);
        wait_class_lm.setOrientation(LinearLayoutManager.VERTICAL);
        wait_class_list.setLayoutManager(wait_class_lm);
        wait_class_list.setAdapter(waitingClassAdapter);
        wait_class_refresh = rootView.findViewById(R.id.wait_class_refresh);
        wait_class_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        waitClassHandler = new WaitClassHandler(this);
    }

    private void handleWaitCourse(String result) {
        try{
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            if (wait_class_refresh.isRefreshing()){
                wait_class_refresh.setRefreshing(false);
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
                    if (waitingClassAdapter != null){
                        waitingClassAdapter.setData(future_courses_list);
                        waitingClassAdapter.notifyDataSetChanged();
                    }
                    Intent intent = new Intent();
                    intent.setAction("load_already_class_data");
                    activity.sendBroadcast(intent);
                }else{
                    String msg = jsonObject.optString("msg");
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        System.out.println("------" + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("------v" + isVisibleToUser);
    }

    public class LoadWaitClassReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (CourseUtil.getFutureCourse() != null){
                if (waitingClassAdapter != null){
                    waitingClassAdapter.setData(CourseUtil.getFutureCourse());
                    waitingClassAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
