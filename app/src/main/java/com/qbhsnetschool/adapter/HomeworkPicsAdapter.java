package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbhsnetschool.R;

import java.util.List;

public class HomeworkPicsAdapter extends PagerAdapter {

    private Context context;
    private List<LocalMedia> localMediaList;

    public HomeworkPicsAdapter(Context context){
        this.context = context;
    }

    public HomeworkPicsAdapter(Context context, List<LocalMedia> localMediaList) {
        this.context = context;
        this.localMediaList = localMediaList;
    }

    public void setData(List<LocalMedia> localMediaList){
        this.localMediaList = localMediaList;
    }

    @Override
    public int getCount() {
        return localMediaList == null ? 0 : localMediaList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.homework_item, null);
        ImageView imageView = view.findViewById(R.id.homework_img);
        Glide.with(context).load(localMediaList.get(position).getCompressPath()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
