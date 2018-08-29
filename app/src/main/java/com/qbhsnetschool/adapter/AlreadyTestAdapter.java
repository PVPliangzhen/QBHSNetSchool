package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qbhsnetschool.entity.AlreadyTestBean;

import java.util.List;

public class AlreadyTestAdapter extends RecyclerView.Adapter<AlreadyTestAdapter.ViewHolder>{

    private Context context;
    private List<AlreadyTestBean> alreadyTestBeans;

    public AlreadyTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AlreadyTestBean> alreadyTestBeans){
        this.alreadyTestBeans = alreadyTestBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return alreadyTestBeans == null ? 0 : alreadyTestBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
