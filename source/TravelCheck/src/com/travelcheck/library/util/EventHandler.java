package com.travelcheck.library.util;

import java.util.Observable;
import java.util.Observer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.travelcheck.R;
import com.travelcheck.library.api.APIException;
import com.travelcheck.library.api.ApiCall;
import com.travelcheck.library.api.Session;
import com.travelcheck.network.HttpConnectionProvider;

public class EventHandler extends Observable {
	/** String to contain the result of the api calls */
	private Object mResult;
	/** type of the request */
	private int mType;

	/** to check whether to the error dialogs or not */
	private boolean mShowErrorPopups = true;
	/**
	 * BaseActivity instance of the current activity that is derived from the
	 * BaseActivty
	 */
	private Observer mObserver;
	/** */
	private Context mContext;
	/** */
	private String method;
	private boolean checkStatus;
	private boolean isProgressRun;
	public boolean isProgessCancelled;
	/*
	 * Alert dailog with animation
	 */
	public static Dialog malertDialog;
	private AnimationDrawable mAnimation;

	/**
	 * Method is to notify the observer as the request done successfully
	 */

	public void resultSet() {
		// Notify observers of change
		setChanged();
		notifyObservers();
		deleteObserver(mObserver);
		// mContext = null;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return mResult;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return mType;
	}

	/**
	 * AsyncTask to get the image information and load the images to the grid
	 * 
	 * @author Manmohan.Soni
	 * 
	 */
	private class ApiTask extends AsyncTask<ApiCall, Void, Object> {
		/**
		 * Progress Dialog for the showing indicator if any background process
		 * is working
		 */
		private ProgressDialog pd;

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

			if (pd == null) {
				pd = new ProgressDialog(context, R.drawable.spinner_inner_holo);
				pd.setCancelable(false);
			}
			pd.show();
		}

		/**
		 * Method to cancel progress dialog
		 */
		public void cancelProgrssDialog() {
			if (pd != null) {
				// mProgressDialog.cancel();
				pd.dismiss();
				isProgessCancelled = true;
			}
			pd = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// showAnimationDialog(mContext);
			if (checkStatus) {
				showProgressDialog(mContext.getString(R.string.waiting_string),
						mContext);
			}
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(ApiCall... params) {
			try {
				ApiCall apiCall = params[0];
				Session session = new Session();
				HttpConnectionProvider httpConnectionProvider = new HttpConnectionProvider();
				httpConnectionProvider.setHttpMethod(method);
				httpConnectionProvider.setCheckStaus(checkStatus);
				session.invoke(apiCall, httpConnectionProvider);
				return apiCall.getResult();
			} catch (Exception exception) {
				return exception;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			cancelProgrssDialog();
			// cancelAnimateDialog();
			if (result != null) {
				/** If an error come from the server */
				if (result instanceof APIException) {
					String code = ((APIException) result).getMessage();
					/**
					 * If code is not null, means the error is defined by server
					 * for example: email not found
					 */
					if (code != null) {
						try {
							if (mShowErrorPopups) {
								Utility.getAlertDialog(mContext,
										mContext.getString(R.string.app_name),
										code
										/*
										 * mContext.getString(Integer
										 * .parseInt(ErrorExplanations
										 * .get(code)))
										 */, mContext.getString(R.string.ok))
										.show();
							} else {
								mResult = result;
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
							Toast.makeText(mContext,
									"code not handled. code :" + code,
									Toast.LENGTH_LONG).show();
						}
					}
					/**
					 * If code is null, means any exception occurred that is
					 * caused due to some connection failure or any other reason
					 * for example JsonException, IllegalArgumentException and
					 * no error message is coming from the server.
					 */
					else {
						if (mShowErrorPopups) {
							Utility.getAlertDialog(mContext,
									mContext.getString(R.string.app_name),
									mContext.getString(R.string.no_network),
									mContext.getString(R.string.ok)).show();
						} else {
							mResult = result;
						}
					}

				}
				/** If any other unexpected exception occurred */
				else if (result instanceof Exception) {
					if (mShowErrorPopups) {
						Utility.getAlertDialog(mContext,
								mContext.getString(R.string.app_name),
								mContext.getString(R.string.no_network), "Ok")
								.show();
					} else {
						mResult = result;
					}
				}/**
				 * Here the request is successful, if the result is sent by
				 * server then result will be data else the result will be null
				 */
				else {
					mResult = result;
				}

			}

			resultSet();
			super.onPostExecute(result);
		}
	}

}
