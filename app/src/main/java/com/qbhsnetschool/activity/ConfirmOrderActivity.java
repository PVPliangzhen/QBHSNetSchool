package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.MyCouponsAdpter;
import com.qbhsnetschool.entity.AddressBean;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.qbhsnetschool.uitls.GlideCircleTransform;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
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
        add_address.setVisibility(View.GONE);
        address_layout = (RelativeLayout) findViewById(R.id.address_layout);
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
                .placeholder(R.mipmap.teacher_placeholder).error(R.mipmap.teacher_placeholder)
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
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_up_btn:
                    if (!UIUtils.isNetworkAvailable(activity)){
                        Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                    }
                    LoadingDialog.loading(activity);
                    Map<String, String> params = new HashMap<>();
                    params.put("course_id", homeCourseBean.getProduct_id());
                    params.put("address_id", addressBean.getId() + "");
                    params.put("study_course", "1");
                    HttpHelper.httpRequest(UrlHelper.createOrder(), params, "POST", new StandardCallBack(activity) {
                        @Override
                        public void onSuccess(String result) {
//                            System.out.println(result);
                            String data = "{\"code\":\"200\",\"charge\":{\"id\":\"ch_PiTSGSSufXn1bXH44SKa5SO8\",\"object\":\"charge\",\"created\":1535524280,\"livemode\":true,\"paid\":false,\"refunded\":false,\"reversed\":false,\"app\":\"app_uTiDO4CarXnPSeTu\",\"channel\":\"wx\",\"order_no\":\"2018082914312061797910\",\"client_ip\":\"127.0.0.1\",\"amount\":9900,\"amount_settle\":9841,\"currency\":\"cny\",\"subject\":\"四年级暑假第四期直播尖子班(全国适用)尖子班2班\",\"body\":\"四年级暑假第四期直播尖子班(全国适用)尖子班2班\",\"extra\":{},\"time_paid\":null,\"time_expire\":1535531480,\"time_settle\":null,\"transaction_no\":null,\"refunds\":{\"object\":\"list\",\"url\":\"/v1/charges/ch_PiTSGSSufXn1bXH44SKa5SO8/refunds\",\"has_more\":false,\"data\":[]},\"amount_refunded\":0,\"failure_code\":null,\"failure_msg\":null,\"metadata\":{\"phone_number\":\"18701073115\",\"user_id\":23443},\"credential\":{\"object\":\"credential\",\"wx\":{\"appId\":\"wx97d8ae0952554984\",\"partnerId\":\"1512997051\",\"prepayId\":\"wx291431211011855edcd5a3cf0303915673\",\"nonceStr\":\"f6cb100fb0581e4c3f164f11ac181da7\",\"timeStamp\":1535524281,\"packageValue\":\"Sign=WXPay\",\"sign\":\"2DCB2495DF6A96301ADA57ECC4B3B582\"}},\"description\":null}}";

                        }
                    });
                    break;
            }
        }
    };
}
