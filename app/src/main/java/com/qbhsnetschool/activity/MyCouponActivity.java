package com.qbhsnetschool.activity;

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
import com.qbhsnetschool.adapter.MyCouponsAdpter;
import com.qbhsnetschool.entity.CouponBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyCouponActivity extends BaseActivity{

    private RecyclerView coupon_list;
    private MyCouponActivity activity;
    private MyCouponHandler myCouponHandler;
    private List<CouponBean> couponBeans;
    private MyCouponsAdpter myCouponsAdpter;

    private static class MyCouponHandler extends Handler {
        WeakReference<MyCouponActivity> weakReference;
        public MyCouponHandler(MyCouponActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyCouponActivity myCouponActivity = weakReference.get();
            if (myCouponActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        myCouponActivity.handleCoupons(result);
                        break;
                }
            }
        }
    }

    private void handleCoupons(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                JSONArray msg = jsonObject.optJSONArray("msg");
                Gson gson = new Gson();
                couponBeans = gson.fromJson(msg.toString(), new TypeToken<List<CouponBean>>() {}.getType());
                if (myCouponsAdpter != null){
                    myCouponsAdpter.setData(couponBeans);
                    myCouponsAdpter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_my_coupon, true, R.color.status_bar_bg_color, false);
        activity = this;
        coupon_list = (RecyclerView) findViewById(R.id.coupon_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        coupon_list.setLayoutManager(lm);
        myCouponHandler = new MyCouponHandler(activity);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("优惠券");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myCouponsAdpter = new MyCouponsAdpter(activity);
        coupon_list.setAdapter(myCouponsAdpter);
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getCoupons(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                myCouponHandler.sendMessage(message);
            }
        });
    }
}
