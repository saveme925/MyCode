package com.travelcheck.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.travelcheck.R;
import com.travelcheck.db.DBHelper;
import com.travelcheck.listener.ClickListenerForMessage;
import com.travelcheck.listener.ClickListenerForPhone;
import com.travelcheck.listener.ClickListenerForRemoveFav;
import com.travelcheck.listener.ClickListenerForSelectEmail;
import com.travelcheck.listener.ClickListenerForEmail;
import com.travelcheck.model.EmailModel;
import com.travelcheck.model.FavouritesModel;

/**
 * @author Sachit Wadhawan
 * 
 */
public class FavouriteListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context mContext;
	private List<FavouritesModel> mCandidateData;
	private DBHelper mDbh;
	private ListView mEmailList;
	private TextView mNoFavText;

	public FavouriteListAdapter(Context pContext,
			List<FavouritesModel> p_contact_list, DBHelper p_dbh,
			ListView p_emailList, TextView p_noFavText) {

		mContext = pContext;
		mDbh = p_dbh;
		mEmailList = p_emailList;
		mNoFavText = p_noFavText;
		mCandidateData = p_contact_list;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static class ViewHolder {

		public TextView mName;
		public ImageView mSelectState;
		public RelativeLayout mWholeLayout;
		public CircularImageView mImage;
		public LinearLayout mCallMeLayout;
		public LinearLayout mMessageMeLayout;
		public LinearLayout mMailMeLayout;
		public LinearLayout mRemoveMeLayout;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCandidateData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private ViewHolder holder;
	private FavouritesModel mModel;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		

		mModel = mCandidateData.get(position);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fav_custom_list, null);
			holder = new ViewHolder();
			findViewById(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		setOnclickListener(position);
		setTextOnInputFields();

		return convertView;
	}

	private void setTextOnInputFields() {

		if (mModel.getmType().equals("email")) {

			holder.mCallMeLayout.setVisibility(View.GONE);
			holder.mMessageMeLayout.setVisibility(View.GONE);
			holder.mMailMeLayout.setVisibility(View.VISIBLE);
			holder.mRemoveMeLayout.setVisibility(View.VISIBLE);

		} else {

			holder.mCallMeLayout.setVisibility(View.VISIBLE);
			holder.mMessageMeLayout.setVisibility(View.VISIBLE);
			holder.mMailMeLayout.setVisibility(View.GONE);
			holder.mRemoveMeLayout.setVisibility(View.VISIBLE);
		}

		holder.mName.setText(mModel.getmName());
		holder.mImage.setImageResource(R.drawable.default_avatar);

	}

	private void setOnclickListener(int p_count) {

		ClickListenerForEmail l_email = new ClickListenerForEmail(mContext,
				mModel);
		holder.mMailMeLayout.setOnClickListener(l_email);

		ClickListenerForPhone l_phone = new ClickListenerForPhone(mContext,
				mModel);
		holder.mCallMeLayout.setOnClickListener(l_phone);

		ClickListenerForMessage l_message = new ClickListenerForMessage(
				mContext, mModel);
		holder.mMessageMeLayout.setOnClickListener(l_message);

		ClickListenerForRemoveFav l_removeFav = new ClickListenerForRemoveFav(
				mContext, mModel, mDbh, mCandidateData, p_count,mEmailList,mNoFavText);
		holder.mRemoveMeLayout.setOnClickListener(l_removeFav);

	}

	private void findViewById(View pConvertView) {

		holder.mWholeLayout = (RelativeLayout) pConvertView
				.findViewById(R.id.whole_row_relativelayout);
		holder.mName = (TextView) pConvertView
				.findViewById(R.id.candidatename_textview);
		holder.mImage = (CircularImageView) pConvertView
				.findViewById(R.id.candidate_profileimage_imageview);
		holder.mSelectState = (ImageView) pConvertView
				.findViewById(R.id.select_candidate_imageview);
		holder.mSelectState.setVisibility(View.INVISIBLE);
		holder.mCallMeLayout = (LinearLayout) pConvertView
				.findViewById(R.id.callme_linearlayout);
		holder.mMessageMeLayout = (LinearLayout) pConvertView
				.findViewById(R.id.message_linearlayout);
		holder.mMailMeLayout = (LinearLayout) pConvertView
				.findViewById(R.id.email_linearlayout);
		holder.mRemoveMeLayout = (LinearLayout) pConvertView
				.findViewById(R.id.remove_linearlayout);

	}

}
