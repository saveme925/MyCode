package com.mindstar.library.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spanned;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 
 * @author Sachit Wadhawan
 * 
 */
public class Utility {

	

	
	/**
	 * Method to check the status of network
	 * 
	 * @param context
	 * @return true if network available else false
	 */
	public static boolean checkNetworkStatus(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean wifi = false;
		if (info != null)
			wifi = info.isConnected();

		info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean sim_nw = false;
		if (info != null)
			sim_nw = info.isConnected();

		if (wifi || sim_nw) {

			return true;
		}

		return false;
	}

	public static void popupCustomDialog(final Context context, String title,
			String message, String positiveText, String negText) {
		AlertDialog.Builder dialogdbuilder = new AlertDialog.Builder(context);
		if (title != null && title.length() > 0)
			dialogdbuilder.setTitle(title);
		dialogdbuilder.setMessage(message);
		dialogdbuilder.setPositiveButton(positiveText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		dialogdbuilder.setNegativeButton(negText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}
		});
		AlertDialog dialog = dialogdbuilder.create();
		dialog.show();
	}

	public static void alertDialogForApp(final Context context, String title,
			String message, String positiveText) {
		final Activity l_activity	=	(Activity) context;
		AlertDialog.Builder dialogdbuilder = new AlertDialog.Builder(context);
		if (title != null && title.length() > 0)
			dialogdbuilder.setTitle(title);
		dialogdbuilder.setMessage(message);
		dialogdbuilder.setPositiveButton(positiveText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();
				l_activity.finish();

			}
		});

		AlertDialog dialog = dialogdbuilder.create();
		dialog.show();
	}
	
	public static void spannedAlertDialogForApp(final Context context, String title,
			Spanned message, String positiveText) {
		AlertDialog.Builder dialogdbuilder = new AlertDialog.Builder(context);
		if (title != null && title.length() > 0)
			dialogdbuilder.setTitle(title);
		dialogdbuilder.setMessage(message);
		dialogdbuilder.setPositiveButton(positiveText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}
		});

		AlertDialog dialog = dialogdbuilder.create();
		dialog.show();
	}

	/**
	 * Method is to cancel all background activities.
	 * 
	 * @param lContext
	 */

	public static void cancelAllPreviousActivities(Context lContext) {

		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		lContext.startActivity(startMain);
	}

	public interface IMAGE_DIMENSIONS {
		/** image lentgh of breath in pixels for the thumbnails */
		int MAX_IMAGE_SIZE = 150;
	}

	/**
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	/**
	 * Method to check whether the memory card present or not
	 * 
	 * @return true if memory card present in the phone
	 */
	public static boolean isSdCardPresent() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

	}

	/**
	 * Method to print a string of console
	 * 
	 * @param text
	 */
	public static void print(String text) {
		// System.out.println("************" + text + "***********");
	}

	/**
	 * Method to check email validation
	 * 
	 * @param target
	 * @return true if email is valid
	 */
	public final static boolean isValidEmail(CharSequence target) {
		try {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * MEthod to encode String in Base 64 format
	 * 
	 * @param dataToEncode
	 * @return encoded data
	 */
	public static String encodeString(String dataToEncode) {
		try {
			dataToEncode = new String(Base64.encode(dataToEncode.getBytes(),
					Base64.DEFAULT));
		} catch (Exception e) {
			print("Exception encodeString::" + e.getMessage());
		}
		print(dataToEncode);
		return dataToEncode;
	}

	/**
	 * MEthod to decode String
	 * 
	 * @param dataToDecode
	 * @return decoded data
	 */
	public static String decodeString(String dataToDecode) {
		try {
			dataToDecode = new String(Base64.decode(dataToDecode.getBytes(),
					Base64.DEFAULT));
		} catch (Exception e) {
			print("Exception encodeString::" + e.getMessage());
		}
		return dataToDecode;
	}

	/**
	 * Method to get a dialog with title, message and two buttons
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveText
	 * @return AlertDialog
	 */
	public static AlertDialog getAlertDialog(Context context, String title,
			String message, String positiveText) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		/* builder. */builder
				.setMessage(message)
				.setTitle(title)
				.setCancelable(true)
				.setNegativeButton(positiveText,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alertDialogBox = builder.create();

		return alertDialogBox;
	}

	/**
	 * Method to check network availability
	 * 
	 * @param context
	 * @return status
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean status = true/* false */;
		/*
		 * ConnectivityManager connectivityManager = (ConnectivityManager)
		 * context .getSystemService(Context.CONNECTIVITY_SERVICE); NetworkInfo
		 * activeNetworkInfo = connectivityManager .getActiveNetworkInfo(); if
		 * (activeNetworkInfo != null) { status =
		 * activeNetworkInfo.isAvailable(); }
		 */
		return status;
	}

	/**
	 * @param pContext
	 * @param v
	 */
	public static void hideKeyboard(Context pContext, View v) {

		InputMethodManager imm = (InputMethodManager) pContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public static void popupCustomDialogForExit(final Context context,
			String title, String message, String positiveText, String negText) {
		AlertDialog.Builder dialogdbuilder = new AlertDialog.Builder(context);
		if (title != null && title.length() > 0)
			dialogdbuilder.setTitle(title);
		dialogdbuilder.setMessage(message);
		dialogdbuilder.setPositiveButton(positiveText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Utility.cancelAllPreviousActivities(context);

				dialog.cancel();

			}
		});
		dialogdbuilder.setNegativeButton(negText, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}
		});
		AlertDialog dialog = dialogdbuilder.create();
		dialog.show();
	}


}
