package com.travelcheck.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.travelcheck.model.FavouritesModel;
import com.travelcheck.util.Util;

public class ClickListenerForEmail implements OnClickListener {

	private Context m_context;
	private Activity m_activity;
	FavouritesModel mModel;

	public ClickListenerForEmail(Context p_context, FavouritesModel p_model) {

		m_context = p_context;
		m_activity = (Activity) p_context;
		mModel = p_model;
	}

	@Override
	public void onClick(View v) {
		try {
			String l_email = mModel.getmName();
			String l_message = "Hi friend, I am in trouble. Please help me. You can reach me according to my location \n"
					+ "Latitude is= "
					+ Util.mLatitude
					+ " and Longitude is= "
					+ Util.mLongitude
					+ "\n"
					+ "I am now at { "
					+ Util.locationAddress + " }"
					+"\n"
					+Html.fromHtml("<b>(From Travel check)</b>");
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.fromParts("mailto", l_email, null));
			emailIntent
					.putExtra(Intent.EXTRA_SUBJECT, "Hi There! I want help.");
			emailIntent.putExtra(Intent.EXTRA_TEXT, l_message);
			m_activity.startActivity(Intent.createChooser(emailIntent,
					"Send email..."));
		} catch (Exception e) {

			Toast.makeText(m_activity, "No email app found.", 1000).show();
		}

	}
}
