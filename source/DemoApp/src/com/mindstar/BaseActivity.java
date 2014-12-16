package com.mindstar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.astro.mindstar.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mindstar.fragments.CruunMenuFragment;

public class BaseActivity extends SlidingFragmentActivity {

	/**
	 * Title of an application
	 */
	private int mTitleRes;

	/**
	 * Fragment that contain the instance of different fragments
	 */
	protected Fragment mFrag;

	private TextView mBadge;

	private ImageView mNotification;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle("Android");

		showActionBar();
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#0ab2e8")));

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new CruunMenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		}
		// } else {
		// mFrag = (ListFragment) this.getSupportFragmentManager()
		// .findFragmentById(R.id.menu_frame);
		// }

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setSlidingEnabled(true); // to disable sliding by swiping
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayOptions(0,
				ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
		calculateScreenWidthAndHeight();
	}

	private void showActionBar() {
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.custom_actionbar, null);
		ActionBar actionBar = getSupportActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(false);
		// actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		// actionBar.setDisplayShowTitleEnabled(false);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, Gravity.LEFT);
		actionBar.setCustomView(v, lp);
		initializeVariables(v);
	}

	private void initializeVariables(View v) {

		((ImageView) v.findViewById(R.id.menu_left))
				.setOnClickListener(toggleClick);
		((ImageView) v.findViewById(R.id.cruun_logo_top))
				.setOnClickListener(toggleClick);

	}

	private void calculateScreenWidthAndHeight() {

		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

	}

	private OnClickListener toggleClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			toggle();

		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {

			toggle();

			return true;

		}

		return super.onKeyDown(keyCode, event);
	}

}
