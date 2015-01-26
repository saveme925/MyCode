package com.travelcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.travelcheck.adapter.LeftMenuAdapter;
import com.travelcheck.model.LeftMenuModel;

/**
 * @author dipenp
 * 
 *         This activity will add Navigation Drawer for our application and all
 *         the code related to navigation drawer. We are going to extend all our
 *         other activites from this BaseActivity so that every activity will
 *         have Navigation Drawer in it. This activity layout contain one frame
 *         layout in which we will add our child activity layout.
 */
public class BaseActivity extends Activity implements Observer {

	/**
	 * Frame layout: Which is going to be used as parent layout for child
	 * activity layout. This layout is protected so that child activity can
	 * access this
	 * */
	protected FrameLayout frameLayout;

	/**
	 * ListView to add navigation drawer item in it. We have made it protected
	 * to access it in child class. We will just use it in child class to make
	 * item selected according to activity opened.
	 */

	public ListView mDrawerList;
	public List<LeftMenuModel> mItemList;
	/**
	 * List item array for navigation drawer items.
	 * */
	protected String[] mOptionArray = { "Home", "Favourites", "Settings",
			"Alert", "About us" };

	protected int[] mImageArray = { R.drawable.favourites,
			R.drawable.favourites, R.drawable.favourites,
			R.drawable.favourites, R.drawable.favourites };

	/**
	 * Static variable for selected item position. Which can be used in child
	 * activity to know which item is selected from the list.
	 * */
	protected static int position;

	/**
	 * This flag is used just to check that launcher activity is called first
	 * time so that we can open appropriate Activity on launch and make list
	 * item position selected accordingly.
	 * */
	private static boolean isLaunch = true;

	/**
	 * Base layout node of this Activity.
	 * */
	public DrawerLayout mDrawerLayout;

	/**
	 * Drawer listner class for drawer open, close etc.
	 */
	private ActionBarDrawerToggle actionBarDrawerToggle;

	private ActionBar mActionBar;

	private LayoutInflater mInflater;

	private View mCustomView;

	private LinearLayout mSliderLayout;

	private TextView mTitleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);
		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		initializeActionBar();

		mItemList = new ArrayList<LeftMenuModel>();

		for (int i = 0; i < mOptionArray.length; i++) {

			LeftMenuModel l_model = new LeftMenuModel();
			l_model.setmOptionName(mOptionArray[i]);
			l_model.setmOptionImage(mImageArray[i]);
			mItemList.add(l_model);

		}

		LeftMenuAdapter l_adapter = new LeftMenuAdapter(BaseActivity.this,
				mItemList);
		mDrawerList.setAdapter(l_adapter);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(false);
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.menu, /* nav drawer image to replace 'Up' caret */
		R.string.open_drawer, /* "open drawer" description for accessibility */
		R.string.close_drawer) /* "close drawer" description for accessibility */
		{
			@Override
			public void onDrawerClosed(View drawerView) {

				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
			}

			@Override
			public void onDrawerStateChanged(int newState) {
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

		/**
		 * As we are calling BaseActivity from manifest file and this base
		 * activity is intended just to add navigation drawer in our app. We
		 * have to open some activity with layout on launch. So we are checking
		 * if this BaseActivity is called first time then we are opening our
		 * first activity.
		 * */
		if (isLaunch) {
			/**
			 * Setting this flag false so that next time it will not open our
			 * first activity. We have to use this flag because we are using
			 * this BaseActivity as parent activity to our other activity. In
			 * this case this base activity will always be call when any child
			 * activity will launch.
			 */
			isLaunch = false;
			openActivity(0);
		}
	}

	private void initializeActionBar() {

		mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#39845E")));
		 mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mInflater = LayoutInflater.from(this);
		mCustomView = mInflater.inflate(R.layout.header, null);
		mSliderLayout = (LinearLayout) mCustomView
				.findViewById(R.id.slider_linearlayout);
		mSliderLayout.setOnClickListener(sliderClickListener);

		mCustomView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mTitleTextView = (TextView) mCustomView.findViewById(R.id.heading);
		mTitleTextView.setText("Dashboard");
		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
	}

	private OnClickListener sliderClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}

		}
	};

	/**
	 * @param position
	 * 
	 *            Launching activity when any list item is clicked.
	 */
	protected void openActivity(int position) {

		/**
		 * We can set title & itemChecked here but as this BaseActivity is
		 * parent for other activity, So whenever any activity is going to
		 * launch this BaseActivity is also going to be called and it will reset
		 * this value because of initialization in onCreate method. So that we
		 * are setting this in child activity.
		 */
		// mDrawerList.setItemChecked(position, true);
		// setTitle(mOptionArray[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; // Setting currently selected position
											// in this field so that it will be
											// available in our child
											// activities.

	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

	}
}
