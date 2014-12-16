package com.mindstar;

import java.util.Observable;
import java.util.Observer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Base class for all activities.
 * 
 * @author Sachit
 * 
 */
public class BaseActivityForCruun extends SherlockActivity implements Observer {

	/** Image loader to load image from urls given, used in various activities */
	// protected ImageLoader imageLoader = ImageLoader.getInstance();
	/** Constant used to show no network error */
	private final String NETWORK_ERROR = "Network_error";
	/** Constant used to to data error */
	private final String DATA_ERROR = "data_error";

	// protected RecieverForExitAll mReciever = null;

	@Override
	public void onStop() {
		// imageLoader.stop();
		super.onStop();
	}

	
	/**
	 * Progress Dialog for the showing indicator if any background process is
	 * working
	 */
	protected ProgressDialog mProgressDialog = null;

	/**
	 * Method to show progress dialog
	 * 
	 * @param messageToShow
	 *            : Text you want to show on the dialog
	 * @param context
	 *            : Context of current class
	 */
	public void showProgressDialog(String messageToShow, Context context) {
		// Progress dialog initialization
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setIndeterminate(false);
		}
		mProgressDialog.setMessage(messageToShow);
		mProgressDialog.show();
	}

	/**
	 * Method to cancel progress dialog
	 */
	public void cancelProgrssDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.cancel();
		}
		mProgressDialog = null;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
