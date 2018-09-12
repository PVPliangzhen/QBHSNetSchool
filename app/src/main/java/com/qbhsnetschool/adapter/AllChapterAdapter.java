package com.qbhsnetschool.adapter;

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

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.activity.ShowSubmittedHomeworkActivity;
import com.qbhsnetschool.activity.SubmitHomeWorkActivity;
import com.qbhsnetschool.entity.AllChapterBean;
import com.qbhsnetschool.uitls.CCVideoUtil;
import com.qbhsnetschool.uitls.CourseUtil;

import java.util.List;

public class AllChapterAdapter extends RecyclerView.Adapter<AllChapterAdapter.ViewHolder> {

    private Context context;
    private List<AllChapterBean> allChapterBeans;

    public AllChapterAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AllChapterBean> allChapterBeans) {
        this.allChapterBeans = allChapterBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_chapter_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (allChapterBeans != null && allChapterBeans.size() > 0) {
            final AllChapterBean allChapterBean = allChapterBeans.get(position);
            viewHolder.course_title.setText(allChapterBean.getChapter_name());
            viewHolder.course_detail.setText(allChapterBean.getChapter_date() + allChapterBean.getChapter_time() + allChapterBean.getTeacher() + "剩余" + allChapterBean.getChapter_expire_time() + "天");
            if (allChapterBean.getState().equalsIgnoreCase("current") || allChapterBean.getState().equalsIgnoreCase("future")) {
                if (allChapterBean.isIs_live()) {
                    //红底白字 进入教室
                    viewHolder.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_hongdibaizi);
                    viewHolder.go_to_room_layout.setEnabled(true);
                    viewHolder.go_to_room_txt.setText("进入教室");
                    viewHolder.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_ffffff));
                    viewHolder.go_to_room_img.setImageResource(R.mipmap.icon_studio_white);
                } else {
                    //白底灰字 进入教室
                    viewHolder.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihuizi);
                    viewHolder.go_to_room_layout.setEnabled(false);
                    viewHolder.go_to_room_txt.setText("进入教室");
                    viewHolder.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_999999));
                    viewHolder.go_to_room_img.setImageResource(R.mipmap.icon_studio_gray);
                }
            } else {
                //past
                if (allChapterBean.getCc_vedio().getRecordId() == null) {
                    //白底灰字 复习课程
                    viewHolder.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihuizi);
                    viewHolder.go_to_room_layout.setEnabled(false);
                    viewHolder.go_to_room_txt.setText("复习课程");
                    viewHolder.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_999999));
                    viewHolder.go_to_room_img.setImageResource(R.mipmap.icon_studio_gray);
                } else {
                    //白底红字 复习课程
                    viewHolder.go_to_room_layout.setBackgroundResource(R.drawable.goto_room_baidihongzi);
                    viewHolder.go_to_room_layout.setEnabled(true);
                    viewHolder.go_to_room_txt.setText("复习课程");
                    viewHolder.go_to_room_txt.setTextColor(context.getResources().getColor(R.color.color_E20000));
                    viewHolder.go_to_room_img.setImageResource(R.mipmap.icon_studio_red);
                }
            }
            viewHolder.go_to_room_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllChapterBean.CcVedioBean ccVedioBean = allChapterBean.getCc_vedio();
                    if (allChapterBean.getState().equalsIgnoreCase("current") || allChapterBean.getState().equalsIgnoreCase("future")){
                        CCVideoUtil.getInstance(context).startCCVideo(ccVedioBean.getRoomId(), ccVedioBean.getUserId(), ccVedioBean.getViewerName(), ccVedioBean.getToken());
                    }else{
                        CCVideoUtil.getInstance(context).startCCPlayBack(ccVedioBean.getRoomId(), ccVedioBean.getUserId(), ccVedioBean.getViewerName(), ccVedioBean.getToken(), ccVedioBean.getRecordId());
                    }
                }
            });
            viewHolder.submit_work.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (submitClickListener != null){
                        submitClickListener.submitHomework(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allChapterBeans == null ? 0 : allChapterBeans.size();
        //return 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView course_title;
        TextView course_detail;
        LinearLayout go_to_room_layout;
        TextView go_to_room_txt;
        ImageView go_to_room_img;
        LinearLayout submit_work;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            course_detail = itemView.findViewById(R.id.course_detail);
            go_to_room_layout = itemView.findViewById(R.id.go_to_room_layout);
            go_to_room_txt = itemView.findViewById(R.id.go_to_room_txt);
            go_to_room_img = itemView.findViewById(R.id.go_to_room_img);
            submit_work = itemView.findViewById(R.id.submit_work);
        }
    }

//    View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.go_to_room_layout:
//                    CCVideoUtil.getInstance(context).startCCVideo();
//                    break;
//            }
//        }
//    };

    public interface SubmitClickListener{
        void submitHomework(int position);
    }

    private SubmitClickListener submitClickListener;

    public void setOnSubmitClickListener(SubmitClickListener submitClickListener){
        this.submitClickListener = submitClickListener;
    }
}
