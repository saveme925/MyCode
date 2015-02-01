package com.travelcheck.listener;

import com.travelcheck.model.FavouritesModel;
import com.travelcheck.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClickListenerForMessage implements OnClickListener {

	private Context m_context;
	private Activity m_activity;
	private FavouritesModel mModel;

	public ClickListenerForMessage(Context p_context, FavouritesModel p_model) {

		m_context = p_context;
		m_activity = (Activity) p_context;
		mModel = p_model;
	}

	@Override
	public void onClick(View v) {

		try {
			String l_phone = mModel.getmName();
			String l_message = "Hi friend, I am in trouble. Please help me. You can reach me according to my location \n"
					+ "Latitude is= "
					+ Util.mLatitude
					+ " and Longitude is= "
					+ Util.mLongitude
					+ "\n"
					+ "I am now at { "
					+ Util.locationAddress + "}"
					+"\n"
					+Html.fromHtml("<b>(From Travel check)</b>");
			Uri uri = Uri.parse("smsto:" + l_phone);
			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
			intent.putExtra("sms_body", l_message);
			m_activity.startActivity(intent);
		} catch (Exception e) {

			Toast.makeText(m_activity, "No message app found.", 1000).show();
		}

	}

}
