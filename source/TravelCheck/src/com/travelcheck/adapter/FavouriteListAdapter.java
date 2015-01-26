package com.travelcheck.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.travelcheck.R;
import com.travelcheck.db.DBHelper;
import com.travelcheck.listener.ClickListenerForSelectEmail;
import com.travelcheck.listener.ClickListenerForShowPopup;
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

	public FavouriteListAdapter(Context pContext,
			List<FavouritesModel> p_contact_list) {

		mContext = pContext;
		mCandidateData = p_contact_list;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static class ViewHolder {

		public TextView mName;
		public ImageView mSelectState;
		public RelativeLayout mWholeLayout;
		public CircularImageView mImage;

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

		setOnclickListener();
		setTextOnInputFields();

		return convertView;
	}

	private void setTextOnInputFields() {

		holder.mName.setText(mModel.getmName());
		holder.mImage.setImageResource(R.drawable.default_avatar);

	}

	private void setOnclickListener() {

		ClickListenerForShowPopup l_click = new ClickListenerForShowPopup(
				mContext);
		holder.mSelectState.setOnClickListener(l_click);

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

	}

}
