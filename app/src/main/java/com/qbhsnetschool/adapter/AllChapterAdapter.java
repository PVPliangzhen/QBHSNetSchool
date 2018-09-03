package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            viewHolder.course_title.setText(allChapterBean.getChapter_name());
            viewHolder.course_detail.setText(allChapterBean.getChapter_date() + allChapterBean.getChapter_time() + allChapterBean.getTeacher() + "剩余" + allChapterBean.getChapter_expire_time() + "天");
        }
    }

    @Override
    public int getItemCount() {
        return allChapterBeans == null ? 0 : allChapterBeans.size();
        //return 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView course_title;
        TextView course_detail;
        LinearLayout play_layout;
        TextView play_txt;
        ImageView play_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            course_detail = itemView.findViewById(R.id.course_detail);
            play_layout = itemView.findViewById(R.id.play_layout);
            play_txt = itemView.findViewById(R.id.play_txt);
            play_img = itemView.findViewById(R.id.play_img);
        }
    }
}
