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
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.CameraUtils;
import com.qbhsnetschool.uitls.FileUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Objects;

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
    private Bitmap photo;
    private FileUtil fileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_user_info, true, R.color.status_bar_bg_color, false);
        activity = this;
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("基本信息");
        page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        avatar_layout = (RelativeLayout) findViewById(R.id.avatar_layout);
        avatar_layout.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.avatar_layout:
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
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
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
                    break;
            }
        }
    };

    private void camera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 相机照片后的临时图片存储位置
            temp = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getImageByCamera.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
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
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 从存储位置拿到图片直接进行裁剪
            if (temp == null) {
                temp = new File(Environment.getExternalStorageDirectory(),
                        "temp.jpg");
            }
            cropImageUri(Uri.fromFile(temp), PHOTORESOULT);
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
                photo = BitmapFactory.decodeFile(userPhotoPath.getAbsolutePath());
                // 设置图片裁剪大图640*640
                photo = setImageSize(photo, 650);
                // 保存图片到sd卡
                if (fileUtils == null) {
                    fileUtils = new FileUtil(activity);
                }
                fileUtils.writeImage(photo, "portrait.jpg", 80);
                // 设置图片的裁剪小图220*220
                photo = setImageSize(photo, 220);
                // 如果是用相机拍照裁剪图片，裁剪完成后删除临时图片
                if (temp != null && temp.exists()) {
                    fileUtils.deleteFile(temp.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(activity, "com.qbhsnetschool", userPhotoPath);
        } else {
            contentUri = Uri.fromFile(userPhotoPath);
        }
        intent.putExtra("output", contentUri);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // intent.putExtra("outputX", outputX);
        // intent.putExtra("outputY", outputY);
        // intent.putExtra("scale", true);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // intent.putExtra("return-data", false);
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
}
