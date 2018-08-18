package com.qbhsnetschool.uitls;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbhsnetschool.R;

public class LoadingDialog {

    // loading 的dialog
    private static Dialog loading_dialog;

    /**
     * 等待网络获取展示的dialog
     *
     * @param str 等待框里显示的内容
     */
    public static void loading(Context context, String str) {
        if ((loading_dialog != null) && loading_dialog.isShowing()) {
            return;
        }
        loading_dialog = new Dialog(context, R.style.Loading_Dialog);
        loading_dialog.setCanceledOnTouchOutside(false);
        loading_dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                return keyCode != KeyEvent.KEYCODE_BACK;
            }
        });
        loading_dialog.setContentView(R.layout.loading_dialog);
        TextView loading_tv = (TextView) loading_dialog.findViewById(R.id.loading_tv);

        ImageView loading_img = (ImageView) loading_dialog.findViewById(R.id.loading_img);
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.loading);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        loading_img.startAnimation(animation);
        loading_tv.setText(str);
        loading_dialog.show();
    }

    /**
     * 等待网络获取展示的dialog
     */
    public static void loading(Context context) {
        if ((loading_dialog != null) && loading_dialog.isShowing()) {
            return;
        }
        loading_dialog = new Dialog(context, R.style.Loading_Dialog);
        loading_dialog.setCanceledOnTouchOutside(false);
        loading_dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                return keyCode != KeyEvent.KEYCODE_BACK;
            }
        });
        loading_dialog.setContentView(R.layout.loading_dialog);
        TextView loading_tv = (TextView) loading_dialog.findViewById(R.id.loading_tv);

        ImageView loading_img = (ImageView) loading_dialog.findViewById(R.id.loading_img);
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.loading);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        loading_img.startAnimation(animation);
        loading_tv.setText("正在加载中");
        loading_dialog.show();
    }

    public static void dismissLoading() {
        try {
            if ((null != loading_dialog) && loading_dialog.isShowing()) {
                loading_dialog.dismiss();
            }
            loading_dialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dialog是否dissmiss掉---- true为dissmiss ; false 为没有DISSMISS
     *
     * @return 是否成功
     */
    public static boolean isDissMissLoading() {
        return loading_dialog == null;
    }
}
