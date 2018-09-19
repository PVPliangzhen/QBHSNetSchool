package com.qbhsnetschool.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.qbhsnetschool.activity.AllChaptersActivity;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.activity.SubmitHomeWorkActivity;
import com.qbhsnetschool.activity.WebActivity;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.UrlHelper;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            if (wait_class_list != null && wait_class_list.size() > 0) {
                final CourseBean courseBean = wait_class_list.get(position);
                String detail_title = courseBean.getDetail_title();
                viewHolder1.course_title.setText(detail_title);
                viewHolder1.all_chapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(context, AllChaptersActivity.class);
                        intent.putExtra("courseBean", courseBean);
                        context.startActivity(intent);
                    }
                });
                viewHolder1.wait_date_txt.setText(courseBean.getCourse_date());
                viewHolder1.wait_time_txt.setText(courseBean.getCourse_time());
                viewHolder1.wait_course_txt.setText(courseBean.getChapter_times());
                final CourseBean.ChapterLatelyBean chapterLatelyBean = courseBean.getChapter_lately();
                viewHolder1.chapter_txt.setText(chapterLatelyBean.getChapter_name());
                viewHolder1.chapter_detail.setText(chapterLatelyBean.getChapter_date() +
                        chapterLatelyBean.getChapter_time() +
                        chapterLatelyBean.getTeacher() + "老师" +
                        "剩余" +
                        chapterLatelyBean.getChapter_expire_time() + "天");
                if (chapterLatelyBean.getChapter_name() == null){
                    viewHolder1.course_bottom_layout.setVisibility(View.GONE);
                }else{
                    viewHolder1.course_bottom_layout.setVisibility(View.VISIBLE);
                    if (chapterLatelyBean.getState().equalsIgnoreCase("current") || chapterLatelyBean.getState().equalsIgnoreCase("future")){
                        if (chapterLatelyBean.isIs_live()){
                            //红底白字 进入教室
                            viewHolder1.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_hongdibaizi);
                            viewHolder1.go_to_room_layout.setEnabled(true);
                            viewHolder1.go_to_room_txt.setText("进入教室");
                            viewHolder1.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_ffffff));
                            viewHolder1.go_to_room_img.setImageResource(R.mipmap.icon_studio_white);
                        }else{
                            //白底灰字 进入教室
                            viewHolder1.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihuizi);
                            viewHolder1.go_to_room_layout.setEnabled(false);
                            viewHolder1.go_to_room_txt.setText("进入教室");
                            viewHolder1.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_999999));
                            viewHolder1.go_to_room_img.setImageResource(R.mipmap.icon_studio_gray);
                        }
                    }else{
                        //past
                        if (chapterLatelyBean.getCc_vedio().getRecordId() == null){
                            //白底灰字 复习课程
                            viewHolder1.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihuizi);
                            viewHolder1.go_to_room_layout.setEnabled(false);
                            viewHolder1.go_to_room_txt.setText("复习课程");
                            viewHolder1.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_999999));
                            viewHolder1.go_to_room_img.setImageResource(R.mipmap.icon_studio_gray);
                        }else{
                            //白底红字 复习课程
                            viewHolder1.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihongzi);
                            viewHolder1.go_to_room_layout.setEnabled(true);
                            viewHolder1.go_to_room_txt.setText("复习课程");
                            viewHolder1.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_E20000));
                            viewHolder1.go_to_room_img.setImageResource(R.mipmap.icon_studio_red);
                        }
                    }
                }
                viewHolder1.go_to_room_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CourseBean.ChapterLatelyBean.CcVedioBean ccVedioBean = chapterLatelyBean.getCc_vedio();
                        if (chapterLatelyBean.getState().equalsIgnoreCase("current") || chapterLatelyBean.getState().equalsIgnoreCase("future")){
                            CCVideoUtil.getInstance(context).startCCVideo(ccVedioBean.getRoomId(), ccVedioBean.getUserId(), ccVedioBean.getViewerName(), ccVedioBean.getToken());
                        }else{
                            CCVideoUtil.getInstance(context).startCCPlayBack(ccVedioBean.getRoomId(), ccVedioBean.getUserId(), ccVedioBean.getViewerName(), ccVedioBean.getToken(), ccVedioBean.getRecordId());
                        }
                    }
                });
                viewHolder1.course_test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        String testUrl = UrlHelper.testUrl(UserManager.getInstance().getUser().getUserId() + "", courseBean.getGrade() + "", courseBean.getExam_id() + "");
                        intent.putExtra("url", testUrl);
                        intent.setClass(context, WebActivity.class);
                        context.startActivity(intent);
                    }
                });
                String season = courseBean.getSeason();
                ConstantUtil.handleSeason(context, season, viewHolder1.season_img, true);
                viewHolder1.submit_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (submitHomeworkClickListener != null){
                            submitHomeworkClickListener.submitHomework(position);
                        }
                    }
                });
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
        LinearLayout go_to_room_layout;
        TextView go_to_room_txt;
        ImageView go_to_room_img;
        TextView course_test;
        ImageView season_img;
        LinearLayout submit_work;
        LinearLayout course_bottom_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            all_chapter = itemView.findViewById(R.id.all_chapter);
            wait_date_txt = itemView.findViewById(R.id.wait_date_txt);
            wait_time_txt = itemView.findViewById(R.id.wait_time_txt);
            wait_course_txt = itemView.findViewById(R.id.wait_course_txt);
            chapter_txt = itemView.findViewById(R.id.chapter_txt);
            chapter_detail = itemView.findViewById(R.id.chapter_detail);
            go_to_room_layout = itemView.findViewById(R.id.go_to_room_layout);
            go_to_room_txt = itemView.findViewById(R.id.go_to_room_txt);
            go_to_room_img = itemView.findViewById(R.id.go_to_room_img);
            course_test = itemView.findViewById(R.id.course_test);
            season_img = itemView.findViewById(R.id.season_img);
            submit_work = itemView.findViewById(R.id.submit_work);
            course_bottom_layout = itemView.findViewById(R.id.course_bottom_layout);
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

    public interface SubmitHomeworkClickListener{
        void submitHomework(int position);
    }

    private SubmitHomeworkClickListener submitHomeworkClickListener;

    public void setOnSubmitHomeworkClickListener(SubmitHomeworkClickListener submitHomeworkClickListener){
        this.submitHomeworkClickListener = submitHomeworkClickListener;
    }

//    View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.go_to_room_layout:
//
//                    CCVideoUtil.getInstance(context).startCCVideo();
//                    break;
//            }
//        }
//    };
}
