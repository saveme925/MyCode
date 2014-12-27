package com.travelcheck.listener;

import com.travelcheck.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class ClickListenerForShowPopup implements OnClickListener {

	private Context m_context;
	private Activity m_activity;
	private PopupWindow popupWindow;

	public ClickListenerForShowPopup(Context p_context) {

		m_context = p_context;
		m_activity = (Activity) p_context;
	}

	@Override
	public void onClick(View v) {

		showPopUp(v);

	}

	private void showPopUp(View p_view) {

		LayoutInflater layoutInflater = LayoutInflater.from(m_context);

		View popupView = layoutInflater.inflate(R.layout.popuplayout, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(m_activity.getResources()
				.getDrawable(android.R.color.transparent));
		popupWindow.setAnimationStyle(R.anim.sliding_up);
		// popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(p_view, 50, -30);

	}

}
