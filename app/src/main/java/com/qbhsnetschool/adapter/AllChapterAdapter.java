package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.AllChapterBean;

import java.util.List;

public class AllChapterAdapter extends RecyclerView.Adapter<AllChapterAdapter.ViewHolder>{

    private Context context;
    private List<AllChapterBean> allChapterBeans;

    public AllChapterAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AllChapterBean> allChapterBeans){
        this.allChapterBeans = allChapterBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_chapter_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (allChapterBeans != null && allChapterBeans.size() > 0){
            AllChapterBean allChapterBean = allChapterBeans.get(position);
        }
    }

    @Override
    public int getItemCount() {
        //return allChapterBeans == null ? 0 : allChapterBeans.size();
        return 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
