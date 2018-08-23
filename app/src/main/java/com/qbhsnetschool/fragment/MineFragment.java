package com.qbhsnetschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.activity.MyCouponActivity;
import com.qbhsnetschool.activity.MyOrderActivity;
import com.qbhsnetschool.activity.UserInfoActivity;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ViewPagerSwipeRefreshLayout;

import java.util.HashMap;
import java.util.Map;

public class MineFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private CardView user_card;
    private Button login_out;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_mine, container, false);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initData() {

    }

    private void initView(View rootView) {
        TextView page_title = (TextView) rootView.findViewById(R.id.page_title);
        page_title.setText("我的");
        ImageView page_back = (ImageView) rootView.findViewById(R.id.page_back);
        page_back.setVisibility(View.INVISIBLE);
        user_card = rootView.findViewById(R.id.user_card);
        user_card.setOnClickListener(clickListener);
        RelativeLayout order_layout = rootView.findViewById(R.id.order_layout);
        order_layout.setOnClickListener(clickListener);
        RelativeLayout coupon_layout = rootView.findViewById(R.id.coupon_layout);
        coupon_layout.setOnClickListener(clickListener);
        RelativeLayout service_layout = rootView.findViewById(R.id.service_layout);
        service_layout.setOnClickListener(clickListener);
        login_out = rootView.findViewById(R.id.login_out);
        login_out.setOnClickListener(clickListener);
        ViewPagerSwipeRefreshLayout mine_swipe_layout = rootView.findViewById(R.id.mine_swipe_layout);
        mine_swipe_layout.setEnabled(false);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.user_card:
                    Intent intent = new Intent(activity, UserInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.order_layout:
                    Intent intent1 = new Intent(activity, MyOrderActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.coupon_layout:
                    Intent intent2 = new Intent(activity, MyCouponActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.service_layout:
                    break;
                case R.id.login_out:
                    UserManager.getInstance().clearUser();
                    activity.clickCourseTab();
                    break;
            }
        }
    };
}
