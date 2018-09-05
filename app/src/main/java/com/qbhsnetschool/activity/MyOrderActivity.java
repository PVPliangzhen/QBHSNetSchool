package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.MyOrderAdapter;
import com.qbhsnetschool.entity.CouponBean;
import com.qbhsnetschool.entity.OrderBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyOrderActivity extends BaseActivity{

    private RecyclerView order_list;
    private MyOrderActivity activity;
    private MyOrderHandler myOrderHandler;
    private List<OrderBean> orderBeans;
    private MyOrderAdapter myOrderAdapter;

    private static class MyOrderHandler extends Handler {
        WeakReference<MyOrderActivity> weakReference;
        public MyOrderHandler(MyOrderActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyOrderActivity myOrderActivity = weakReference.get();
            if (myOrderActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        myOrderActivity.handleOrder(result);
                        break;
                }
            }
        }
    }

    private void handleOrder(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                JSONArray msg = jsonObject.optJSONArray("msg");
                Gson gson = new Gson();
                orderBeans = gson.fromJson(msg.toString(), new TypeToken<List<OrderBean>>() {}.getType());
                if (myOrderAdapter != null){
                    myOrderAdapter.setData(orderBeans);
                    myOrderAdapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_my_order, true, R.color.status_bar_bg_color, false);
        activity = this;
        order_list = (RecyclerView) findViewById(R.id.order_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        order_list.setLayoutManager(lm);
        myOrderHandler = new MyOrderHandler(activity);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("我的订单");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myOrderAdapter = new MyOrderAdapter(activity);
        order_list.setAdapter(myOrderAdapter);
        initCancelOrder();
        myOrderAdapter.setOnPayOrderListener(new MyOrderAdapter.PayOrderListener() {
            @Override
            public void onPayOrder(int position) {
                Intent intent = new Intent();
                intent.putExtra("is_from_order", true);
                intent.putExtra("order_bean", orderBeans.get(position));
                intent.setClass(activity, ConfirmOrderActivity.class);
                startActivity(intent);
            }
        });
        initData();
    }

    private void initCancelOrder() {
        myOrderAdapter.setOnCancelOrderListener(new MyOrderAdapter.CancelOrderListener() {
            @Override
            public void onCancelOrder(final int position) {
                if (!UIUtils.isNetworkAvailable(activity)){
                    Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                    return;
                }
                LoadingDialog.loading(activity);
                OrderBean orderBean = orderBeans.get(position);
                HttpHelper.httpRequest(UrlHelper.cancelOrder(orderBean.getOrder_no()), null, "DELETE", new StandardCallBack(activity) {
                    @Override
                    public void onSuccess(final String result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!LoadingDialog.isDissMissLoading()){
                                    LoadingDialog.dismissLoading();
                                }
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    String code = jsonObject.optString("code");
                                    String msg = jsonObject.optString("msg");
                                    if (code.equalsIgnoreCase("1506")){
                                        orderBeans.remove(position);
                                        if (myOrderAdapter != null){
                                            myOrderAdapter.setData(orderBeans);
                                            myOrderAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });
            }
        });
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getOrders(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                myOrderHandler.sendMessage(message);
            }
        });
    }
}
