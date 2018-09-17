package com.qbhsnetschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.HomeworkAdapter;
import com.qbhsnetschool.entity.AllChapterBean;
import com.qbhsnetschool.entity.ChapterBean;
import com.qbhsnetschool.entity.CourseBean;
import com.qbhsnetschool.entity.HomeworkImgBean;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ShowSubmittedHomeworkActivity extends BaseActivity{

    private ShowSubmittedHomeworkActivity activity;
    private ShowSubmittedHomeworkHandler showSubmittedHomeworkHandler;
    public List<HomeworkImgBean> homeworkImgBeans;
    private HomeworkAdapter homeworkAdapter;
    private boolean from_lately_chapter;
    private int chapterId;

    private static class ShowSubmittedHomeworkHandler extends Handler{
        WeakReference<ShowSubmittedHomeworkActivity> weakReference;
        public ShowSubmittedHomeworkHandler(ShowSubmittedHomeworkActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShowSubmittedHomeworkActivity showSubmittedHomeworkActivity = weakReference.get();
            if (showSubmittedHomeworkActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        showSubmittedHomeworkActivity.handleShowHomeWork(result);
                        break;
                }
            }
        }
    }

    private void handleShowHomeWork(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")){
                JSONArray jsonArray = jsonObject.optJSONArray("msg");
                Gson gson = new Gson();
                homeworkImgBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<HomeworkImgBean>>(){}.getType());
                if (homeworkAdapter != null){
                    homeworkAdapter.setData(homeworkImgBeans);
                    homeworkAdapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_show_submitted_homework, false, R.color.status_bar_bg_color, false);
        activity = this;
        showSubmittedHomeworkHandler = new ShowSubmittedHomeworkHandler(activity);
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("全部作业");
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        from_lately_chapter = getIntent().getBooleanExtra("from_lately_chapter", true);
        if (from_lately_chapter){
            CourseBean courseBean = (CourseBean) getIntent().getSerializableExtra("courseBean");
            chapterId = courseBean.getChapter_lately().getId();
        }else{
            AllChapterBean allChapterBean = (AllChapterBean) getIntent().getSerializableExtra("all_chapter_bean");
            chapterId = allChapterBean.getId();
        }
        RecyclerView homework_list = (RecyclerView) findViewById(R.id.homework_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        homework_list.setLayoutManager(lm);
        homeworkAdapter = new HomeworkAdapter(activity);
        homework_list.setAdapter(homeworkAdapter);
        homeworkAdapter.setShowSingleHomeworkClickListener(new HomeworkAdapter.ShowSingleHomeworkClickListener() {
            @Override
            public void singleHomeworkClick(int position) {
                Intent intent = new Intent();
                intent.setClass(activity, ShowSingleHomeworkActivity.class);
                intent.putExtra("homeworkImgBeans", (Serializable) homeworkImgBeans);
                intent.putExtra("select_position", position);
                startActivity(intent);
            }
        });
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);

        HttpHelper.httpGetRequest(UrlHelper.showHomeworks(chapterId), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                showSubmittedHomeworkHandler.sendMessage(message);
            }
        });
    }
}
