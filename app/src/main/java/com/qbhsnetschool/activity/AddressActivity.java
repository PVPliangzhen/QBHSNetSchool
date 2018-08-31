package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.JsonBean;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.GetJsonDataUtil;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressActivity extends BaseActivity{

    private EditText receive_goods;
    private ImageView receive_goods_delete;
    private EditText phone_number;
    private ImageView phone_number_delete;
    private TextView region;
    private EditText address_detail;
    private ImageView address_detail_delete;
    private TextView save_address;
    private AddressActivity activity;
    private AddressHandler addressHandler;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String province = "";
    private String city = "";
    private String country = "";

    private static class AddressHandler extends Handler{
        WeakReference<AddressActivity> weakReference;
        public AddressHandler(AddressActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AddressActivity addressActivity = weakReference.get();
            if (addressActivity != null){
                switch (msg.what){
                    case MSG_LOAD_FAILED:
                        //Toast.makeText(addressActivity, "Parse Failed", Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_LOAD_SUCCESS:
                        //Toast.makeText(addressActivity, "Parse Success", Toast.LENGTH_SHORT).show();
                        break;
                    case 0x11:
                        try{
                            String result = (String) msg.obj;
                            JSONObject jsonObject = new JSONObject(result);
                            String code = jsonObject.optString("code");
                            if (code.equalsIgnoreCase("200")){
                                Toast.makeText(addressActivity, "地址添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("result", result);
                                addressActivity.setResult(0x11, intent);
                                addressActivity.finish();
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
        setBaseContentView(R.layout.activity_address, false, R.color.status_bar_bg_color, false);
        activity = this;
        addressHandler = new AddressHandler(activity);
        initJsonData();
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("地址");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        receive_goods = (EditText) findViewById(R.id.receive_goods);
        receive_goods.setOnFocusChangeListener(focusChangeListener);
        receive_goods_delete = (ImageView) findViewById(R.id.receive_goods_delete);
        phone_number = (EditText) findViewById(R.id.phone_number);
        phone_number_delete = (ImageView) findViewById(R.id.phone_number_delete);
        phone_number.setOnFocusChangeListener(focusChangeListener);
        region = (TextView) findViewById(R.id.region);
        region.setOnClickListener(clickListener);
        address_detail = (EditText) findViewById(R.id.address_detail);
        address_detail.setOnFocusChangeListener(focusChangeListener);
        address_detail_delete = (ImageView) findViewById(R.id.address_detail_delete);
        save_address = (TextView) findViewById(R.id.save_address);
        save_address.setOnClickListener(clickListener);
        receive_goods.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (receive_goods.getText().toString().trim().length() > 0) {
                    receive_goods_delete.setVisibility(View.VISIBLE);
                } else {
                    receive_goods_delete.setVisibility(View.GONE);
                }
            }
        });
        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phone_number.getText().toString().trim().length() > 0) {
                    phone_number_delete.setVisibility(View.VISIBLE);
                } else {
                    phone_number_delete.setVisibility(View.GONE);
                }
            }
        });
        address_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (address_detail.getText().toString().trim().length() > 0) {
                    address_detail_delete.setVisibility(View.VISIBLE);
                } else {
                    address_detail_delete.setVisibility(View.GONE);
                }
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.region:
                    showPickerView();
                    break;
                case R.id.receive_goods_delete:
                    receive_goods.setText("");
                    break;
                case R.id.phone_number_delete:
                    phone_number.setText("");
                    break;
                case R.id.address_detail_delete:
                    address_detail.setText("");
                    break;
                case R.id.save_address:
                    if (!UIUtils.isNetworkAvailable(activity)){
                        Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtils.isEmpty(receive_goods.getText().toString().trim())){
                        Toast.makeText(activity, "请填写收货人", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtils.isEmpty(phone_number.getText().toString().trim())){
                        Toast.makeText(activity, "请填写手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!judgePhoneNumberFormat(phone_number.getText().toString().trim())){
                        Toast.makeText(activity, "请填写正确的手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtils.isEmpty(region.getText().toString().trim())){
                        Toast.makeText(activity, "请填写所在地区", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StringUtils.isEmpty(address_detail.getText().toString().trim())){
                        Toast.makeText(activity, "请填写详细地址", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LoadingDialog.loading(activity);
                    Map<String, String> params = new HashMap<>();
                    params.put("name", receive_goods.getText().toString().trim());
                    params.put("tel", phone_number.getText().toString().trim());
                    params.put("uid", UserManager.getInstance().getUser().getUserId() + "");
                    params.put("province", province);
                    params.put("city", city);
                    params.put("county", country);
                    params.put("address", address_detail.getText().toString().trim());
                    HttpHelper.httpRequest(UrlHelper.addAddress(), params, "POST", new StandardCallBack(activity) {
                        @Override
                        public void onSuccess(String result) {
                            Message message = Message.obtain();
                            message.what = 0x11;
                            message.obj = result;
                            addressHandler.sendMessage(message);
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat(String phonenum) {
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(phonenum);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                //Toast.makeText(activity, tx, Toast.LENGTH_SHORT).show();
                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(options2);
                country = options3Items.get(options1).get(options2).get(options3);
                String regionResult = province + "-" + city + "-" + country;
                region.setText(regionResult);
            }
        }).setTitleText("所在地").setDividerColor(Color.BLACK).setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20).build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        addressHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addressHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                switch (view.getId()) {
                    case R.id.receive_goods:
                        if (StringUtils.isEmpty(receive_goods.getText().toString().trim())) {
                            receive_goods_delete.setVisibility(View.GONE);
                        } else {
                            receive_goods_delete.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.phone_number:
                        if (StringUtils.isEmpty(phone_number.getText().toString().trim())) {
                            phone_number_delete.setVisibility(View.GONE);
                        } else {
                            phone_number_delete.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.address_detail:
                        if (StringUtils.isEmpty(address_detail.getText().toString().trim())) {
                            address_detail_delete.setVisibility(View.GONE);
                        } else {
                            address_detail_delete.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (view.getId()) {
                    case R.id.receive_goods:
                        receive_goods_delete.setVisibility(View.GONE);
                        break;
                    case R.id.phone_number:
                        phone_number_delete.setVisibility(View.GONE);
                        break;
                    case R.id.address_detail:
                        address_detail_delete.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };
}
