package com.qbhsnetschool.widget.ccvideo.popup;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.qbhsnetschool.R;
import com.qbhsnetschool.widget.ccvideo.utils.PopupAnimUtil;

/**
 * 问卷停止的弹窗
 */
public class QuestionnaireStopPopup extends BasePopupWindow {

    public QuestionnaireStopPopup(Context context) {
        super(context);
    }

    @Override
    protected void onViewCreated() {
        Button btn_confirm_stop = findViewById(R.id.confirm_stop);
        btn_confirm_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 设定Back键不做消失处理
        this.setKeyBackCancel(false);
    }

    @Override
    protected int getContentView() {
        return R.layout.questionnaire_stop_layout;
    }

    @Override
    protected Animation getEnterAnimation() {
        return PopupAnimUtil.getDefScaleEnterAnim();
    }

    @Override
    protected Animation getExitAnimation() {
        return PopupAnimUtil.getDefScaleExitAnim();
    }

}
