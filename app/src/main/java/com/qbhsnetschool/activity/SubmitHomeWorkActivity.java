package com.qbhsnetschool.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.HomeworkPicsAdapter;
import com.qbhsnetschool.widget.vp_transforms.AlphaPageTransformer;
import com.qbhsnetschool.widget.vp_transforms.RotateYTransformer;

import java.util.ArrayList;
import java.util.List;

public class SubmitHomeWorkActivity extends Activity {

    private Button take_pic_to_upload;
    private Button submit_pic;
    private int chooseMode = PictureMimeType.ofAll();
    private int themeId;
    private Button take_pic_from_album;
    private List<LocalMedia> selectList = new ArrayList<>();
    private ViewPager scan_pic_pager;
    private HomeworkPicsAdapter homeworkPicsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_homework);
        take_pic_to_upload = findViewById(R.id.take_pic_to_upload);
        submit_pic = findViewById(R.id.submit_pic);
        take_pic_from_album = findViewById(R.id.take_pic_from_album);
        take_pic_from_album.setOnClickListener(clickListener);
        take_pic_to_upload.setOnClickListener(clickListener);
        submit_pic.setOnClickListener(clickListener);
        themeId = R.style.picture_hlg_style;
        scan_pic_pager = findViewById(R.id.scan_pic_pager);
        scan_pic_pager.setPageMargin(10);
        scan_pic_pager.setOffscreenPageLimit(5);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.take_pic_from_album:
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
                case R.id.take_pic_to_upload:
                    // 单独拍照
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
                case R.id.submit_pic:

                    break;
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
                    homeworkPicsAdapter = new HomeworkPicsAdapter(SubmitHomeWorkActivity.this, selectList);
                    scan_pic_pager.setPageTransformer(false, new AlphaPageTransformer(new RotateYTransformer()));
                    scan_pic_pager.setAdapter(homeworkPicsAdapter);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    break;
            }
        }
    }
}
