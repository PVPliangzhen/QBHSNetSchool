package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.ChapterBean;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder>{

    private Context context;
    private List<ChapterBean> chapterBeans;

    public ChapterAdapter(Context context){
        this.context = context;
    }

    public void setData(List<ChapterBean> chapterBeans){
        this.chapterBeans = chapterBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_digist_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ChapterBean chapterBean = chapterBeans.get(position);
        viewHolder.chapter_title.setText("第" + chapterBean.getMeasure() + "讲");
        viewHolder.chapter_detail.setText(chapterBean.getChapter_name());
        viewHolder.chapter_date.setText(chapterBean.getChapter_date());
        viewHolder.chapter_time.setText(chapterBean.getChapter_time());
    }

    @Override
    public int getItemCount() {
        return chapterBeans == null ? 0 : chapterBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView chapter_title;
        TextView chapter_detail;
        TextView chapter_date;
        TextView chapter_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chapter_title = itemView.findViewById(R.id.chapter_title);
            chapter_detail = itemView.findViewById(R.id.chapter_detail);
            chapter_date = itemView.findViewById(R.id.chapter_date);
            chapter_time = itemView.findViewById(R.id.chapter_time);
        }
    }
}
