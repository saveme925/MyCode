package com.travelcheck.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.travelcheck.library.util.Constants;
import com.travelcheck.model.EmailModel;
import com.travelcheck.model.FavouritesModel;

/**
 * 
 * @author Sachit Wadhawan
 * 
 */

public class Util {

	private static Context mAppicationContxt;
	public static List<FavouritesModel> l_contact_list;
	public static Double mLatitude, mLongitude;
	public static String locationAddress = "";

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

	/**
	 * @param p_imageuri
	 *            Uri of capture image
	 * @param utils
	 * @param p_context
	 * @return Path of an image
	 */

	public static String compressImage(String p_imageuri, Activity p_context,
			ImageLoadingUtils p_utils) {

		String filePath = getRealPathFromURI(p_imageuri, p_context);
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;
		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		options.inSampleSize = p_utils.calculateInSampleSize(options,
				actualWidth, actualHeight);
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
				middleY - bmp.getHeight() / 2, new Paint(
						Paint.FILTER_BITMAP_FLAG));

		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream out = null;
		String filename = getFilename();
		try {
			out = new FileOutputStream(filename);
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return filename;

	}

	/**
	 * @param p_content_uri
	 *            Content uri of an image.
	 * @param p_context
	 * @return Actual path of file.
	 */
	public static String getRealPathFromURI(String p_content_uri,
			Activity p_context) {
		Uri contentUri = Uri.parse(p_content_uri);
		Cursor cursor = p_context.getContentResolver().query(contentUri, null,
				null, null, null);
		if (cursor == null) {
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		}
	}

	/**
	 * @return Custom file name with travel check directory.
	 */

	public static String getFilename() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), Constants.DIRECTORY_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		String uriSting = (file.getAbsolutePath() + "/IMG_"
				+ System.currentTimeMillis() + ".jpg");

		return uriSting;

	}

}
