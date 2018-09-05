package com.qbhsnetschool.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class RewardProgressActivity extends BaseActivity{

    private RewardProgressActivity activity;
    private ImageView cash_img;
    private ImageView coupon_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reward_progress, true, R.color.status_bar_bg_color, false);
        activity = this;
        initView();
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("奖励进度");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        TextView release_times = (TextView) findViewById(R.id.release_times);
        release_times.setText(Html.fromHtml("领取次数还剩<font color =" + getResources().getColor(R.color.color_E20000) + "><big>0</big></font>次"));
        coupon_img = (ImageView) findViewById(R.id.coupon_img);
        coupon_img.setOnClickListener(clickListener);
        cash_img = (ImageView) findViewById(R.id.cash_img);
        cash_img.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.coupon_img:
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(coupon_img, "scaleX", 1.0f, 0.7f, 1.3f, 1.0f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(coupon_img, "scaleY", 1.0f, 0.7f, 1.3f, 1.0f);
                    animatorSet.setDuration(800);
                    animatorSet.play(scaleX).with(scaleY);
                    animatorSet.start();
                    break;
                case R.id.cash_img:
                    AnimatorSet animatorSet1 = new AnimatorSet();
                    ObjectAnimator translateX = ObjectAnimator.ofFloat(cash_img, "translationX", 0f, -20f, 0, 20f, -10f, 0, 10f, 0);
                    animatorSet1.setDuration(800);
                    animatorSet1.play(translateX);
                    animatorSet1.start();
                    break;
            }
        }
    };
}
