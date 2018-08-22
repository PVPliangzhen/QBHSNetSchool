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
import com.qbhsnetschool.adapter.WaitingClassAdapter;

public class WaitClassFragment extends Fragment{

    private HomeActivity activity;
    private View rootView;
    private RecyclerView wait_class_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        rootView = LayoutInflater.from(activity).inflate(R.layout.fragment_wait_class, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        wait_class_list = rootView.findViewById(R.id.wait_class_list);
        WaitingClassAdapter waitingClassAdapter = new WaitingClassAdapter(activity);
        LinearLayoutManager wait_class_lm = new LinearLayoutManager(activity);
        wait_class_lm.setOrientation(LinearLayoutManager.VERTICAL);
        wait_class_list.setLayoutManager(wait_class_lm);
        wait_class_list.setAdapter(waitingClassAdapter);
    }
}
