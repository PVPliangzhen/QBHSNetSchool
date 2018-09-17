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
import com.qbhsnetschool.adapter.WaitingTestAdapter;
import com.qbhsnetschool.entity.TestBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WaitTestFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private RecyclerView wait_test_list;
    private WaitTestHandler waitTestHandler;
    private WaitingTestAdapter waitingTestAdapter;
    private ViewPagerSwipeRefreshLayout wait_test_refresh;
    private List<TestBean> testBeans = new ArrayList<>();

    private static class WaitTestHandler extends Handler {

        WeakReference<WaitTestFragment> weakReference;

        public WaitTestHandler (WaitTestFragment fragment){
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            WaitTestFragment waitTestFragment = weakReference.get();
            if (waitTestFragment != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        waitTestFragment.handleWaitTest(result);
                        break;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_wait_test, container, false);
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

    public void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getWaitExam(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                waitTestHandler.sendMessage(message);
            }

            @Override
            public void onFailure(int code) {
                super.onFailure(code);
                if (wait_test_refresh.isRefreshing()){
                    wait_test_refresh.setRefreshing(false);
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                if (wait_test_refresh.isRefreshing()){
                    wait_test_refresh.setRefreshing(false);
                }
            }
        });
    }

    private void initView(View rootView) {
        wait_test_list = rootView.findViewById(R.id.wait_test_list);
        waitingTestAdapter = new WaitingTestAdapter(activity);
        LinearLayoutManager wait_class_lm = new LinearLayoutManager(activity);
        wait_class_lm.setOrientation(LinearLayoutManager.VERTICAL);
        wait_test_list.setLayoutManager(wait_class_lm);
        wait_test_list.setAdapter(waitingTestAdapter);
        wait_test_refresh = rootView.findViewById(R.id.wait_test_refresh);
        wait_test_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        waitTestHandler = new WaitTestHandler(this);
    }

    private void handleWaitTest(String result) {
        try{
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            if (wait_test_refresh.isRefreshing()){
                wait_test_refresh.setRefreshing(false);
            }
            if (!StringUtils.isEmpty(result)){
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.optString("code");
                if (code.equalsIgnoreCase("200")){
                    JSONArray msg = jsonObject.optJSONArray("msg");
                    Gson gson = new Gson();
                    testBeans = gson.fromJson(msg.toString(), new TypeToken<List<TestBean>>(){}.getType());
                }else{
                    testBeans.clear();
                    String msg = jsonObject.optString("msg");
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
                if (waitingTestAdapter != null){
                    waitingTestAdapter.setData(testBeans);
                    waitingTestAdapter.notifyDataSetChanged();
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
}
