package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.ChapterAdapter;
import com.qbhsnetschool.entity.ChapterBean;
import com.qbhsnetschool.entity.HomeCourseBean;
import com.qbhsnetschool.entity.UserManager;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.GlideCircleTransform;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by liangzhen on 2018/8/23.
 */

public class CourseDetailActivity extends BaseActivity{

    private CourseDetailActivity activity;
    private LinearLayout sign_up_btn;
    private HomeCourseBean homeCourseBean;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private CourseDetailHandler courseDetailHandler;
    private RecyclerView chapter_list;
    private ChapterAdapter chapterAdapter;
    private List<ChapterBean> chapterBeans;

    private static class CourseDetailHandler extends Handler{

        WeakReference<CourseDetailActivity> weakReference;

        public CourseDetailHandler(CourseDetailActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseDetailActivity courseDetailActivity = weakReference.get();
            if (courseDetailActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        courseDetailActivity.handleCourseDigist(result);
                        break;
                }
            }
        }
    }

    private void handleCourseDigist(String result) {
        if (!LoadingDialog.isDissMissLoading()){
            LoadingDialog.dismissLoading();
        }
        try {
            if (result != null){
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.optString("code");
                if (code.equalsIgnoreCase("200")){
                    JSONArray jsonArray = jsonObject.optJSONArray("chapter");
                    Gson gson = new Gson();
                    chapterBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<ChapterBean>>() {}.getType());
                    if (chapterAdapter != null){
                        chapterAdapter.setData(chapterBeans);
                        chapterAdapter.notifyDataSetChanged();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_course_detail, false, R.color.status_bar_bg_color, false);
        activity = this;
        courseDetailHandler = new CourseDetailHandler(activity);
        initIntent();
        initView();
        initData();
    }

    private void initData() {
        String product_id = homeCourseBean.getProduct_id();
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getChapters(product_id), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                courseDetailHandler.sendMessage(message);
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();
        homeCourseBean = (HomeCourseBean) intent.getSerializableExtra("homeCourseBean");
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("课程详情");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        sign_up_btn = (LinearLayout) findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(clickListener);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);
        initDifficulty(homeCourseBean.getStars());
        TextView course_date = (TextView) findViewById(R.id.course_date);
        course_date.setText(homeCourseBean.getCourse_date());
        TextView course_time = (TextView) findViewById(R.id.course_time);
        course_time.setText(homeCourseBean.getCourse_time());
        TextView chapter_time = (TextView) findViewById(R.id.chapter_time);
        chapter_time.setText(homeCourseBean.getChapter_times());

        ImageView teacher_head = (ImageView) findViewById(R.id.teacher_head);
        Glide.with(activity).load(homeCourseBean.getTeacher1().getApp_head_pic_small()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .placeholder(R.mipmap.avatars).error(R.mipmap.avatars)
                .transform(new GlideCircleTransform(activity, homeCourseBean.getTeacher1().getApp_head_pic_small())).into(teacher_head);
        TextView teacher_name = (TextView) findViewById(R.id.teacher_name);
        teacher_name.setText(homeCourseBean.getTeacher1().getName());
        TextView intro1 = (TextView) findViewById(R.id.intro1);
        TextView intro2 = (TextView) findViewById(R.id.intro2);
        TextView intro3 = (TextView) findViewById(R.id.intro3);
        intro1.setText(homeCourseBean.getTeacher1().getIntro1());
        intro2.setText(homeCourseBean.getTeacher1().getIntro2());
        intro3.setText(homeCourseBean.getTeacher1().getIntro3());
        chapter_list = (RecyclerView) findViewById(R.id.chapter_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        chapter_list.setLayoutManager(lm);
        chapterAdapter = new ChapterAdapter(activity);
        chapter_list.setAdapter(chapterAdapter);
        TextView original_price = (TextView) findViewById(R.id.original_price);
        original_price.setText("原价" + homeCourseBean.getOriginal_price() + "元");
        original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        TextView currenr_price = (TextView) findViewById(R.id.current_price);
        currenr_price.setText("￥" + homeCourseBean.getPrice());
    }

    private void initDifficulty(int stars){
        switch (stars){
            case 1:
                star5.setVisibility(View.GONE);
                star4.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                star2.setVisibility(View.GONE);
                break;
            case 2:
                star5.setVisibility(View.GONE);
                star4.setVisibility(View.GONE);
                star3.setVisibility(View.GONE);
                break;
            case 3:
                star5.setVisibility(View.GONE);
                star4.setVisibility(View.GONE);
                break;
            case 4:
                star5.setVisibility(View.GONE);
                break;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
                case R.id.sign_up_btn:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(activity, LoginTrasitActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, ConfirmOrderActivity.class);
                        intent.putExtra("homeCourseBean", homeCourseBean);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
}
