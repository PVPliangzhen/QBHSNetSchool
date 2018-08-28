package com.qbhsnetschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.CourseDetailActivity;
import com.qbhsnetschool.entity.CheapieBean;
import com.qbhsnetschool.uitls.ConstantUtil;

import java.util.List;

public class CheapieAdapter extends RecyclerView.Adapter<CheapieAdapter.ViewHolder>{

    private Context context;
    private List<CheapieBean> cheapieBeans;

    public CheapieAdapter(Context context, List<CheapieBean> cheapieBeans) {
        this.context = context;
        this.cheapieBeans = cheapieBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cheapie_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final CheapieBean cheapieBean = cheapieBeans.get(position);
        String gradeItem = ConstantUtil.getGradeItems().get(cheapieBean.getItems());
        String title1 = cheapieBean.getTitle1();
        viewHolder.discount_title.setText("【"+ gradeItem + "】 " + title1);
        String course_date = cheapieBean.getCourse_date();
        String chapter_times = cheapieBean.getChapter_times();
        viewHolder.discount_content.setText(course_date + "|" +chapter_times);
        viewHolder.buy_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("cheapieBean", cheapieBean);
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cheapieBeans == null ? 0 : cheapieBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView discount_title;
        TextView discount_content;
        Button buy_discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            discount_title = itemView.findViewById(R.id.discount_title);
            discount_content = itemView.findViewById(R.id.discount_content);
            buy_discount = itemView.findViewById(R.id.buy_discount);
        }
    }
}
