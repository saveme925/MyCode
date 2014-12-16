package com.mindstar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.astro.mindstar.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mindstar.fragments.CruunMenuFragment;
import com.mindstar.library.util.Utility;

public class FragmentChangeActivity extends BaseActivity {

	private Fragment mContent;

	public FragmentChangeActivity() {
		super(R.string.changing_fragments);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View

		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");

		if (mContent == null)
			mContent = new DashBoard();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		setSlidingActionBarEnabled(false);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new CruunMenuFragment()).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).addToBackStack(null)
				.commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void onBackPressed() {

		Utility.popupCustomDialogForExit(this, "Cruun",
				getString(R.string.exit), getString(R.string.yes),
				getString(R.string.cancel));

	}

}
