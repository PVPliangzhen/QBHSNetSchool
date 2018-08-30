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

public class JianziAdapter extends RecyclerView.Adapter<JianziAdapter.ViewHolder>{

    private Context context;
    private List<HomeCourseBean> jianziBeans;

    public JianziAdapter(Context context, List<HomeCourseBean> jianziBeans) {
        this.context = context;
        this.jianziBeans = jianziBeans;
    }

    @NonNull
    @Override
    public JianziAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_class_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JianziAdapter.ViewHolder viewHolder, int position) {
        final HomeCourseBean jianziBean = jianziBeans.get(position);
        String gradeItem = ConstantUtil.getSanqiItems().get(jianziBean.getItems());
        String title1 = jianziBean.getTitle1();
        viewHolder.home_class_title_txt.setText("【"+ gradeItem + "】" + title1);
        String detail_title = jianziBean.getDetail_title();
        viewHolder.home_class_content.setText(detail_title);
        String course_date = jianziBean.getCourse_date();
        viewHolder.home_class_date_txt.setText(course_date);
        String course_time = jianziBean.getCourse_time();
        viewHolder.home_class_time_txt.setText(course_time);
        String chapter_times = jianziBean.getChapter_times();
        viewHolder.home_class_course_txt.setText(chapter_times);
        HomeCourseBean.Teacher1Bean teacher1Bean = jianziBean.getTeacher1();
        String teacher_name = teacher1Bean.getName();
        viewHolder.teacher_btn.setText(teacher_name);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("homeCourseBean", jianziBean);
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        String teacherImg = teacher1Bean.getApp_head_pic();
        Glide.with(context).load(teacherImg).placeholder(R.mipmap.teacher_placeholder).error(R.mipmap.teacher_placeholder).into(viewHolder.teacher_show);
        int original_price = jianziBean.getOriginal_price();
        viewHolder.ori_price.setText("原价￥" + original_price + "");
        viewHolder.ori_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        int price = jianziBean.getPrice();
        viewHolder.cur_price.setText("￥" + price + "");
    }

    @Override
    public int getItemCount() {
        return jianziBeans == null ? 0 : jianziBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
        }
    }
}
