package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pingplusplus.android.Pingpp;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.MyCouponsAdpter;
import com.qbhsnetschool.entity.AddressBean;
import com.qbhsnetschool.entity.CouponBean;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.qbhsnetschool.uitls.GlideCircleTransform;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderActivity extends BaseActivity{

    private HomeCourseBean homeCourseBean;
    private ConfirmOrderActivity activity;
    private ConfirmOrderHandler confirmOrderHandler;
    private List<AddressBean> addressBeans;
    private LinearLayout add_address;
    private RelativeLayout address_layout;
    private TextView user_name;
    private TextView user_num;
    private TextView user_address;
    private TextView course_title;
    private TextView course_number;
    private TextView course_date;
    private TextView course_time;
    private TextView course_chapter;
    private TextView teacher_name_txt;
    private TextView original_price;
    private TextView current_price;
    private ImageView teacher_head_img;
    private RecyclerView coupon_list;
    private MyCouponsAdpter myCouponsAdpter;
    private AddressBean addressBean;
    private TextView no_coupon_txt;
    private RelativeLayout coupon_layout;

    private static class ConfirmOrderHandler extends Handler{
        WeakReference<ConfirmOrderActivity> weakReference;

        public ConfirmOrderHandler(ConfirmOrderActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ConfirmOrderActivity confirmOrderActivity = weakReference.get();
            if (confirmOrderActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        confirmOrderActivity.handleAddress(result);
                        break;
                    case 0x02:
                        String result1 = (String) msg.obj;
                        try {
                            JSONObject jsonObject = new JSONObject(result1);
                            String code = jsonObject.optString("code");
                            if (code.equalsIgnoreCase("200")){
                                confirmOrderActivity.no_coupon_txt.setVisibility(View.GONE);
                                confirmOrderActivity.coupon_layout.setVisibility(View.VISIBLE);
                                Gson gson = new Gson();
                                List<CouponBean> couponBeans = new ArrayList<>();
                                CouponBean couponBean = gson.fromJson(jsonObject.toString(), CouponBean.class);
                                couponBeans.add(couponBean);
                                if (confirmOrderActivity.myCouponsAdpter != null){
                                    confirmOrderActivity.myCouponsAdpter.setData(couponBeans);
                                    confirmOrderActivity.myCouponsAdpter.notifyDataSetChanged();
                                }
                            }
                            if (code.equalsIgnoreCase("1601")){
                                confirmOrderActivity.no_coupon_txt.setVisibility(View.VISIBLE);
                                confirmOrderActivity.coupon_layout.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    private void handleAddress(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                JSONArray addresses = jsonObject.optJSONArray("addresses");
                Gson gson = new Gson();
                addressBeans = gson.fromJson(addresses.toString(), new TypeToken<List<AddressBean>>(){}.getType());
                if (addressBeans == null || addressBeans.size() <= 0){
                    add_address.setVisibility(View.VISIBLE);
                    address_layout.setVisibility(View.GONE);
                }else{
                    add_address.setVisibility(View.GONE);
                    address_layout.setVisibility(View.VISIBLE);
                    addressBean = addressBeans.get(0);
                    user_name.setText(addressBean.getName());
                    user_num.setText(addressBean.getTel());
                    user_address.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_confirm_order, false, R.color.status_bar_bg_color, false);
        activity = this;
        confirmOrderHandler = new ConfirmOrderHandler(activity);
        initIntent();
        initView();
        initAddress();
        initCoupons();
    }

    private void initCoupons() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        Map<String, String> params = new HashMap<>();
        params.put("course_id", homeCourseBean.getProduct_id());
        HttpHelper.httpRequest(UrlHelper.getOrderCoupon(), params, "POST", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x02;
                message.obj = result;
                confirmOrderHandler.sendMessage(message);
            }
        });
    }

    private void initAddress() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getAddresses(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                confirmOrderHandler.sendMessage(message);
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();
        homeCourseBean = (HomeCourseBean) intent.getSerializableExtra("homeCourseBean");
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("确认订单");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_address = (LinearLayout) findViewById(R.id.add_address);
        add_address.setOnClickListener(clickListener);
        add_address.setVisibility(View.GONE);
        address_layout = (RelativeLayout) findViewById(R.id.address_layout);
        address_layout.setOnClickListener(clickListener);
        address_layout.setVisibility(View.INVISIBLE);
        user_name = (TextView) findViewById(R.id.user_name);
        user_num = (TextView) findViewById(R.id.user_num);
        user_address = (TextView) findViewById(R.id.user_address);
        course_title = (TextView) findViewById(R.id.course_title);
        course_title.setText(homeCourseBean.getTitle1());
        course_number = (TextView) findViewById(R.id.course_number);
        course_number.setText(ConstantUtil.getSanqiItems().get(homeCourseBean.getItems()));
        course_date = (TextView) findViewById(R.id.course_date);
        course_date.setText(homeCourseBean.getCourse_date());
        course_time = (TextView) findViewById(R.id.course_time);
        course_time.setText(homeCourseBean.getCourse_time());
        course_chapter = (TextView) findViewById(R.id.course_chapter);
        course_chapter.setText(homeCourseBean.getChapter_times());
        teacher_name_txt = (TextView) findViewById(R.id.teacher_name_txt);
        teacher_name_txt.setText(homeCourseBean.getTeacher1().getName());
        original_price = (TextView) findViewById(R.id.original_price);
        original_price.setText("原价" + homeCourseBean.getOriginal_price() + "元");
        original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        current_price = (TextView) findViewById(R.id.current_price);
        current_price.setText("￥" + homeCourseBean.getPrice());
        teacher_head_img = (ImageView) findViewById(R.id.teacher_head_img);
        Glide.with(activity).load(homeCourseBean.getTeacher1().getApp_head_pic_small()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .placeholder(R.mipmap.avatars).error(R.mipmap.avatars)
                .transform(new GlideCircleTransform(activity, homeCourseBean.getTeacher1().getApp_head_pic_small())).into(teacher_head_img);
        coupon_list = (RecyclerView) findViewById(R.id.coupon_list);
        myCouponsAdpter = new MyCouponsAdpter(activity);
        LinearLayoutManager lm = new LinearLayoutManager(activity){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        coupon_list.setLayoutManager(lm);
        coupon_list.setAdapter(myCouponsAdpter);
        TextView real_price = (TextView) findViewById(R.id.real_price);
        real_price.setText("￥" + homeCourseBean.getPrice());
        LinearLayout sign_up_btn = (LinearLayout) findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(clickListener);
        no_coupon_txt = (TextView) findViewById(R.id.no_coupon_txt);
        coupon_layout = (RelativeLayout) findViewById(R.id.coupon_layout);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_up_btn:
                    if (!UIUtils.isNetworkAvailable(activity)){
                        Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtils.isEmpty(user_address.getText().toString().trim())){
                        Toast.makeText(activity, "请添加收货地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LoadingDialog.loading(activity);
                    Map<String, String> params = new HashMap<>();
                    params.put("course_id", homeCourseBean.getProduct_id());
                    params.put("address_id", addressBean.getId() + "");
                    params.put("study_course", "1");
                    HttpHelper.httpRequest(UrlHelper.createOrder(), params, "POST", new StandardCallBack(activity) {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.optString("code");
                                if (code.equalsIgnoreCase("200")){
                                    String chargeString = jsonObject.optString("charge");
                                    Pingpp.createPayment(activity, chargeString);
                                }else{
                                    final String msg = jsonObject.optString("msg");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!LoadingDialog.isDissMissLoading()){
                                                LoadingDialog.dismissLoading();
                                            }
                                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case R.id.add_address:
                    Intent intent = new Intent(activity, AddressActivity.class);
                    startActivityForResult(intent, 0x10);
                    break;
                case R.id.address_layout:
                    Intent intent1 = new Intent(activity, AddressManagerActivity.class);
                    startActivityForResult(intent1, 0x14);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            String result = data.getExtras().getString("pay_result");
            switch (result){
                case "fail":
                    Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                    break;
                case "success":
                    //Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(activity, PurchaseSuccessActivity.class);
                    startActivity(intent);
                    break;
                case "cancel":
                    Toast.makeText(activity, "取消支付", Toast.LENGTH_SHORT).show();
                    break;
                case "invalid":
                    Toast.makeText(activity, "未安装微信客户端，请安装后重试", Toast.LENGTH_SHORT).show();
                    break;
                case "unknown":
                    Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                    break;
            }
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
            String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
            String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
        }
        if (requestCode == 0x10){
            if (resultCode == 0x11){
                String result = data.getStringExtra("result");
                Gson gson = new Gson();
                AddressBean addressBean = gson.fromJson(result, AddressBean.class);
                add_address.setVisibility(View.GONE);
                address_layout.setVisibility(View.VISIBLE);
                user_name.setText(addressBean.getName());
                user_num.setText(addressBean.getTel());
                user_address.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
            }
        }
        if (requestCode == 0x14){
            if (resultCode == 0x15){
                AddressBean addressBean = (AddressBean) data.getSerializableExtra("address_bean");
                add_address.setVisibility(View.GONE);
                address_layout.setVisibility(View.VISIBLE);
                user_name.setText(addressBean.getName());
                user_num.setText(addressBean.getTel());
                user_address.setText(addressBean.getProvince() + addressBean.getCity() + addressBean.getCounty() + addressBean.getAddress());
            }
        }
    }
}
