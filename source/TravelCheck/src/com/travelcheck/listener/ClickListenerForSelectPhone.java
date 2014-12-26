package com.travelcheck.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.travelcheck.R;
import com.travelcheck.model.PhoneModel;

public class ClickListenerForSelectPhone implements OnClickListener {

	private ImageView m_selectedState;
	private Context m_context;
	private PhoneModel m_model;

	public ClickListenerForSelectPhone(ImageView p_selectState,
			Context p_context, PhoneModel p_model) {

		m_selectedState = p_selectState;
		m_context = p_context;
		m_model = p_model;

	}

	@Override
	public void onClick(View v) {

		Drawable drawables = m_selectedState.getBackground();
		Bitmap bitmap = ((BitmapDrawable) drawables).getBitmap();
		Bitmap bitmap2 = ((BitmapDrawable) m_context.getResources()
				.getDrawable(R.drawable.right_inactive)).getBitmap();

		if (bitmap == bitmap2) {

			m_selectedState.setBackgroundResource(R.drawable.right_active);
			m_model.setmCheck(true);

		} else {

			m_selectedState.setBackgroundResource(R.drawable.right_inactive);
			m_model.setmCheck(false);
		}

	}

}
