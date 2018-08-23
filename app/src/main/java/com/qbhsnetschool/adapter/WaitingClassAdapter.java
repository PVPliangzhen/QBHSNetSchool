package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.uitls.CCVideoUtil;

import java.util.List;

public class WaitingClassAdapter extends RecyclerView.Adapter<WaitingClassAdapter.ViewHolder>{

    private Context context;
    private List<CourseBean> wait_class_list;

    public WaitingClassAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> list){
        this.wait_class_list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_wait_class_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (wait_class_list != null && wait_class_list.size() > 0) {
            final CourseBean courseBean = wait_class_list.get(position);
            String detail_title = courseBean.getDetail_title();
            viewHolder.course_title.setText(detail_title);
            viewHolder.all_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            viewHolder.wait_date_txt.setText(courseBean.getCourse_date());
            viewHolder.wait_time_txt.setText(courseBean.getCourse_time());
            viewHolder.wait_course_txt.setText(courseBean.getChapter_times());
            CourseBean.ChapterLatelyBean chapterLatelyBean = courseBean.getChapter_lately();
            viewHolder.chapter_txt.setText(chapterLatelyBean.getChapter_name());
            viewHolder.chapter_detail.setText(chapterLatelyBean.getChapter_date()+
                    chapterLatelyBean.getChapter_time()+
                    chapterLatelyBean.getTeacher()+"老师"+
                    chapterLatelyBean.getChapter_expire_time()+"天");
            viewHolder.go_to_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeActivity activity = (HomeActivity) context;
                    CCVideoUtil.getInstance(activity).startCCVideo();
                }
            });
            viewHolder.course_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return wait_class_list == null ? 0 : wait_class_list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView course_title;
        TextView all_chapter;
        TextView wait_date_txt;
        TextView wait_time_txt;
        TextView wait_course_txt;
        TextView chapter_txt;
        TextView chapter_detail;
        LinearLayout go_to_room;
        TextView course_test;

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
        }
    }
}
