package com.mindstar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astro.mindstar.R;

public class CruunMenuFragment extends Fragment {

	private View mparentView;
	private ListView mList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mparentView = inflater.inflate(R.layout.left_menu_new, null);

		return mparentView;
	}

}
