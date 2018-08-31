package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.AddressBean;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private Context context;
    private List<AddressBean> addressBeans;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setDate(List<AddressBean> addressBeans){
        this.addressBeans = addressBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_address_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AddressBean addressBean = addressBeans.get(i);
    }

    @Override
    public int getItemCount() {
        return addressBeans == null ? 0 : addressBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
