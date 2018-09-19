package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.AlreadyTestBean;
import com.qbhsnetschool.uitls.ConstantUtil;

import java.util.List;

public class AlreadyTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<AlreadyTestBean> alreadyTestBeans;
    public static final int EEMPTY_ITEM = 1;
    public static final int DATA_ITEM = 2;


    public AlreadyTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AlreadyTestBean> alreadyTestBeans){
        this.alreadyTestBeans = alreadyTestBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == EEMPTY_ITEM) {
            viewHolder = new MaskViewHolder(LayoutInflater.from(context).inflate(R.layout.mask_layout, viewGroup, false));
        } else if (viewType == DATA_ITEM) {
            viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_already_test_item, viewGroup, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder){
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            AlreadyTestBean alreadyTestBean = alreadyTestBeans.get(position);
            int examType = alreadyTestBean.getExam_type();
            if (examType == 1){
                viewHolder1.wrong_count.setVisibility(View.VISIBLE);
                viewHolder1.go_to_test.setVisibility(View.GONE);
                viewHolder1.mark_layout.setVisibility(View.VISIBLE);
                viewHolder1.complete_layout.setVisibility(View.GONE);
                viewHolder1.course_title.setText(alreadyTestBean.getBefore_title() + "  " + alreadyTestBean.getTitle());
                viewHolder1.total_count.setText(alreadyTestBean.getItems());
                viewHolder1.wrong_count.setText(alreadyTestBean.getWrong_items());
                viewHolder1.couse_time.setText("作答时间 ：" + alreadyTestBean.getDatetime());
                viewHolder1.go_to_test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                String totalScore = String.valueOf(alreadyTestBean.getTotal_score());
                if (totalScore.length() == 1){
                    viewHolder1.hundred_img.setVisibility(View.GONE);
                    viewHolder1.ten_img.setVisibility(View.GONE);
                    viewHolder1.per_img.setVisibility(View.VISIBLE);
                    int imageId = ConstantUtil.handleScore().get(totalScore);
                    Glide.with(context).load(imageId).into(viewHolder1.per_img);
                }else if (totalScore.length() == 2){
                    char c [] = totalScore.toCharArray();
                    viewHolder1.hundred_img.setVisibility(View.GONE);
                    viewHolder1.ten_img.setVisibility(View.VISIBLE);
                    viewHolder1.per_img.setVisibility(View.VISIBLE);
                    int imageId1 = ConstantUtil.handleScore().get(String.valueOf(c[0]));
                    int imageId2 = ConstantUtil.handleScore().get(String.valueOf(c[1]));
                    Glide.with(context).load(imageId1).into(viewHolder1.ten_img);
                    Glide.with(context).load(imageId2).into(viewHolder1.per_img);
                }else if (totalScore.length() == 3){
                    char c [] = totalScore.toCharArray();
                    viewHolder1.hundred_img.setVisibility(View.VISIBLE);
                    viewHolder1.ten_img.setVisibility(View.VISIBLE);
                    viewHolder1.per_img.setVisibility(View.VISIBLE);
                    int imageId1 = ConstantUtil.handleScore().get(String.valueOf(c[0]));
                    int imageId2 = ConstantUtil.handleScore().get(String.valueOf(c[1]));
                    int imageId3 = ConstantUtil.handleScore().get(String.valueOf(c[2]));
                    Glide.with(context).load(imageId1).into(viewHolder1.hundred_img);
                    Glide.with(context).load(imageId2).into(viewHolder1.ten_img);
                    Glide.with(context).load(imageId3).into(viewHolder1.per_img);
                }
            }else{
                viewHolder1.wrong_count.setVisibility(View.GONE);
                viewHolder1.go_to_test.setVisibility(View.GONE);
                viewHolder1.mark_layout.setVisibility(View.GONE);
                viewHolder1.complete_layout.setVisibility(View.VISIBLE);
                viewHolder1.course_title.setText(alreadyTestBean.getBefore_title() + "  " + alreadyTestBean.getTitle());
                viewHolder1.total_count.setText(alreadyTestBean.getItems());
                viewHolder1.couse_time.setText("作答时间 ：" + alreadyTestBean.getDatetime());
            }
        }
        if (viewHolder instanceof MaskViewHolder) {
            MaskViewHolder maskViewHolder = (MaskViewHolder) viewHolder;
            maskViewHolder.mask_btn.setText("马上去选课");
            maskViewHolder.mask_btn.setVisibility(View.GONE);
            maskViewHolder.mask_title.setText("您还没有参加过任何测试题~");
        }
    }

    @Override
    public int getItemCount() {
        if (alreadyTestBeans == null || alreadyTestBeans.size() <= 0){
            return 1;
        }else{
            return alreadyTestBeans.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (alreadyTestBeans == null || alreadyTestBeans.size() <= 0) {
            return EEMPTY_ITEM;
        }else{
            return DATA_ITEM;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView course_title;
        TextView total_count;
        TextView wrong_count;
        TextView couse_time;
        TextView go_to_test;
        RelativeLayout complete_layout;
        ImageView complete_img;
        RelativeLayout mark_layout;
        ImageView hundred_img;
        ImageView ten_img;
        ImageView per_img;
        ImageView number_line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_title = itemView.findViewById(R.id.course_title);
            total_count = itemView.findViewById(R.id.total_count);
            wrong_count = itemView.findViewById(R.id.wrong_count);
            couse_time = itemView.findViewById(R.id.couse_time);
            go_to_test = itemView.findViewById(R.id.go_to_test);
            complete_layout = itemView.findViewById(R.id.complete_layout);
            complete_img = itemView.findViewById(R.id.complete_img);
            mark_layout = itemView.findViewById(R.id.mark_layout);
            hundred_img = itemView.findViewById(R.id.hundred_img);
            ten_img = itemView.findViewById(R.id.ten_img);
            per_img = itemView.findViewById(R.id.per_img);
            number_line = itemView.findViewById(R.id.number_line);
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
