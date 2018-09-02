package com.qbhsnetschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qbhsnetschool.R;
import com.qbhsnetschool.adapter.AllChapterAdapter;
import com.qbhsnetschool.protocol.HttpHelper;
import com.qbhsnetschool.protocol.StandardCallBack;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.LoadingDialog;
import com.qbhsnetschool.uitls.UIUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class AllChaptersActivity extends BaseActivity{

    private AllChaptersActivity activity;
    private AllChapterHandler allChapterHandler;
    private AllChapterAdapter allChapterAdapter;

    private static class AllChapterHandler extends Handler{
        WeakReference<AllChaptersActivity> weakReference;
        public AllChapterHandler(AllChaptersActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AllChaptersActivity allChaptersActivity = weakReference.get();
            if (allChaptersActivity != null){
                switch (msg.what){
                    case 0x01:
                        String result = (String) msg.obj;
                        allChaptersActivity.handleAllChapters(result);
                        break;
                }
            }
        }
    }

    private void handleAllChapters(String result) {
        try {
            if (!LoadingDialog.isDissMissLoading()){
                LoadingDialog.dismissLoading();
            }
            JSONObject jsonObject = new JSONObject(result);
            if (allChapterAdapter != null){
                allChapterAdapter.setData(null);
                allChapterAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_all_chapters, true, R.color.status_bar_bg_color, false);
        activity = this;
        allChapterHandler = new AllChapterHandler(activity);
        initView();
        initData();
    }

    private void initData() {
        if (!UIUtils.isNetworkAvailable(activity)){
            Toast.makeText(activity, R.string.no_network, Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog.loading(activity);
        HttpHelper.httpGetRequest(UrlHelper.getAllChapters(""), "GET", new StandardCallBack(activity) {
            @Override
            public void onSuccess(String result) {
                Message message = Message.obtain();
                message.what = 0x01;
                message.obj = result;
                allChapterHandler.sendMessage(message);
            }
        });
    }

    private void initView() {
        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText("全部章节");
        ImageView page_back = (ImageView) findViewById(R.id.page_back);
        page_back.setOnClickListener(clickListener);
        allChapterAdapter = new AllChapterAdapter(activity);
        RecyclerView all_chapter_list = (RecyclerView) findViewById(R.id.all_chapter_list);
        LinearLayoutManager lm = new LinearLayoutManager(activity){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        all_chapter_list.setLayoutManager(lm);
        all_chapter_list.setAdapter(allChapterAdapter);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.page_back:
                    finish();
                    break;
            }
        }
    };
}
