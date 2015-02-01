package com.travelcheck.listener;

import java.util.List;

import com.travelcheck.FavouritesContacts;
import com.travelcheck.db.DBHelper;
import com.travelcheck.model.FavouritesModel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ClickListenerForRemoveFav implements OnClickListener {

	private Context m_context;
	private Activity m_activity;
	private FavouritesModel mModel;
	private DBHelper mDbh;
	private List<FavouritesModel> mCandidateData;
	private int mCount;
	private ListView mEmailList;
	private TextView mNoFav;

	public ClickListenerForRemoveFav(Context p_context,
			FavouritesModel p_model, DBHelper p_dbh,
			List<FavouritesModel> p_candidateData, int p_count,
			ListView p_emailList, TextView p_noFavText) {

		m_context = p_context;
		mEmailList = p_emailList;
		mNoFav = p_noFavText;
		m_activity = (Activity) p_context;
		mDbh = p_dbh;
		mModel = p_model;
		mCandidateData = p_candidateData;
		mCount = p_count;
	}

	@Override
	public void onClick(View v) {

		
		String l_email = mModel.getmName();
		long l_id = mDbh.deleteFavouritesList(l_email);
		mCandidateData.remove(mCount);
		if (FavouritesContacts.l_fav_adapter != null)
			FavouritesContacts.l_fav_adapter.notifyDataSetChanged();
		
		if (mCandidateData.size() > 0) {
			mEmailList.setVisibility(View.VISIBLE);
			mNoFav.setVisibility(View.GONE);
		} else {
			mEmailList.setVisibility(View.GONE);
			mNoFav.setVisibility(View.VISIBLE);
		}
		
		Log.i("ID", "" + l_id);

	}

}
