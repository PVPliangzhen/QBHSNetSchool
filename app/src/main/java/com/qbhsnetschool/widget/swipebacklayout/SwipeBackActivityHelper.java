package com.qbhsnetschool.widget.swipebacklayout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.qbhsnetschool.R;


/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {
	private Activity mActivity;

	private SwipeBackLayout mSwipeBackLayout;

	public SwipeBackActivityHelper(Activity activity) {
		mActivity = activity;
	}

	public void onActivityCreate() {
		mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
		mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity).inflate(R.layout.swipeback_layout, null);
		//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		//			mSwipeBackLayout.findViewById(R.id.swipe).setPadding(0,
		//					UIUtils.getStatusBarHeight(mActivity), 0, 0);
		//		}
		mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
			@Override
			public void onScrollStateChange(int state, float scrollPercent) {
			}

			@Override
			public void onEdgeTouch(int edgeFlag) {
				Utils.convertActivityToTranslucent(mActivity);
			}

			@Override
			public void onScrollOverThreshold() {

			}
		});
	}

	public void onPostCreate() {
		mSwipeBackLayout.attachToActivity(mActivity);
	}

	public View findViewById(int id) {
		if (mSwipeBackLayout != null) {
			return mSwipeBackLayout.findViewById(id);
		}
		return null;
	}

	public SwipeBackLayout getSwipeBackLayout() {
		return mSwipeBackLayout;
	}
}
