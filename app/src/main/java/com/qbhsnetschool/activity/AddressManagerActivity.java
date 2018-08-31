package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.AddressAdapter;
import com.qbhsnetschool.entity.AddressBean;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddressManagerActivity extends BaseActivity{

    private AddressManagerActivity activity;
    private RecyclerView address_list;
    private AddressManagerHandler addressManagerHandler;
    private AddressAdapter addressAdapter;

    private static class AddressManagerHandler extends Handler{
        WeakReference<AddressManagerActivity> weakReference;

        public AddressManagerHandler(AddressManagerActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AddressManagerActivity addressManagerActivity = weakReference.get();
            if (addressManagerActivity != null){
                switch (msg.what){
                    case 0x01:
                        if (!LoadingDialog.isDissMissLoading()){
                            LoadingDialog.dismissLoading();
                        }
                        String result = (String) msg.obj;
                        try{
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray addresses = jsonObject.optJSONArray("addresses");
                            Gson gson = new Gson();
                            List<AddressBean> addressBeans = gson.fromJson(addresses.toString(), new TypeToken<List<AddressBean>>() {
                            }.getType());
                            if (addressManagerActivity.addressAdapter != null){
                                addressManagerActivity.addressAdapter.setDate(addressBeans);
                                addressManagerActivity.addressAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_address_manage, false, R.color.status_bar_bg_color, false);
        activity = this;
        addressManagerHandler = new AddressManagerHandler(activity);
        address_list = (RecyclerView) findViewById(R.id.address_list);
        addressAdapter = new AddressAdapter(activity);
        address_list.setAdapter(addressAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        address_list.setLayoutManager(lm);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("地址管理");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.addAddress(), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                addressManagerHandler.sendMessage(message);
            }
        });
    }
}
