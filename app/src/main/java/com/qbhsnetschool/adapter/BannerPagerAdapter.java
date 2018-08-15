package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qbhsnetschool.R;
import com.qbhsnetschool.entity.BannerBean;

import java.util.ArrayList;
import java.util.List;

public class BannerPagerAdapter extends PagerAdapter{

    private Context context;
    private BannerBean bannerBean;
    private int banners [] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};
    private List<String> bannerUrls;

    public BannerPagerAdapter(Context context, BannerBean bannerBean) {
        this.context = context;
        this.bannerBean = bannerBean;
        bannerUrls = new ArrayList<>();
        bannerUrls.add(bannerBean.getPic1());
        bannerUrls.add(bannerBean.getPic2());
        bannerUrls.add(bannerBean.getPic3());
        bannerUrls.add(bannerBean.getPic4());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        ImageView imageView = view.findViewById(R.id.banner_img);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.banner_placeholder).error(R.mipmap.banner_placeholder);
        Glide.with(context).load(bannerUrls.get(position % 4)).apply(requestOptions).into(imageView);
        container.addView(view);
        return view;
    }
}
