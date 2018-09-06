package com.qbhsnetschool.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pingplusplus.android.Pingpp;
import com.qbhsnetschool.R;
import com.qbhsnetschool.activity.HomeActivity;
import com.qbhsnetschool.entity.BannerBean;
import com.qbhsnetschool.uitls.ConstantUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;
import java.util.List;

public class BannerPagerAdapter extends PagerAdapter{

    private Context context;
    private BannerBean bannerBean;
    private int banners [] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};
    private List<String> bannerUrls = new ArrayList<>();

    public BannerPagerAdapter(Context context) {
        this.context = context;
    }

    public void setBanner(BannerBean bannerBean){
        this.bannerBean = bannerBean;
        bannerUrls.clear();
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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        ImageView imageView = view.findViewById(R.id.banner_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (bannerUrls != null && bannerUrls.size() > 0) {
            Glide.with(context).load(bannerUrls.get(position % 4)).placeholder(R.mipmap.banner_placeholder).error(R.mipmap.banner_placeholder).into(imageView);
        }else{
            Glide.with(context).load(R.mipmap.banner_placeholder).placeholder(R.mipmap.banner_placeholder).error(R.mipmap.banner_placeholder).into(imageView);
        }
        container.addView(view);
        return view;
    }
}
