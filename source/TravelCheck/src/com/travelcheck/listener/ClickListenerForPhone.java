package com.travelcheck.listener;

import com.travelcheck.model.FavouritesModel;
import com.travelcheck.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClickListenerForPhone implements OnClickListener {

	private Context m_context;
	private Activity m_activity;
	private FavouritesModel mModel;

	public ClickListenerForPhone(Context p_context, FavouritesModel p_model) {

		m_context = p_context;
		m_activity = (Activity) p_context;
		mModel = p_model;
	}

	@Override
	public void onClick(View v) {

		try {
			String l_phone = mModel.getmName();
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + l_phone));
			m_activity.startActivity(callIntent);
		} catch (Exception e) {

			Toast.makeText(m_activity, "Call facility not found.", 1000).show();
		}

	}

}
