package com.qbhsnetschool.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;

public class TestFragment extends Fragment{
    
    private HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        View rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_test, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
    }
}
