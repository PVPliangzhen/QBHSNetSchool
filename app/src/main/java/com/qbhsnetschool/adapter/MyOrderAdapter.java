package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.CouponBean;
import com.qbhsnetschool.entity.OrderBean;
import com.qbhsnetschool.uitls.ConstantUtil;

import org.w3c.dom.Text;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>{

    private List<OrderBean> orderBeans;
    private Context context;

    public MyOrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<OrderBean> orderBeans){
        this.orderBeans = orderBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_order_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        try {
            OrderBean orderBean = orderBeans.get(position);
            viewHolder.order_id.setText("订单号：" + orderBean.getOrder_no());
            if (orderBean.getStatus() == 0){
                viewHolder.pay_status.setText("未支付");
                viewHolder.order_cancel.setVisibility(View.VISIBLE);
                viewHolder.goto_pay.setVisibility(View.VISIBLE);
            }else{
                viewHolder.pay_status.setText("已支付");
                viewHolder.order_cancel.setVisibility(View.GONE);
                viewHolder.goto_pay.setVisibility(View.GONE);
            }
            viewHolder.order_title.setText(orderBean.getDetail_title());
            viewHolder.course_season.setText(ConstantUtil.getSanqiItems().get(orderBean.getCourse_data().getItems()));
            viewHolder.course_date.setText(orderBean.getCourse_date());
            viewHolder.course_time.setText(orderBean.getChapter_times());
            viewHolder.teacher_name.setText(orderBean.getCourse_data().getTeacher());
            viewHolder.order_price.setText("￥" + orderBean.getAmount() / 100);
            viewHolder.order_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cancelOrderListener != null){
                        cancelOrderListener.onCancelOrder(position);
                    }
                }
            });
            viewHolder.goto_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (payOrderListener != null){
                        payOrderListener.onPayOrder(position);
                    }
                }
            });
            ConstantUtil.handleSeason(context, orderBean.getCourse_data().getSeason(), viewHolder.season_img, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orderBeans == null ? 0 : orderBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView order_id;
        TextView pay_status;
        ImageView season_img;
        TextView order_title;
        TextView course_season;
        TextView course_date;
        TextView course_time;
        TextView teacher_name;
        TextView pay_actual;
        TextView order_price;
        TextView goto_pay;
        TextView order_cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id);
            pay_status = itemView.findViewById(R.id.pay_status);
            season_img = itemView.findViewById(R.id.season_img);
            order_title = itemView.findViewById(R.id.order_title);
            course_season = itemView.findViewById(R.id.course_season);
            course_date = itemView.findViewById(R.id.course_date);
            course_time = itemView.findViewById(R.id.course_time);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            pay_actual = itemView.findViewById(R.id.pay_actual);
            order_price = itemView.findViewById(R.id.order_price);
            goto_pay = itemView.findViewById(R.id.goto_pay);
            order_cancel = itemView.findViewById(R.id.order_cancel);
        }
    }

    public interface CancelOrderListener{
        void onCancelOrder(int position);
    }

    private CancelOrderListener cancelOrderListener;

    public void setOnCancelOrderListener(CancelOrderListener cancelOrderListener){
        this.cancelOrderListener = cancelOrderListener;
    }

    public interface PayOrderListener{
        void onPayOrder(int position);
    }

    private PayOrderListener payOrderListener;

    public void setOnPayOrderListener(PayOrderListener payOrderListener){
        this.payOrderListener = payOrderListener;
    }
}
