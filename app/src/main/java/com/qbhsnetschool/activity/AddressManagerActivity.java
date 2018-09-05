package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.qbhsnetschool.adapter.AddressAdapter;
import com.qbhsnetschool.entity.AddressBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddressManagerActivity extends BaseActivity {

    private AddressManagerActivity activity;
    private RecyclerView address_list;
    private AddressManagerHandler addressManagerHandler;
    private AddressAdapter addressAdapter;
    private List<AddressBean> addressBeans;

    private static class AddressManagerHandler extends Handler {
        WeakReference<AddressManagerActivity> weakReference;

        public AddressManagerHandler(AddressManagerActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AddressManagerActivity addressManagerActivity = weakReference.get();
            if (addressManagerActivity != null) {
                switch (msg.what) {
                    case 0x01:
                        if (!LoadingDialog.isDissMissLoading()) {
                            LoadingDialog.dismissLoading();
                        }
                        String result = (String) msg.obj;
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray addresses = jsonObject.optJSONArray("addresses");
                            Gson gson = new Gson();
                            addressManagerActivity.addressBeans = gson.fromJson(addresses.toString(), new TypeToken<List<AddressBean>>() {
                            }.getType());
                            if (addressManagerActivity.addressAdapter != null) {
                                addressManagerActivity.addressAdapter.setDate(addressManagerActivity.addressBeans);
                                addressManagerActivity.addressAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
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
        initView();
    }

    private void initView() {
        address_list = (RecyclerView) findViewById(R.id.address_list);
        addressAdapter = new AddressAdapter(activity);
        address_list.setAdapter(addressAdapter);
        initAddressDeleteListener();
        initAddressEditListener();
        addressAdapter.setOnAddressSelectListener(new AddressAdapter.AddressSelectListener() {
            @Override
            public void onAddressSelect(int position) {
                if (addressBeans != null && addressBeans.size() > 0) {
                    AddressBean addressBean = addressBeans.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("address_bean", addressBean);
                    setResult(0x15, intent);
                    finish();
                }
            }
        });
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        address_list.setLayoutManager(lm);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("地址管理");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView add_address_btn = (TextView) findViewById(R.id.add_address_btn);
        add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, AddressActivity.class);
                setResult(0x22);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initAddressEditListener() {
        addressAdapter.setOnAddressEditListener(new AddressAdapter.AddressEditListener() {
            @Override
            public void onAddressEdit(int position) {
                Intent intent = new Intent();
                intent.setClass(activity, AddressActivity.class);
                intent.putExtra("is_from_edit", true);
                intent.putExtra("addressbean", addressBeans.get(position));
                setResult(0x20);
                startActivityForResult(intent, 0x12);
            }
        });
    }

    private void initAddressDeleteListener() {
        addressAdapter.setOnAddressDeleteListener(new AddressAdapter.AddressDeleteListener() {
            @Override
            public void onAddressDelete(final int position) {
                if (!UIUtils.isNetworkAvailable(activity)) {
                    Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (addressBeans != null && addressBeans.size() > 0) {
                    AddressBean addressBean = addressBeans.get(position);
                    LoadingDialog.loading(activity);
                    HttpHelper.httpRequest(UrlHelper.deleteAddress(addressBean.getId()), null, "DELETE", new StandardCallBack(activity) {
                        @Override
                        public void onSuccess(final String result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result == null) {
                                        return;
                                    }
                                    try {
                                        if (!LoadingDialog.isDissMissLoading()){
                                            LoadingDialog.dismissLoading();
                                        }
                                        JSONObject jsonObject = new JSONObject(result);
                                        String code = jsonObject.optString("code");
                                        if (code.equalsIgnoreCase("1300")) {
                                            String msg = jsonObject.optString("msg");
                                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                            addressBeans.remove(position);
                                            addressAdapter.setDate(addressBeans);
                                            addressAdapter.notifyDataSetChanged();
                                            setResult(0x21);
                                        } else {
                                            Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x12){
            if (resultCode == 0x13){
                initData();
            }
        }
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)) {
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
