package com.qbhsnetschool.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.HomeworkImgBean;

import java.util.ArrayList;
import java.util.List;

public class SingleHomeworkAdapter extends PagerAdapter{

    private List<HomeworkImgBean> homeworkImgBeans = new ArrayList<>();
    private Context context;

    public SingleHomeworkAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeworkImgBean> homeworkImgBeans){
        this.homeworkImgBeans = homeworkImgBeans;
    }

    @Override
    public int getCount() {
        return homeworkImgBeans == null ? 0 : homeworkImgBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_homework_item, container, false);
        final PhotoView single_homework_img = view.findViewById(R.id.single_homework_img);
        Glide.with(context).load(homeworkImgBeans.get(position).getHomework_img()).asBitmap().placeholder(R.mipmap.banner_placeholder).error(R.mipmap.banner_placeholder).into(single_homework_img);
        container.addView(view);
        return view;
    }
}
