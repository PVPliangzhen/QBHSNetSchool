package com.qbhsnetschool.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.luck.picture.lib.PicturePreviewActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.HomeworkPicsAdapter;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.vp_transforms.AlphaPageTransformer;
import com.qbhsnetschool.widget.vp_transforms.RotateYTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitHomeWorkActivity extends BaseActivity {

    private int chooseMode = PictureMimeType.ofAll();
    private int themeId;
    private List<LocalMedia> selectList = new ArrayList<>();
    private ViewPager scan_pic_pager;
    private HomeworkPicsAdapter homeworkPicsAdapter;
    private SubmitHomeWorkActivity activity;
    private int currentPosition = 0;
    private ImageView no_data_img;
    private RelativeLayout list_layout;
    private CourseBean courseBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_submit_homework, false, R.color.color_1AE200000, false);
        activity = this;
        courseBean = (CourseBean) getIntent().getSerializableExtra("courseBean");
        ImageView camera_close = (ImageView) findViewById(R.id.camera_close);
        camera_close.setOnClickListener(clickListener);
        themeId = R.style.picture_hlg_style;
        ImageView take_pic_img = (ImageView) findViewById(R.id.take_pic_img);
        take_pic_img.setOnClickListener(clickListener);
        ImageView commit_img = (ImageView) findViewById(R.id.commit_img);
        commit_img.setOnClickListener(clickListener);
        scan_pic_pager = (ViewPager) findViewById(R.id.scan_pic_pager);
        scan_pic_pager.setPageMargin((int) getResources().getDimension(R.dimen.dp30));
        scan_pic_pager.setOffscreenPageLimit(5);
        no_data_img = (ImageView) findViewById(R.id.no_data_img);
        no_data_img.setVisibility(View.VISIBLE);
        list_layout = (RelativeLayout) findViewById(R.id.list_layout);
        list_layout.setVisibility(View.GONE);
        scan_pic_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.camera_close:
                    finish();
                    break;
                case R.id.take_pic_img:
                    takePicImg();
                    break;
                case R.id.commit_img:
                    if (!UIUtils.isNetworkAvailable(activity)) {
                        Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LoadingDialog.loading(activity);
                    OkHttpClient okHttpClient = new OkHttpClient();
                    MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    multipartBuilder.addFormDataPart("user_id", UserManager.getInstance().getUser().getUserId() + "");
                    multipartBuilder.addFormDataPart("course_id", courseBean.getProduct_id());
                    multipartBuilder.addFormDataPart("chapter_id", courseBean.getChapter_lately().getId() + "");
                    multipartBuilder.addFormDataPart("measure", courseBean.getChapter_lately().getMeasure() + "");

                    for (int i = 0; i < selectList.size(); i++) {
                        multipartBuilder.addFormDataPart("homework" + i, selectList.get(i).getPath(), RequestBody.create(MediaType.parse("image/jpg"), new File(selectList.get(i).getPath())));
                    }

                    Request request = new Request.Builder().url(UrlHelper.submitHomework()).post(multipartBuilder.build()).build();

                    try {

                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Toast.makeText(activity, "作业上传失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    final String code = jsonObject.optString("code");
                                    final String msg = jsonObject.optString("msg");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        private void takePicImg() {
            try {
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
                                        // 进入相册 以下是例子：不需要的api可以不写
                                        PictureSelector.create(SubmitHomeWorkActivity.this)
                                                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                                                .maxSelectNum(6)// 最大图片选择数量
                                                .minSelectNum(0)// 最小选择数量
                                                .imageSpanCount(4)// 每行显示个数
                                                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                                                .previewImage(true)// 是否可预览图片
                                                .isCamera(false)// 是否显示拍照按钮
                                                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                                                .enableCrop(false)// 是否裁剪
                                                .compress(true)// 是否压缩
                                                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                                .selectionMedia(selectList)
                                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                                        break;
                                    case 1:// 拍照
                                        // 单独拍照
                                        if (selectList != null && selectList.size() >= 6) {
                                            Toast.makeText(activity, "作业图片不能超过6张", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        PictureSelector.create(SubmitHomeWorkActivity.this)
                                                .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                                                .theme(themeId)// 主题样式设置 具体参考 values/styles
                                                .maxSelectNum(6)// 最大图片选择数量
                                                .minSelectNum(0)// 最小选择数量
                                                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                                                .previewImage(true)// 是否可预览图片
                                                .isCamera(false)// 是否显示拍照按钮
                                                .enableCrop(false)// 是否裁剪
                                                .compress(true)// 是否压缩
                                                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                                                .minimumCompressSize(100)// 小于100kb的图片不压缩
                                                .selectionMedia(selectList)
                                                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        no_data_img.setVisibility(View.GONE);
                        list_layout.setVisibility(View.VISIBLE);
                        homeworkPicsAdapter = new HomeworkPicsAdapter(SubmitHomeWorkActivity.this, selectList);
                        homeworkPicsAdapter.setOnHomeworkClickListener(new HomeworkPicsAdapter.HomeworkClickListener() {
                            @Override
                            public void onHomeworkClick(int position) {
//                                Intent intent = new Intent(activity, ShowSingleHomeworkActivity.class);
//                                intent.putParcelableArrayListExtra("select_list", (ArrayList<? extends Parcelable>) selectList);
//                                intent.putExtra("select_position", position);
//                                startActivity(intent);
                                List<LocalMedia> selectedImages = selectList;

                                List<LocalMedia> medias = new ArrayList<>();
                                for (LocalMedia media : selectedImages) {
                                    medias.add(media);
                                }
                                Intent intent = new Intent(activity, PicturePreviewActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(PictureConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
                                bundle.putSerializable(PictureConfig.EXTRA_SELECT_LIST, (Serializable) selectedImages);
                                bundle.putBoolean(PictureConfig.EXTRA_BOTTOM_PREVIEW, true);
                                bundle.putInt(PictureConfig.EXTRA_POSITION, position);
                                intent.putExtras(bundle);
                                activity.startActivityForResult(intent, PictureConfig.CHOOSE_REQUEST, bundle);
                                overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
                            }
                        });
                        scan_pic_pager.setPageTransformer(false, new AlphaPageTransformer(new RotateYTransformer()));
                        scan_pic_pager.setAdapter(homeworkPicsAdapter);
                        //scan_pic_pager.setCurrentItem(currentPosition);
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                        for (LocalMedia media : selectList) {
                            Log.i("图片-----》", media.getPath());
                        }
                    } else {
                        no_data_img.setVisibility(View.VISIBLE);
                        list_layout.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }
}
