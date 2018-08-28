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

import com.qbhsnetschool.R;
import com.qbhsnetschool.uitls.DialogUtils;
import com.qbhsnetschool.uitls.UIUtils;

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
        finish();
    }
}
