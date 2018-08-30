package com.qbhsnetschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.WebActivity;
import com.qbhsnetschool.entity.TestBean;
import com.qbhsnetschool.entity.UserManager;

import java.util.List;

public class WaitingTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<TestBean> testBeanList;
    public static final int EEMPTY_ITEM = 1;
    public static final int DATA_ITEM = 2;

    public WaitingTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TestBean> list){
        this.testBeanList = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == EEMPTY_ITEM) {
            viewHolder = new MaskViewHolder(LayoutInflater.from(context).inflate(R.layout.mask_layout, viewGroup, false));
        } else if (viewType == DATA_ITEM) {
            viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_wait_test_item, viewGroup, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder){
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            final TestBean testBean = testBeanList.get(position);
            viewHolder1.test_title.setText(testBean.getBefore_title() + "  " + testBean.getTitle());
            viewHolder1.test_number.setText(testBean.getItems());
            viewHolder1.go_to_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    String url = testBean.getUrl() + "?user_id=" + UserManager.getInstance().getUser().getUserId() + "&grade=5&exam_id=" + testBean.getId();
                    intent.putExtra("url", url);
                    intent.setClass(context, WebActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (viewHolder instanceof MaskViewHolder) {
            MaskViewHolder maskViewHolder = (MaskViewHolder) viewHolder;
            maskViewHolder.mask_btn.setText("马上去选课");
            maskViewHolder.mask_btn.setVisibility(View.GONE);
            maskViewHolder.mask_title.setText("还没有测试哦~");
        }
    }

    @Override
    public int getItemCount() {
        if (testBeanList == null || testBeanList.size() <= 0){
            return 1;
        }else{
            return testBeanList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (testBeanList == null || testBeanList.size() <= 0) {
            return EEMPTY_ITEM;
        }else{
            return DATA_ITEM;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView test_title;
        TextView test_number;
        TextView go_to_test;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            test_title = itemView.findViewById(R.id.test_title);
            test_number = itemView.findViewById(R.id.test_number);
            go_to_test = itemView.findViewById(R.id.go_to_test);
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
