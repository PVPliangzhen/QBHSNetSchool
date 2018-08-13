package com.qbhsnetschool.uitls;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class DialogUtils extends Dialog {

    // loading 的dialog
    private static Dialog loading_dialog;
    //dialog提示标题
    private TextView dialog_title;
    // dialog提示文字
    private TextView dialog_text;
    // dialog取消按钮
    private TextView dialog_cancel;
    // dialog确认按钮
    private TextView dialog_ok;
    //取消和确定中间的分割线
    private View dialog_view_line;

    public DialogUtils(Context context, int theme) {
        super(context, theme);
        LayoutInflater inflater = LayoutInflater.from(context);
//        View contentView = inflater.inflate(R.layout.custom_dialog, null);
//        ScrollView scrollView = (ScrollView) contentView.findViewById(R.id.scrollview);
//        scrollView.setVerticalScrollBarEnabled(false);
//        dialog_text = (TextView) contentView.findViewById(R.id.dialog_text);
//        dialog_cancel = (TextView) contentView.findViewById(R.id.dialog_left);
//        dialog_ok = (TextView) contentView.findViewById(R.id.dialog_right);
//        dialog_view_line = contentView.findViewById(R.id.dialog_view_line);
//        dialog_title = (TextView) contentView.findViewById(R.id.dialog_title);
//        setContentView(contentView);
    }

    public TextView getDialog_text() {
        return dialog_text;
    }

    public void setDialog_text(String text) {
        dialog_text.setText(text);
    }

    public TextView getDialog_cancel() {
        return dialog_cancel;
    }

    public void setDialog_cancel(String text) {
        dialog_cancel.setText(text);
    }

    public TextView getDialog_ok() {
        return dialog_ok;
    }

    public void setDialog_ok(String text) {
        dialog_ok.setText(text);
    }

    public void setDialog_title(String text) {
        dialog_title.setText(text);
    }

    /**
     * 隐藏取消按钮
     */
    public void hideCancel() {
        dialog_cancel.setVisibility(View.GONE);
        dialog_view_line.setVisibility(View.GONE);
    }

    /**
     * 显示取消按钮
     */
    public void showCancel() {
        dialog_cancel.setVisibility(View.VISIBLE);
        dialog_view_line.setVisibility(View.VISIBLE);
    }


    /**
     * 取消按钮
     */
    public void setOnCancelListener(
            View.OnClickListener onClickListener) {
        dialog_cancel.setOnClickListener(onClickListener);
    }

    /**
     * 确认按钮
     */
    public void setOnOkListener(
            View.OnClickListener onClickListener) {
        dialog_ok.setOnClickListener(onClickListener);
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
