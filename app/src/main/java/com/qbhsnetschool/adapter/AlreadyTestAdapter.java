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

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.AlreadyTestBean;

import java.util.List;

public class AlreadyTestAdapter extends RecyclerView.Adapter<AlreadyTestAdapter.ViewHolder>{

    private Context context;
    private List<AlreadyTestBean> alreadyTestBeans;

    public AlreadyTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AlreadyTestBean> alreadyTestBeans){
        this.alreadyTestBeans = alreadyTestBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_already_test_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        AlreadyTestBean alreadyTestBean = alreadyTestBeans.get(position);
        int examType = alreadyTestBean.getExam_type();
        if (examType == 1){
            viewHolder.wrong_count.setVisibility(View.VISIBLE);
            viewHolder.go_to_test.setVisibility(View.VISIBLE);
            viewHolder.mark_layout.setVisibility(View.VISIBLE);
            viewHolder.complete_layout.setVisibility(View.GONE);
            viewHolder.course_title.setText(alreadyTestBean.getBefore_title() + "  " + alreadyTestBean.getTitle());
            viewHolder.total_count.setText(alreadyTestBean.getItems());
            viewHolder.wrong_count.setText(alreadyTestBean.getWrong_items());
            viewHolder.couse_time.setText("作答时间 ：" + alreadyTestBean.getDatetime());
            viewHolder.go_to_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else{
            viewHolder.wrong_count.setVisibility(View.GONE);
            viewHolder.go_to_test.setVisibility(View.GONE);
            viewHolder.mark_layout.setVisibility(View.GONE);
            viewHolder.complete_layout.setVisibility(View.VISIBLE);
            viewHolder.course_title.setText(alreadyTestBean.getBefore_title() + "  " + alreadyTestBean.getTitle());
            viewHolder.total_count.setText(alreadyTestBean.getItems());
            viewHolder.couse_time.setText("作答时间 ：" + alreadyTestBean.getDatetime());
        }
    }

    @Override
    public int getItemCount() {
        return alreadyTestBeans == null ? 0 : alreadyTestBeans.size();
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
}
