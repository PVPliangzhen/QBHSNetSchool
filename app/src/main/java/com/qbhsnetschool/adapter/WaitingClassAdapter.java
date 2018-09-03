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

import com.bumptech.glide.Glide;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.uitls.CCVideoUtil;
import com.qbhsnetschool.uitls.ConstantUtil;

import java.util.List;

public class WaitingClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CourseBean> wait_class_list;
    public static final int EEMPTY_ITEM = 1;
    public static final int DATA_ITEM = 2;

    public WaitingClassAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> list) {
        this.wait_class_list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == EEMPTY_ITEM) {
            viewHolder = new MaskViewHolder(LayoutInflater.from(context).inflate(R.layout.mask_layout, viewGroup, false));
        } else if (viewType == DATA_ITEM) {
            viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_wait_class_item, viewGroup, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            if (wait_class_list != null && wait_class_list.size() > 0) {
                final CourseBean courseBean = wait_class_list.get(position);
                String detail_title = courseBean.getDetail_title();
                viewHolder1.course_title.setText(detail_title);
                viewHolder1.all_chapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                viewHolder1.wait_date_txt.setText(courseBean.getCourse_date());
                viewHolder1.wait_time_txt.setText(courseBean.getCourse_time());
                viewHolder1.wait_course_txt.setText(courseBean.getChapter_times());
                CourseBean.ChapterLatelyBean chapterLatelyBean = courseBean.getChapter_lately();
                viewHolder1.chapter_txt.setText(chapterLatelyBean.getChapter_name());
                viewHolder1.chapter_detail.setText(chapterLatelyBean.getChapter_date() +
                        chapterLatelyBean.getChapter_time() +
                        chapterLatelyBean.getTeacher() + "老师" +
                        chapterLatelyBean.getChapter_expire_time() + "天");
                viewHolder1.go_to_room.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HomeActivity activity = (HomeActivity) context;
                        CCVideoUtil.getInstance(activity).startCCVideo();
                    }
                });
                viewHolder1.course_test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                String season = courseBean.getSeason();
                ConstantUtil.handleSeason(context, season, viewHolder1.season_img, true);
            }
        }
        if (viewHolder instanceof MaskViewHolder) {
            MaskViewHolder maskViewHolder = (MaskViewHolder) viewHolder;
            maskViewHolder.mask_btn.setText("马上去选课");
            maskViewHolder.mask_btn.setVisibility(View.VISIBLE);
            maskViewHolder.mask_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeActivity activity = (HomeActivity) context;
                    activity.clickCourseTab();
                }
            });
            maskViewHolder.mask_title.setText("还没有上过课哦~");
        }
    }

    @Override
    public int getItemCount() {
        if (wait_class_list == null || wait_class_list.size() <= 0){
            return 1;
        }else{
            return wait_class_list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (wait_class_list == null || wait_class_list.size() <= 0) {
            return EEMPTY_ITEM;
        }else{
            return DATA_ITEM;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView course_title;
        TextView all_chapter;
        TextView wait_date_txt;
        TextView wait_time_txt;
        TextView wait_course_txt;
        TextView chapter_txt;
        TextView chapter_detail;
        LinearLayout go_to_room;
        TextView course_test;
        ImageView season_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            all_chapter = itemView.findViewById(R.id.all_chapter);
            wait_date_txt = itemView.findViewById(R.id.wait_date_txt);
            wait_time_txt = itemView.findViewById(R.id.wait_time_txt);
            wait_course_txt = itemView.findViewById(R.id.wait_course_txt);
            chapter_txt = itemView.findViewById(R.id.chapter_txt);
            chapter_detail = itemView.findViewById(R.id.chapter_detail);
            go_to_room = itemView.findViewById(R.id.go_to_room);
            course_test = itemView.findViewById(R.id.course_test);
            season_img = itemView.findViewById(R.id.season_img);
        }
    }

    static class MaskViewHolder extends RecyclerView.ViewHolder {

        TextView mask_title;
        TextView mask_btn;

        public MaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mask_title = itemView.findViewById(R.id.mask_title);
            mask_btn = itemView.findViewById(R.id.mask_btn);
        }
    }
}
