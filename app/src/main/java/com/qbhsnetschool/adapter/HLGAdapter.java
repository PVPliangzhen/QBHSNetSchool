package com.qbhsnetschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.CourseDetailActivity;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.uitls.ConstantUtil;

import java.util.List;

public class HLGAdapter extends RecyclerView.Adapter<HLGAdapter.ViewHolder>{

    private Context context;
    private List<HomeCourseBean> hlgBeans;

    public HLGAdapter(Context context, List<HomeCourseBean> hlgBeans) {
        this.context = context;
        this.hlgBeans = hlgBeans;
    }

    @NonNull
    @Override
    public HLGAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_class_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HLGAdapter.ViewHolder viewHolder, int position) {
        final HomeCourseBean hlgBean = hlgBeans.get(position);
        String gradeItem = ConstantUtil.getSanqiItems().get(hlgBean.getItems());
        String title1 = hlgBean.getTitle1();
        viewHolder.home_class_title_txt.setText("【"+ gradeItem + "】" + title1);
        String detail_title = hlgBean.getDetail_title();
        viewHolder.home_class_content.setText(detail_title);
        String course_date = hlgBean.getCourse_date();
        viewHolder.home_class_date_txt.setText(course_date);
        String course_time = hlgBean.getCourse_time();
        viewHolder.home_class_time_txt.setText(course_time);
        String chapter_times = hlgBean.getChapter_times();
        viewHolder.home_class_course_txt.setText(chapter_times);
        HomeCourseBean.Teacher1Bean teacher1Bean = hlgBean.getTeacher1();
        String teacher_name = teacher1Bean.getName();
        viewHolder.teacher_btn.setText(teacher_name);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("homeCourseBean", hlgBean);
                intent.putExtra("isHLG", true);
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        String teacherImg = teacher1Bean.getApp_head_pic();
        Glide.with(context).load(teacherImg).placeholder(R.mipmap.teacher_placeholder).error(R.mipmap.teacher_placeholder).into(viewHolder.teacher_show);
        int original_price = hlgBean.getOriginal_price();
        viewHolder.ori_price.setText("原价￥" + original_price + "");
        viewHolder.ori_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        int price = hlgBean.getPrice();
        viewHolder.cur_price.setText("￥" + price + "");
        ConstantUtil.handleSeason(context, hlgBean.getSeason(), viewHolder.season_image, true);
    }

    @Override
    public int getItemCount() {
        return hlgBeans == null ? 0 : hlgBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView home_class_title_txt;
        TextView home_class_content;
        TextView home_class_date_txt;
        TextView home_class_time_txt;
        TextView home_class_course_txt;
        Button teacher_btn;
        TextView ori_price;
        TextView cur_price;
        ImageView teacher_show;
        ImageView season_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_class_title_txt = itemView.findViewById(R.id.home_class_title_txt);
            home_class_content = itemView.findViewById(R.id.home_class_content);
            home_class_date_txt = itemView.findViewById(R.id.home_class_date_txt);
            home_class_time_txt = itemView.findViewById(R.id.home_class_time_txt);
            home_class_course_txt = itemView.findViewById(R.id.home_class_course_txt);
            teacher_btn = itemView.findViewById(R.id.teacher_btn);
            ori_price = itemView.findViewById(R.id.ori_price);
            cur_price = itemView.findViewById(R.id.cur_price);
            teacher_show = itemView.findViewById(R.id.teacher_show);
            season_image = itemView.findViewById(R.id.season_image);
        }
    }
}
