package com.qbhsnetschool.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.adapter.AlreadyClassAdapter;

public class AlreadyClassFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private RecyclerView already_class_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_already_class, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        already_class_list = rootView.findViewById(R.id.already_class_list);
        AlreadyClassAdapter alreadyClassAdapter = new AlreadyClassAdapter(activity);
        LinearLayoutManager already_class_lm = new LinearLayoutManager(activity);
        already_class_lm.setOrientation(LinearLayoutManager.VERTICAL);
        already_class_list.setLayoutManager(already_class_lm);
        already_class_list.setAdapter(alreadyClassAdapter);
    }
}
