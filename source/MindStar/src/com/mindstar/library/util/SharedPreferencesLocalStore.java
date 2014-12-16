package com.mindstar.library.util;

import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author AND-08
 *
 */
public class SharedPreferencesLocalStore {

	private static final String TAG = "SharedPreferencesLocalStore";

	/**
	 * @param context
	 */
	public static void clear(Context context) {

		clear(context, "unknown");
	}

	/**
	 * @param context
	 * @param caller
	 */
	public static void clear(Context context, String caller) {

		Editor editor = context.getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * @param key
	 * @param value
	 * @param context
	 * @return
	 */
	public static boolean setCustomBooleanData(String key, boolean value,
			Context context) {

		Editor editor = context.getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);

		return editor.commit();
	}

	/**
	 * @param key
	 * @param value
	 * @param context
	 * @return
	 */
	public static boolean setCustomIntegerData(String key, Integer value,
			Context context) {

		Editor editor = context.getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);

		return editor.commit();
	}

	/**
	 * @param key
	 * @param context
	 * @return
	 */
	public static int getCustomIntegerData(String key, Context context) {

		SharedPreferences savedSession = context.getSharedPreferences(
				Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

		return savedSession.getInt(key, -1);
	}

	/**
	 * @param key
	 * @param context
	 * @return
	 */
	public static boolean getCustomBooleanData(String key, Context context) {

		SharedPreferences savedSession = context.getSharedPreferences(
				Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getBoolean(key, false));
	}

	/**
	 * @param key
	 * @param value
	 * @param context
	 * @return
	 */
	public static boolean setCustomStringData(String key, String value,
			Context context) {

		Editor editor = context.getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, value);

		return editor.commit();
	}

	/**
	 * @param key
	 * @param context
	 * @return
	 */
	public static String getCustomStringData(String key, Context context) {

		SharedPreferences savedSession = context.getSharedPreferences(
				Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getString(key, null));
	}

	/**
	 * @param customKey
	 * @param context
	 * @return
	 */
	public static boolean isCustomStringExistInLocal(String customKey,
			Context context) {

		SharedPreferences savedSession = context.getSharedPreferences(

		Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getString(customKey, null)) == null ? false : true;
	}

	/**
	 * @param objKey
	 * @param dataObj
	 * @param context
	 * @return
	 */
	public static boolean saveObject(String objKey, Serializable dataObj,
			Context context) {

		Editor editor = context.getSharedPreferences(Constants.PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		try {
			editor.putString(objKey, ObjectSerializer.serialize(dataObj));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return editor.commit();
	}

	/**
	 * @param context
	 * @param objKey
	 */
	public static void deleteSettings(Context context, String objKey) {
		SharedPreferences info = context.getSharedPreferences(
				Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.remove(objKey);
		editor.commit();
	}

	/**
	 * @param objKey
	 * @param context
	 * @return
	 */
	public static Object getObject(String objKey, Context context) {

		SharedPreferences savedSession = context.getSharedPreferences(
				Constants.PREF_FILE_NAME, Context.MODE_PRIVATE);

		Object dataObj = null;
		try {
			dataObj = ObjectSerializer.deserialize(savedSession.getString(
					objKey, null));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataObj;
	}

	/**
	 * @param objKey
	 * @param dataObj
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean saveObjectTofile(String objKey, Serializable dataObj,
			Context context, String fileName) {

		Editor editor = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE).edit();
		try {
			editor.putString(objKey, ObjectSerializer.serialize(dataObj));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return editor.commit();
	}

	/**
	 * @param objKey
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Object getObjectFromfile(String objKey, Context context,
			String fileName) {

		SharedPreferences savedSession = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);

		Object dataObj = null;
		try {
			dataObj = ObjectSerializer.deserialize(savedSession.getString(
					objKey, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataObj;
	}
	
	/**
	  * Method to save user data for auto login
	  * 
	  * @param context
	  * @param friendList
	  */
	

}
