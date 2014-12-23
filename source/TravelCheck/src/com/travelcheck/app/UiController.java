package com.travelcheck.app;

import com.travelcheck.DashBoard;
import com.travelcheck.library.util.Constants.ACTIVITY_STATES;

import android.app.Activity;
import android.content.Intent;

/**
 * @author Sachit
 */
public class UiController {
	/**
	 * @param fromActivity
	 *            current activity
	 * @param toActivityId
	 *            new activity
	 * @param dataToSend
	 *            data from one activity to another activity
	 */
	@SuppressWarnings("unchecked")
	public void handleEvent(Activity fromActivity, byte toActivityId,
			Object dataToSend) {
		Intent intent = null;
		switch (toActivityId) {

		case ACTIVITY_STATES.MAIN_APP:
			 intent = new Intent(fromActivity, DashBoard.class);
			break;

		}
		if (intent != null) {
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			fromActivity.startActivity(intent);
		}

	}

	/**
	 * Method to start activity for Result
	 * 
	 * @param fromActivity
	 * @param toActivityId
	 * @param requestCode
	 * @param dataToSend
	 */
	@SuppressWarnings("unchecked")
	public void handleEventForResult(Activity fromActivity, byte toActivityId,
			int requestCode, Object dataToSend) {
		Intent intent = null;
		switch (toActivityId) {

		// case ACTIVITY_STATES.CALENDER:
		// // intent = new Intent(fromActivity, CalendarView.class);
		// break;
		}

		if (intent != null) {
			fromActivity.startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * Method to clear all activities and get exit from the home
	 * 
	 * @param ctx
	 */
	public static void clearAndLogout(Activity ctx) {

		// if (!(ctx instanceof HomeActivity)) {
		// Intent intent = new Intent(ctx, HomeActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.putExtra("EXIT", true);
		// ctx.startActivity(intent);
		// } else {
		// ((Activity) ctx).finish();
		// }

	}

}
