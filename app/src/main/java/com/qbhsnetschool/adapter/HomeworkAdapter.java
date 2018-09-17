package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.HomeworkImgBean;

import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder>{

    private Context context;
    private List<HomeworkImgBean> homeworkImgBeans;

    public HomeworkAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeworkImgBean> homeworkImgBeans){
        this.homeworkImgBeans = homeworkImgBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.homework_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        HomeworkImgBean homeworkImgBean = homeworkImgBeans.get(position);
        Glide.with(context).load(homeworkImgBean.getHomework_img()).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.banner_placeholder).error(R.mipmap.banner_placeholder)
                .into(viewHolder.homework_img);
        viewHolder.homework_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showSingleHomeworkClickListener != null){
                    showSingleHomeworkClickListener.singleHomeworkClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeworkImgBeans == null ? 0 : homeworkImgBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView homework_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homework_img = itemView.findViewById(R.id.homework_img);
        }
    }

    public interface ShowSingleHomeworkClickListener{
        void singleHomeworkClick(int position);
    }

    private ShowSingleHomeworkClickListener showSingleHomeworkClickListener;

    public void setShowSingleHomeworkClickListener(ShowSingleHomeworkClickListener showSingleHomeworkClickListener){
        this.showSingleHomeworkClickListener = showSingleHomeworkClickListener;
    }
}
