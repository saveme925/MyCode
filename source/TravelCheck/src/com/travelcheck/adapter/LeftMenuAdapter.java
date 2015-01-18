package com.travelcheck.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelcheck.R;
import com.travelcheck.db.DBHelper;
import com.travelcheck.model.LeftMenuModel;

/**
 * @author Sachit Wadhawan
 * 
 */
public class LeftMenuAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context mContext;
	private List<LeftMenuModel> mOptionList;

	public LeftMenuAdapter(Context pContext, List<LeftMenuModel> pOptionList) {

		mContext = pContext;
		mOptionList = pOptionList;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public static class ViewHolder {

		public TextView mName;
		public ImageView mImage;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOptionList.size();
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
	private LeftMenuModel mModel;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		mModel = mOptionList.get(position);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.left_menu_custom, null);
			holder = new ViewHolder();
			findViewById(convertView);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		setTextOnInputFields();

		return convertView;
	}

	private void setTextOnInputFields() {

		
		holder.mName.setText(mModel.getmOptionName());
		holder.mImage.setImageResource(mModel.getmOptionImage());

	}

	

	private void findViewById(View pConvertView) {

		
		holder.mName = (TextView) pConvertView
				.findViewById(R.id.option_textview);
		
		holder.mImage = (ImageView) pConvertView
				.findViewById(R.id.option_image_imageview);

	}

}
