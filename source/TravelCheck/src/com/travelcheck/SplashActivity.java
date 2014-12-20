package com.travelcheck;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.travelcheck.R;
import com.travelcheck.app.AppController;
import com.travelcheck.library.util.Constants.ACTIVITY_STATES;

/**
 * @author Sachit Wadhawan
 */

public class SplashActivity extends Activity {

	/**
	 * context to hold reference of an activity
	 */
	private static Context mContext = null;
	/**
	 * this will contain the user id by which session can be maintain of that
	 * particular user.
	 */
	private Long userId;
	private String deviceId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
		new SplashAsync().execute();

	}

	public static Context getSelf() {
		return mContext;
	}

	class SplashAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(3000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			AppController.handleEvent(SplashActivity.this,
					ACTIVITY_STATES.MAIN_APP);
			finish();

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
}
