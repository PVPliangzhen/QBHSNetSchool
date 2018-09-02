package com.qbhsnetschool.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayLoginListener;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayLiveInfo;
import com.bokecc.sdk.mobile.live.replay.pojo.ReplayLoginInfo;
import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.DialogUtils;
import com.qbhsnetschool.uitls.UIUtils;
import com.qbhsnetschool.widget.ccvideo.ReplayActivity;

/**
 * created by liangzhen at 2018/8/10
 */
public class SplashActivity extends Activity{

    private SplashActivity activity;
    private DialogUtils dialogUtils;
    private final int REQUEST_PERMINSSION_WRITE_STORAGE_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //setBaseContentView(R.layout.activity_splash, false, false);
        setContentView(R.layout.activity_splash);
        activity = this;
        dialogUtils = new DialogUtils(activity, R.style.dialog_user);
        dialogUtils.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK;
            }
        });

        checkStoragePower();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建登录信息
                ReplayLoginInfo replayLoginInfo = new ReplayLoginInfo();
                replayLoginInfo.setUserId("B27039502337407C");
                replayLoginInfo.setRoomId("080D04CB846F0FB29C33DC5901307461");
                replayLoginInfo.setLiveId("50743DD69A9B2C60");
                replayLoginInfo.setRecordId("3804F642D564BE78");
                replayLoginInfo.setViewerName("111");
                replayLoginInfo.setViewerToken("111");

                // 设置登录参数
                DWLiveReplay.getInstance().setLoginParams(new DWLiveReplayLoginListener() {
                    @Override
                    public void onException(final DWLiveException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onLogin(TemplateInfo templateInfo) {runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 回放的直播开始时间和结束时间必须在登录成功后再获取，否则为空
                                ReplayLiveInfo replayLiveInfo = DWLiveReplay.getInstance().getReplayLiveInfo();
                                if (replayLiveInfo != null) {
                                    Toast.makeText(SplashActivity.this, "直播开始时间：" + replayLiveInfo.getStartTime() + "\n"
                                            + "直播结束时间：" +  replayLiveInfo.getEndTime(), Toast.LENGTH_LONG).show();
                                }
                                Intent intent = new Intent(SplashActivity.this, ReplayActivity.class);
                                startActivity(intent);
                            }
                        });

                    }
                }, replayLoginInfo);

                // 设置是否使用Https
                DWLiveReplay.getInstance().setSecure(false);

                // 执行登录操作
                DWLiveReplay.getInstance().startLogin();
            }
        });
    }

    private void checkStoragePower() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            showGuideDialog(1);
        }else{
            gotoHomeActivity();
        }
    }

    private void showGuideDialog(final int type) {
        switch (type) {
            case 1:
                dialogUtils.setDialog_ok("下一步");
                dialogUtils.hideCancel();
                dialogUtils.setDialog_title("开启华罗庚网校");
                dialogUtils.setDialog_text("为了您正常使用华罗庚网校,需要以下权限\n\n存储空间");
                dialogUtils.setCanceledOnTouchOutside(false);
                setDialogListener(1);
                dialogUtils.show();
                break;
            case 2:
                dialogUtils.setDialog_ok("确定");
                dialogUtils.setDialog_cancel("取消");
                dialogUtils.setDialog_title("请允许存储空间权限");
                dialogUtils.setDialog_text("我们需要获取存储空间权限,否则您将无法正常使用华罗庚网校");
                dialogUtils.showCancel();
                dialogUtils.setCanceledOnTouchOutside(false);
                setDialogListener(2);
                dialogUtils.show();
                break;
            case 3:
                dialogUtils.setDialog_ok("去设置");
                dialogUtils.setDialog_cancel("拒绝");
                dialogUtils.setDialog_title("请允许存储空间权限");
                dialogUtils.setDialog_text("我们需要获取存储空间权限,否则您将无法正常使用华罗庚网校");
                dialogUtils.showCancel();
                dialogUtils.setCanceledOnTouchOutside(false);
                setDialogListener(3);
                dialogUtils.show();
                break;
        }
    }

    private void setDialogListener(final int type) {
        dialogUtils.setOnOkListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 1:
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMINSSION_WRITE_STORAGE_CODE);
                        dialogUtils.dismiss();
                        break;
                    case 2:
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMINSSION_WRITE_STORAGE_CODE);
                        dialogUtils.dismiss();
                        break;
                    case 3:
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "com.qbhsnetschool", null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialogUtils.dismiss();
                        finish();
                        break;
                }
            }
        });

        dialogUtils.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 2:
                        finish();
                        break;
                    case 3:
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMINSSION_WRITE_STORAGE_CODE) {
            boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotoHomeActivity();
            } else {
                if (!isTip) {
                    if (dialogUtils.isShowing()) {
                        dialogUtils.dismiss();
                    }
                    showGuideDialog(3);
                } else {
                    if (dialogUtils.isShowing()) {
                        dialogUtils.dismiss();
                    }
                    showGuideDialog(2);
                }
            }
        }
    }

    public void gotoHomeActivity(){
        Intent intent = new Intent();
        intent.setClass(activity, HomeActivity.class);
        startActivity(intent);
        //finish();
    }
}
