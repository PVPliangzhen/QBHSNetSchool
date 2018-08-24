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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.CouponBean;
import com.qbhsnetschool.entity.CourseBean;

import org.w3c.dom.Text;

import java.util.List;

public class MyCouponsAdpter extends RecyclerView.Adapter<MyCouponsAdpter.ViewHolder> {

    private List<CouponBean> couponBeanList;
    private Context context;

    public MyCouponsAdpter(Context context) {
        this.context = context;
    }

    public void setData(List<CouponBean> couponBeanList){
        this.couponBeanList = couponBeanList;
    }

    @NonNull
    @Override
    public MyCouponsAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.coupon_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCouponsAdpter.ViewHolder viewHolder, int position) {

        CouponBean couponBean = couponBeanList.get(position);
        viewHolder.coupon_price.setText(couponBean.getAmount() + "");
        viewHolder.coupon_title.setText(couponBean.getDesc());
        viewHolder.coupon_time.setText("有效期:" + couponBean.getExpiry_date());

        if (couponBean.isIs_used()){
            viewHolder.coupon_img.setImageResource(R.mipmap.monry_b_yellow);
            viewHolder.coupon_root_layout.setBackgroundResource(R.mipmap.money_b_yellow_line);
            viewHolder.coupon_use.setText("已使用");
            viewHolder.coupon_use.setTextColor(context.getResources().getColor(R.color.color_FFCF00));
        }else if (!couponBean.isIs_active()){
            viewHolder.coupon_img.setImageResource(R.mipmap.money_b_gray);
            viewHolder.coupon_root_layout.setBackgroundResource(R.mipmap.money_b_grayc);
            viewHolder.coupon_use.setText("已过期");
            viewHolder.coupon_use.setTextColor(context.getResources().getColor(R.color.color_666666));
        }else{
            viewHolder.coupon_img.setImageResource(R.mipmap.monry_b_yellow);
            viewHolder.coupon_root_layout.setBackgroundResource(R.mipmap.money_b_yellow_shadow);
            viewHolder.coupon_use.setText(couponBean.getRest_date());
            viewHolder.coupon_use.setTextColor(context.getResources().getColor(R.color.color_FFCF00));
        }
    }

    @Override
    public int getItemCount() {
        return couponBeanList == null ? 0 : couponBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView coupon_price;
        TextView coupon_title;
        TextView coupon_time;
        ImageView coupon_img;
        RelativeLayout coupon_root_layout;
        TextView coupon_use;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupon_price = itemView.findViewById(R.id.coupon_price);
            coupon_title = itemView.findViewById(R.id.coupon_title);
            coupon_time = itemView.findViewById(R.id.coupon_time);
            coupon_img = itemView.findViewById(R.id.coupon_img);
            coupon_root_layout = itemView.findViewById(R.id.coupon_root_layout);
            coupon_use = itemView.findViewById(R.id.coupon_use);
        }
    }
}
