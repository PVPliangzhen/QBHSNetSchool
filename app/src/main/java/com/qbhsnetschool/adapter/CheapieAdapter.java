package com.qbhsnetschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

public class CheapieAdapter extends RecyclerView.Adapter<CheapieAdapter.ViewHolder>{

    private Context context;
    private List<HomeCourseBean> cheapieBeans;

    public CheapieAdapter(Context context, List<HomeCourseBean> cheapieBeans) {
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
        final HomeCourseBean cheapieBean = cheapieBeans.get(position);
        String gradeItem = ConstantUtil.getSanqiItems().get(cheapieBean.getItems());
        String title1 = cheapieBean.getTitle1();
        viewHolder.discount_title.setText(Html.fromHtml("【" + gradeItem + "】 <font color =" + context.getResources().getColor(R.color.color_E20000) + ">" + cheapieBean.getPrice() + "元</font>" + title1));
        //viewHolder.discount_title.setText("【"+ gradeItem + "】 " + title1);
        String course_date = cheapieBean.getCourse_date();
        String chapter_times = cheapieBean.getChapter_times();
        viewHolder.discount_content.setText(course_date + "|" +chapter_times);
        viewHolder.buy_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("homeCourseBean", cheapieBean);
                intent.setClass(context, CourseDetailActivity.class);
                context.startActivity(intent);
            }
        });
        String season = cheapieBean.getSeason();
        //Glide.with(context).load(R.mipmap.summer).into(viewHolder.season_img);
        ConstantUtil.handleSeason(context, season, viewHolder.season_img, false);
    }

    @Override
    public int getItemCount() {
        return cheapieBeans == null ? 0 : cheapieBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView discount_title;
        TextView discount_content;
        Button buy_discount;
        ImageView season_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            discount_title = itemView.findViewById(R.id.discount_title);
            discount_content = itemView.findViewById(R.id.discount_content);
            buy_discount = itemView.findViewById(R.id.buy_discount);
            season_img = itemView.findViewById(R.id.season_img);
        }
    }
}
