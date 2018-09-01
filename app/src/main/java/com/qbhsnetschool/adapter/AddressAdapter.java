package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.AddressBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_address_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (addressBeans != null && addressBeans.size() > 0){
            final AddressBean addressBean = addressBeans.get(position);
            viewHolder.address_user.setText(addressBean.getName() + "  " + addressBean.getTel());
            viewHolder.address_detail.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
            viewHolder.address_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addressEditListener != null){
                        addressEditListener.onAddressEdit(position);
                    }
                }
            });
            viewHolder.address_delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (addressDeleteListener != null){
                        addressDeleteListener.onAddressDelete(position);
                    }
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return addressBeans == null ? 0 : addressBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView address_user;
        TextView address_detail;
        Button address_delete;
        Button address_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_user = itemView.findViewById(R.id.address_user);
            address_detail = itemView.findViewById(R.id.address_detail);
            address_delete = itemView.findViewById(R.id.address_delete);
            address_edit = itemView.findViewById(R.id.address_edit);
        }
    }

    public interface AddressDeleteListener{
        void onAddressDelete(int position);
    }

    private AddressDeleteListener addressDeleteListener;

    public void setOnAddressDeleteListener(AddressDeleteListener addressDeleteListener){
        this.addressDeleteListener = addressDeleteListener;
    }

    public interface AddressEditListener{
        void onAddressEdit(int position);
    }

    private AddressEditListener addressEditListener;

    public void setOnAddressEditListener(AddressEditListener addressEditListener) {
        this.addressEditListener = addressEditListener;
    }

    public interface AddressSelectListener{
        void AddressSelect(int position);
    }

    private AddressSelectListener addressSelectListener;

    public void setOnAddressSelectListener(AddressSelectListener addressSelectListener) {
        this.addressSelectListener = addressSelectListener;
    }
}
