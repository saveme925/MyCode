package com.mindstar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * 
 * @author Sachit Wadhawan()
 * 
 */

public class Util {

	private static Context mAppicationContxt;

	/**
	 * @param drawbaleResource
	 * 
	 *            To load an deafult or empty image view
	 */
	public static void loadDiaplyOption(int drawbaleResource) {
	}

	/**
	 * @return base context
	 */
	public static Context getMyApplicationContext() {
		return mAppicationContxt;
	}

	/**
	 * @param appicationContxt
	 */
	public static void setMyApplicationContext(Context appicationContxt) {
		if (mAppicationContxt == null)
			mAppicationContxt = appicationContxt;
	}

	/**
	 * @param mContext
	 * @param url
	 * @param imageResource
	 *            loading image from string
	 */
	public static void loadImageBitmapImageAware(final Context mContext,
			String url, final ImageAware imageResource) {

		if (url != null && url.length() > 0) {

			ImageLoader.getInstance().displayImage(url, imageResource,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap arg2) {

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});

		}

	}

	public static SpannableString underlineText(String text) {
		SpannableString contentUnderline = new SpannableString(text);
		contentUnderline.setSpan(new UnderlineSpan(), 0,
				contentUnderline.length(), 0);
		return contentUnderline;
	}

	public static void makeHyperLink(TextView textView, String text) {
		String htmlText = "<html><head></head><body><u>" + text
				+ "</u></body><html>";
		textView.setText(Html.fromHtml(htmlText));
	}

	// Method to show toast on screen with custom message
	public static void toastMessage(Context p_context, String p_message) {
		if (p_context == null)
			return;
		Toast.makeText(p_context, p_message, Toast.LENGTH_LONG).show();
	}

	// Method to show custom toast with message to user on screen

	public static void customToastMessage(Context p_context, String p_message) {

		Toast l_toast = new Toast(mAppicationContxt);
		l_toast = Toast.makeText(p_context, p_message, Toast.LENGTH_SHORT);
		l_toast.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 0);
		l_toast.show();

	}

	/**
	 * @param regEx
	 * @param inputField
	 * @return whether string validates according to given expressions
	 */
	public static boolean validator(String regEx, String inputField) {
		Pattern pattern_type = Pattern.compile(regEx);
		Matcher matcher_field = pattern_type.matcher(inputField);

		if (matcher_field.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Setting fontFace
	 * 
	 * @param textView
	 */
	public static void setFont(TextView textView) {
		Typeface tf = Typeface.createFromAsset(textView.getContext()
				.getAssets(), "fonts/Madita_Medium.otf");
		textView.setTypeface(tf);

	}

	public static String convertSentenceFirstLetterIntoUpperCase(
			String pSentence) {

		if (pSentence.length() == 0)
			return "";
		else
			return pSentence.substring(0, 1).toUpperCase()
					+ pSentence.substring(1);

	}

	public static void closeKeyBoard(View p_view) {

		InputMethodManager inputManager = (InputMethodManager) mAppicationContxt
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(p_view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
