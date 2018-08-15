package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class SanQiLianBaoAdapter extends RecyclerView.Adapter<SanQiLianBaoAdapter.ViewHolder>{

    private Context context;

    public SanQiLianBaoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sanqi_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView discount_title;
        TextView discount_date;
        TextView discount_course;
        Button discount_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            discount_title = itemView.findViewById(R.id.discount_title);
            discount_date = itemView.findViewById(R.id.discount_date);
            discount_course = itemView.findViewById(R.id.discount_course);
            discount_btn = itemView.findViewById(R.id.discount_btn);
        }
    }
}
