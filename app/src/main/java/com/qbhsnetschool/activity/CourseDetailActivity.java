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
import com.qbhsnetschool.entity.CourseDetailBean;
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
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by liangzhen on 2018/8/23.
 */

public class CourseDetailActivity extends BaseActivity {

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
    private boolean isHLG;
    private TextView course_title;
    private TextView sign_up_txt;
    private ImageView teacher_head;
    private TextView teacher_name;
    private TextView intro1;
    private TextView intro2;
    private TextView intro3;
    private TextView original_price;
    private TextView currenr_price;
    private TextView hlg_bottom_txt;
    private LinearLayout hlg_bottom_layout;
    private TextView course_date;
    private TextView course_time;
    private TextView chapter_time;
    private CourseDetailBean courseDetailBean;

    private static class CourseDetailHandler extends Handler {

        WeakReference<CourseDetailActivity> weakReference;

        public CourseDetailHandler(CourseDetailActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseDetailActivity courseDetailActivity = weakReference.get();
            if (courseDetailActivity != null) {
                switch (msg.what) {
                    case 0x01:
                        String result = (String) msg.obj;
                        courseDetailActivity.handleCourseDigist(result);
                        break;
                }
            }
        }
    }

    private void handleCourseDigist(String result) {
        if (!LoadingDialog.isDissMissLoading()) {
            LoadingDialog.dismissLoading();
        }
        try {
            if (result != null) {
                JSONObject jsonObject = new JSONObject(result);
                Gson gson = new Gson();
                courseDetailBean = gson.fromJson(jsonObject.toString(), CourseDetailBean.class);
                if (courseDetailBean.getCode().equalsIgnoreCase("200")){
                    if (chapterAdapter != null){
                        chapterAdapter.setData(courseDetailBean.getChapter());
                        chapterAdapter.notifyDataSetChanged();
                    }
                    CourseDetailBean.CourseBean courseBean = courseDetailBean.getCourse();
                    switch (courseBean.getCourse_status()) {
                        case 1:
                            sign_up_txt.setText("立即报名");
                            sign_up_btn.setBackgroundColor(getResources().getColor(R.color.color_E20000));
                            sign_up_btn.setEnabled(true);
                            break;
                        case 2:
                            sign_up_txt.setText("人数已满");
                            sign_up_btn.setBackgroundColor(getResources().getColor(R.color.color_999999));
                            sign_up_btn.setEnabled(false);
                            break;
                        case 3:
                            sign_up_txt.setText("报名未开始");
                            sign_up_btn.setBackgroundColor(getResources().getColor(R.color.color_999999));
                            sign_up_btn.setEnabled(false);
                            break;
                        case 4:
                            sign_up_txt.setText("报名结束");
                            sign_up_btn.setBackgroundColor(getResources().getColor(R.color.color_999999));
                            sign_up_btn.setEnabled(false);
                            break;
                    }
                    initDifficulty(courseBean.getStars());
                    Glide.with(activity).load(courseBean.getTeacher1().getApp_head_pic_small()).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                            .placeholder(R.mipmap.avatars).error(R.mipmap.avatars)
                            .transform(new GlideCircleTransform(activity, courseBean.getTeacher1().getApp_head_pic_small())).into(teacher_head);
                    teacher_name.setText(courseBean.getTeacher1().getName());
                    intro1.setText(courseBean.getTeacher1().getIntro1());
                    intro2.setText(courseBean.getTeacher1().getIntro2());
                    intro3.setText(courseBean.getTeacher1().getIntro3());
                    original_price.setText("原价" + courseBean.getOriginal_price() + "元");
                    original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    currenr_price.setText("￥" + courseBean.getPrice());
                    if (isHLG) {
                        hlg_bottom_txt.setVisibility(View.VISIBLE);
                        hlg_bottom_layout.setVisibility(View.GONE);
                    } else {
                        hlg_bottom_txt.setVisibility(View.GONE);
                        hlg_bottom_layout.setVisibility(View.VISIBLE);
                    }
                    course_title = (TextView) findViewById(R.id.course_title);
                    course_date.setText(courseBean.getCourse_date());
                    course_time.setText(courseBean.getCourse_time());
                    chapter_time.setText(courseBean.getChapter_times());
                }
            }
        } catch (Exception e) {
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
        if (!UIUtils.isNetworkAvailable(activity)) {
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
        isHLG = intent.getBooleanExtra("isHLG", false);
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("课程详情");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        sign_up_btn = (LinearLayout) findViewById(R.id.sign_up_btn);
        sign_up_btn.setOnClickListener(clickListener);
        sign_up_txt = (TextView) findViewById(R.id.sign_up_txt);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);
        course_date = (TextView) findViewById(R.id.course_date);
        course_time = (TextView) findViewById(R.id.course_time);
        chapter_time = (TextView) findViewById(R.id.chapter_time);
        teacher_head = (ImageView) findViewById(R.id.teacher_head);
        teacher_name = (TextView) findViewById(R.id.teacher_name);
        intro1 = (TextView) findViewById(R.id.intro1);
        intro2 = (TextView) findViewById(R.id.intro2);
        intro3 = (TextView) findViewById(R.id.intro3);
        chapter_list = (RecyclerView) findViewById(R.id.chapter_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        chapter_list.setLayoutManager(lm);
        chapterAdapter = new ChapterAdapter(activity);
        chapter_list.setAdapter(chapterAdapter);
        original_price = (TextView) findViewById(R.id.original_price);
        currenr_price = (TextView) findViewById(R.id.current_price);
        hlg_bottom_txt = (TextView) findViewById(R.id.hlg_bottom_txt);
        hlg_bottom_txt.setOnClickListener(clickListener);
        hlg_bottom_layout = (LinearLayout) findViewById(R.id.hlg_bottom_layout);
    }

    private void initDifficulty(int stars) {
        switch (stars) {
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
            switch (view.getId()) {
                case R.id.page_back:
                    finish();
                    break;
                case R.id.sign_up_btn:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(activity, LoginTrasitActivity.class);
                        intent.putExtra("go_to_main", false);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, ConfirmOrderActivity.class);
                        intent.putExtra("courseDetailBean", courseDetailBean);
                        startActivity(intent);
                    }
                    break;
                case R.id.hlg_bottom_txt:
                    if (!UserManager.getInstance().isLogin()) {
                        Intent intent = new Intent(activity, LoginTrasitActivity.class);
                        intent.putExtra("go_to_main", false);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("url", UrlHelper.testUrl(UserManager.getInstance().getUser().getUserId() + "",
                                homeCourseBean.getGrade() + "", homeCourseBean.getExam_id() + ""));
                        intent.setClass(activity, WebActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
}
