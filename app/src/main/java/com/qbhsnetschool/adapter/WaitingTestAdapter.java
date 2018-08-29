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

public class WaitingTestAdapter extends RecyclerView.Adapter<WaitingTestAdapter.ViewHolder>{

    private Context context;
    private List<TestBean> testBeanList;

    public WaitingTestAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TestBean> list){
        this.testBeanList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_wait_test_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final TestBean testBean = testBeanList.get(position);
        viewHolder.test_title.setText(testBean.getBefore_title() + "  " + testBean.getTitle());
        viewHolder.test_number.setText(testBean.getItems());
        viewHolder.go_to_test.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return testBeanList == null ? 0 : testBeanList.size();
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
}
