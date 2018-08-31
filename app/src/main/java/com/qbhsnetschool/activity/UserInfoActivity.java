package com.qbhsnetschool.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.PersonalInfo;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.CameraUtils;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.qbhsnetschool.uitls.FileUtil;
import com.qbhsnetschool.uitls.GlideCircleTransform;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.StringUtils;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfoActivity extends BaseActivity{

    private ImageView page_back;
    private RelativeLayout avatar_layout;
    private UserInfoActivity activity;
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 相册
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    private File userPhotoPath;
    private File temp;
    private FileUtil fileUtils;
    private ImageView avatar_img;
    private UserInfoHandler userInfoHandler;
    private PersonalInfo personalInfo = null;
    private RelativeLayout nickname_layout, realname_layout;
    private ImageView nickname_img, realname_img;
    private TextView nickname_txt, realname_txt;
    private RelativeLayout birthday_layout;
    private ImageView birthday_img;
    private TextView birthday_txt;
    private RelativeLayout grade_layout;
    private ImageView grade_img;
    private TextView grade_txt;
    private TextView contactNumber;
    private ImageView male_img;
    private TextView male_txt;
    private ImageView female_img;
    private TextView female_txt;

    private static class UserInfoHandler extends Handler{

        private WeakReference<UserInfoActivity> weakReference;

        public UserInfoHandler(UserInfoActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            UserInfoActivity userInfoActivity = weakReference.get();
            if (userInfoActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        userInfoActivity.handlePersonalInfo(result);
                        break;
                }
            }
        }
    }

    private void handlePersonalInfo(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            PersonalInfo personalInfo = gson.fromJson(jsonObject.toString(), PersonalInfo.class);
            this.personalInfo = personalInfo;
            nickname_txt.setText(personalInfo.getNickname());
            realname_txt.setText(personalInfo.getRealname());
            birthday_txt.setText(personalInfo.getBirthday());
            grade_txt.setText(ConstantUtil.getGradeItems().get(personalInfo.getGrade()));
            contactNumber.setText(subStringPhoneNumber(personalInfo.getTel()));
            Glide.with(activity).load(personalInfo.getHeadpic()).asBitmap().placeholder(R.mipmap.avatars).error(R.mipmap.avatars).transform(new GlideCircleTransform(activity, personalInfo.getHeadpic())).into(avatar_img);
            genderEvent(personalInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genderEvent(PersonalInfo personalInfo) {
        String sex = personalInfo.getSex();
        if (StringUtils.isEmpty(sex)){
            male_img.setImageResource(R.mipmap.oval_nor);
            female_img.setImageResource(R.mipmap.oval_nor);
        }
        if (sex.equalsIgnoreCase("m")){
            male_img.setImageResource(R.mipmap.oval_pre);
            female_img.setImageResource(R.mipmap.oval_nor);
        }
        if (sex.equalsIgnoreCase("f")){
            male_img.setImageResource(R.mipmap.oval_nor);
            female_img.setImageResource(R.mipmap.oval_pre);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_user_info, true, R.color.status_bar_bg_color, false);
        activity = this;
        userInfoHandler = new UserInfoHandler(activity);
        initView();
        initData();
    }

    private void initData() {
        LoadingDialog.loading(activity);
        if (UIUtils.isNetworkAvailable(activity)){
            HttpHelper.httpGetRequest(UrlHelper.getPersonalInfo(), "GET", new StandardCallBack(activity) {
                @Override
                public void onSuccess(String result) {
                    Message message = Message.obtain();
                    message.what = 0x01;
                    message.obj = result;
                    userInfoHandler.sendMessage(message);
                }
            });
        }else{
            Toast.makeText(activity, "网络异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("基本信息");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        avatar_layout = (RelativeLayout) findViewById(R.id.avatar_layout);
        avatar_layout.setOnClickListener(clickListener);
        avatar_img = (ImageView) findViewById(R.id.avatar_img);

        nickname_layout = (RelativeLayout) findViewById(R.id.nickname_layout);
        nickname_layout.setOnClickListener(clickListener);
        nickname_img = (ImageView) findViewById(R.id.nickname_img);
        nickname_img.setOnClickListener(clickListener);
        nickname_txt = (TextView) findViewById(R.id.nickname_txt);

        realname_layout = (RelativeLayout) findViewById(R.id.realname_layout);
        realname_layout.setOnClickListener(clickListener);
        realname_img = (ImageView) findViewById(R.id.realname_img);
        realname_img.setOnClickListener(clickListener);
        realname_txt = (TextView) findViewById(R.id.realname_txt);

        birthday_layout = (RelativeLayout) findViewById(R.id.birthday_layout);
        birthday_layout.setOnClickListener(clickListener);
        birthday_img = (ImageView) findViewById(R.id.birthday_img);
        birthday_img.setOnClickListener(clickListener);
        birthday_txt = (TextView) findViewById(R.id.birthday_txt);

        grade_layout = (RelativeLayout) findViewById(R.id.grade_layout);
        grade_layout.setOnClickListener(clickListener);
        grade_img = (ImageView) findViewById(R.id.grade_img);
        grade_img.setOnClickListener(clickListener);
        grade_txt = (TextView) findViewById(R.id.grade_txt);

        contactNumber = (TextView) findViewById(R.id.contact_number);

        male_img = (ImageView) findViewById(R.id.male_img);
        male_img.setOnClickListener(clickListener);
        male_txt = (TextView) findViewById(R.id.male_txt);
        male_txt.setOnClickListener(clickListener);
        female_img = (ImageView) findViewById(R.id.female_img);
        female_img.setOnClickListener(clickListener);
        female_txt = (TextView) findViewById(R.id.female_txt);
        female_txt.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.avatar_layout:
                    createAvatar();
                    break;
                case R.id.nickname_img:
                case R.id.nickname_layout:
                    Intent intent = new Intent(activity, ChangeNickNameActivity.class);
                    startActivityForResult(intent, 0x11);
                    break;
                case R.id.realname_img:
                case R.id.realname_layout:
                    Toast.makeText(activity, "修改真实姓名请联系客服", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.male_img:
                case R.id.male_txt:
                    changeGender("m");
                    break;
                case R.id.female_img:
                case R.id.female_txt:
                    changeGender("f");
                    break;
                case R.id.birthday_layout:
                case R.id.birthday_img:
                    final TimePickerView timePickerView = new TimePickerBuilder(activity, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            if (!UIUtils.isNetworkAvailable(activity)) {
                                Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            birthday_txt.setText(getTime(date));
                            LoadingDialog.loading(activity);
                            Map<String, String> params = new HashMap<>();
                            params.put("birthday", getTime(date));
                            HttpHelper.httpRequest(UrlHelper.getPersonalInfo(), params, "PATCH", new StandardCallBack(activity) {
                                @Override
                                public void onSuccess(final String result) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                String responseCode = jsonObject.optString("code");
                                                if (responseCode.equalsIgnoreCase("200")) {
                                                    if (!LoadingDialog.isDissMissLoading()) {
                                                        LoadingDialog.dismissLoading();
                                                    }
                                                    Toast.makeText(activity, "修改成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(activity, "修改失败", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                        @Override
                        public void onTimeSelectChanged(Date date) {

                        }
                    }).setTitleText("生日").isDialog(false).setType(new boolean[]{true, true, true, false, false, false}).build();
                    timePickerView.show();
                    break;
                case R.id.grade_layout:
                case R.id.grade_img:
                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            if (!UIUtils.isNetworkAvailable(activity)) {
                                Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String grade = ConstantUtil.getGrades().get(options1);
                            grade_txt.setText(grade);
                            LoadingDialog.loading(activity);
                            Map<String, String> params = new HashMap<>();
                            int gradeIndex = ConstantUtil.getGradeIndex().get(grade);
                            params.put("grade", gradeIndex + "");
                            HttpHelper.httpRequest(UrlHelper.getPersonalInfo(), params, "PATCH", new StandardCallBack(activity) {
                                @Override
                                public void onSuccess(final String result) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                String responseCode = jsonObject.optString("code");
                                                if (responseCode.equalsIgnoreCase("200")) {
                                                    if (!LoadingDialog.isDissMissLoading()) {
                                                        LoadingDialog.dismissLoading();
                                                    }
                                                    Toast.makeText(activity, "修改成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(activity, "修改失败", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }).setTitleText("选择年级").isRestoreItem(true).isCenterLabel(false).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                        @Override
                        public void onOptionsSelectChanged(int options1, int options2, int options3) {

                        }
                    }).build();
                    optionsPickerView.setPicker(ConstantUtil.getGrades());
                    optionsPickerView.show();
                    break;
            }
        }

        private String getTime(Date date) {//可根据需要自行截取数据显示
            Log.d("getTime()", "choice date millis: " + date.getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        }

        private void createAvatar() {
            try {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    userPhotoPath = new File(getExternalFilesDir("avatar").getAbsolutePath()
                            + "/portrait.jpg");
                }
                new AlertDialog.Builder(activity).setItems(
                        new String[]{"从相册选择", "拍照上传"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            protected Object clone() throws CloneNotSupportedException {
                                return super.clone();
                            }

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:// 从相册中取
                                        Intent intent = null;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                                                    .EXTERNAL_CONTENT_URI);
                                            startActivityForResult(intent, PHOTOZOOM);
                                        } else {
                                            intent = new Intent(Intent.ACTION_GET_CONTENT);
                                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    IMAGE_UNSPECIFIED);
                                            startActivityForResult(intent, PHOTOZOOM);
                                        }
                                        break;
                                    case 1:// 拍照
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ContextCompat.checkSelfPermission(activity, Manifest.permission
                                                    .CAMERA) !=
                                                    PackageManager.PERMISSION_GRANTED) {
                                                //申请CAMERA的权限
                                                requestPermissions(new String[]{Manifest.permission
                                                        .CAMERA}, REQUEST_PERMISSION_CAMERA_CODE);
                                            } else {
                                                camera();
                                            }
                                        } else {
                                            if (CameraUtils.isCameraCanUse()) {
                                                camera();
                                            } else {
                                                Toast.makeText(activity, "请打开相机权限", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private void changeGender(final String gender) {
        if (!UIUtils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "当前网络不可用，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (personalInfo.getSex().equalsIgnoreCase(gender)){
            return;
        }
        LoadingDialog.loading(activity);
        Map<String, String> params = new HashMap<>();
        params.put("sex", gender);
        HttpHelper.httpRequest(UrlHelper.getPersonalInfo(), params, "PATCH", new StandardCallBack(activity) {
            @Override
            public void onSuccess(final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String responseCode = jsonObject.optString("code");
                            if (responseCode.equalsIgnoreCase("200")) {
                                if (!LoadingDialog.isDissMissLoading()) {
                                    LoadingDialog.dismissLoading();
                                }
                                if (gender.equalsIgnoreCase("m")) {
                                    male_img.setImageResource(R.mipmap.oval_pre);
                                    female_img.setImageResource(R.mipmap.oval_nor);
                                    personalInfo.setSex(gender);
                                }else if (gender.equalsIgnoreCase("f")) {
                                    male_img.setImageResource(R.mipmap.oval_nor);
                                    female_img.setImageResource(R.mipmap.oval_pre);
                                    personalInfo.setSex(gender);
                                }
                                Toast.makeText(activity, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void camera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 相机照片后的临时图片存储位置
            File imagePath = new File(activity.getExternalCacheDir(), "images");
            if (!imagePath.exists())
                imagePath.mkdirs();
            temp = new File(imagePath, "temp.jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getImageByCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(activity, "com.qbhsnetschool", temp);
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            }else {
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(temp));
            }
            startActivityForResult(getImageByCamera, PHOTOHRAPH);
        } else {
            Toast.makeText(activity, "请确认插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camera();
            } else {
                Toast.makeText(activity, "请打开相机权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE) {
            return;
        }
        if (resultCode == 0x12 && requestCode == 0x11){
            initData();
        }
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 从存储位置拿到图片直接进行裁剪
            if (temp == null) {
                File imagePath = new File(activity.getExternalCacheDir(), "images");
                if (!imagePath.exists())
                    imagePath.mkdirs();
                temp = new File(imagePath, "temp.jpg");
            }
            Uri contentUri = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentUri = FileProvider.getUriForFile(activity, "com.qbhsnetschool", temp);
            }else {
                contentUri = Uri.fromFile(temp);
            }
            cropImageUri(contentUri, PHOTORESOULT);
            return;
        }
        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            Uri uri = data.getData();
            cropImageUri(uri, PHOTORESOULT);
            return;
        }
        // 处理结果(裁剪后的图片)
        try {
            if (requestCode == PHOTORESOULT) {
                if (userPhotoPath == null) {
                    userPhotoPath = new File(getExternalFilesDir("avatar").getAbsolutePath()
                            + "/portrait.jpg");
                }

                handleUploadAvatar(userPhotoPath);
//                Bitmap photo = BitmapFactory.decodeFile(userPhotoPath.getAbsolutePath());
//                // 设置图片裁剪大图640*640
//                photo = setImageSize(photo, 650);
//                // 保存图片到sd卡
//                if (fileUtils == null) {
//                    fileUtils = new FileUtil(activity);
//                }
//                fileUtils.writeImage(photo, "portrait.jpg", 80);
//                // 设置图片的裁剪小图220*220
//                photo = setImageSize(photo, 220);
//                // 如果是用相机拍照裁剪图片，裁剪完成后删除临时图片
//                if (temp != null && temp.exists()) {
//                    fileUtils.deleteFile(temp.getAbsolutePath());
//                }
                Glide.with(activity).load(userPhotoPath).asBitmap().placeholder(R.mipmap.avatars).error(R.mipmap.avatars).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).transform(new GlideCircleTransform(activity, userPhotoPath.toString())).into(avatar_img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleUploadAvatar(final File uri) {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        //LoadingDialog.loading(activity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("headpic", uri.getName(), RequestBody.create(MediaType.parse("image/jpg"), uri))
                        .addFormDataPart("user_id", UserManager.getInstance().getUser().getUserId() + "").build();

                Request request = new Request.Builder().url(UrlHelper.upLoadHeads()).post(requestBody).build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String result = response.body().string();
                    System.out.println(result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        HttpHelper.httpRequest(UrlHelper.upLoadHeads(), params, "POST", new StandardCallBack(activity) {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println(result);
//            }
//        });
    }

    /**
     * 开启系统自带的裁剪图片
     *
     * @param uri
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (userPhotoPath == null) {
            userPhotoPath = new File(getExternalFilesDir("avatar").getAbsolutePath()
                    + "/portrait.jpg");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //intent.putExtra("output", Uri.fromFile(userPhotoPath));
//        Uri contentUri = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            contentUri = FileProvider.getUriForFile(activity, "com.qbhsnetschool", temp);
//        } else {
//            contentUri = Uri.fromFile(temp);
//        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(userPhotoPath));
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
         intent.putExtra("scale", true);
         intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // 人脸识别
        startActivityForResult(intent, requestCode);
    }

    /**
     * 设置图片的裁剪大小
     *
     * @param cropImage 要设置大小的图片
     * @param i         设置的尺寸
     * @return
     */
    private Bitmap setImageSize(Bitmap cropImage, int i) {
        if (cropImage.getWidth() > i && cropImage.getHeight() > i) {
            cropImage = ThumbnailUtils.extractThumbnail(cropImage, i, i);
        } else if (cropImage.getWidth() > i && cropImage.getHeight() < i) {
            cropImage = ThumbnailUtils.extractThumbnail(cropImage,
                    cropImage.getWidth(), i);
        } else if (cropImage.getWidth() < i && cropImage.getHeight() > i) {
            cropImage = ThumbnailUtils.extractThumbnail(cropImage, i,
                    cropImage.getHeight());
        }
        return cropImage;
    }

    private String subStringPhoneNumber(String mobile){
        String maskNumber = mobile;
        if (judgePhoneNumberFormat(mobile)) {
            maskNumber = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        }
        return maskNumber;
    }

    /**
     * 判断手机号格式
     *
     * @return
     */
    public boolean judgePhoneNumberFormat(String mobile) {
        Pattern mobileReg = Pattern
                .compile("((^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$)|(^\\d{7,8}$)|(^0[1," +
                        "2]{1}\\d{1}(-|_)?\\d{8}$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}$)|(^0[1,2]{1}\\d{1}(-|_)?\\d{8}" +
                        "(-|_)(\\d{1,4})$)|(^0[3-9]{1}\\d{2}(-|_)?\\d{7,8}(-|_)(\\d{1,4})$))");
        Matcher mobileMatcher = mobileReg.matcher(mobile);
        boolean isMobile = mobileMatcher.matches();
        return isMobile;
    }
}
