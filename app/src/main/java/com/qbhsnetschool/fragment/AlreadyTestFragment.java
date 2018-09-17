package com.qbhsnetschool.fragment;

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
import com.qbhsnetschool.adapter.AlreadyTestAdapter;
import com.qbhsnetschool.entity.AlreadyTestBean;
import com.qbhsnetschool.entity.TestBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AlreadyTestFragment extends Fragment{

    private HomeActivity activity;
    private AlreadyTestHandler alreadyTestHandler;
    private List<AlreadyTestBean> alreadyTestBeans = new ArrayList<>();
    private AlreadyTestAdapter alreadyTestAdapter;
    private ViewPagerSwipeRefreshLayout already_test_refresh;

    private static class AlreadyTestHandler extends Handler{

        WeakReference<AlreadyTestFragment> weakReference;

        public AlreadyTestHandler(AlreadyTestFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            AlreadyTestFragment alreadyTestFragment = weakReference.get();
            if (alreadyTestFragment != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        alreadyTestFragment.handleExams(result);
                        break;
                }
            }
        }
    }

    private void handleExams(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            if (already_test_refresh.isRefreshing()){
                already_test_refresh.setRefreshing(false);
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                JSONArray msg = jsonObject.optJSONArray("msg");
                Gson gson = new Gson();
                alreadyTestBeans = gson.fromJson(msg.toString(), new TypeToken<List<AlreadyTestBean>>(){}.getType());
            }else{
                String msg = jsonObject.optString("msg");
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
            if (alreadyTestAdapter != null){
                alreadyTestAdapter.setData(alreadyTestBeans);
                alreadyTestAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        View rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_already_test, container, false);
        alreadyTestHandler = new AlreadyTestHandler(this);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activity.getCurrentFragment() instanceof TestFragment) {
            initData();
        }
    }

    private void initView(View rootView) {
        RecyclerView already_test_list = rootView.findViewById(R.id.already_test_list);
        alreadyTestAdapter = new AlreadyTestAdapter(activity);
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        already_test_list.setLayoutManager(lm);
        already_test_list.setAdapter(alreadyTestAdapter);
        already_test_refresh = rootView.findViewById(R.id.already_test_refresh);
        already_test_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    public void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        alreadyTestBeans.clear();
        HttpHelper.httpGetRequest(UrlHelper.getAlreadyExam(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                alreadyTestHandler.sendMessage(message);
            }

            @Override
            public void onFailure(int code) {
                super.onFailure(code);
                if (already_test_refresh.isRefreshing()){
                    already_test_refresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                if (already_test_refresh.isRefreshing()){
                    already_test_refresh.setRefreshing(false);
                }
            }
        });
    }
}
