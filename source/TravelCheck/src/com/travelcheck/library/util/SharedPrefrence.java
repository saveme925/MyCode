package com.travelcheck.library.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class is to save and retrive the data to the preference store
 * 
 * @author Manmohan.Soni
 * 
 */
public class SharedPrefrence {
	private static final String USER_INFO = "userinfo";
	private static final String SETTING_INFO = "settinginfo";

	private interface PREF_KEYS {
		final String PROFILE_ID = "PROFILE_ID";
		final String USER_ID = "USER_ID";
		final String PROJECT_ID = "PROJECT_ID";
		final String LAST_VISIBLE_POSITION = "LAST_VISIBLE_POSITION";
		final String DEVICE_ID = "DEVICE_ID";
		final String ASSIGNEE_EMAIL = "ASSIGNEE_EMAIL";
		final String REGISTRATION_ID = "REGISTRATION_ID";
		final String FOLDER_NAME = "FOLDER_NAME";
		final String PROFILE_IMAGE = "PROFILE_IMAGE";
		final String PREFERENCE_SETTINGS = "PREFERENCE_SETTINGS";
		final String COMMENT_NUMBER = "COMMENT_NUMBER";
		final String FIRST_TIME_APP = "FIRST_TIME_APP";
		final String ACCESS_TOKEN = "ACCESS_TOKEN";
		final String EMAIL_ID = "EMAIL_ID";
		final String EXPAND_FLAG = "EXPAND_COLLAPSE";
		final String SINGLE_EXPAND_COLLAPSE	=	"SINGLE_EXPAND_COLLAPSE";

	}

	public static void saveFirstTimeAppUser(Context context,
			boolean p_preference) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putBoolean(PREF_KEYS.FIRST_TIME_APP, p_preference);
		editor.commit();
	}

	public static boolean getFirstTimeAppUser(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getBoolean(PREF_KEYS.FIRST_TIME_APP, true);

	}

	public static void saveLastVisiblePosition(Context p_context, int p_position) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putInt(PREF_KEYS.LAST_VISIBLE_POSITION, p_position);
		editor.commit();

	}

	public static int getLastVisiblePosition(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getInt(PREF_KEYS.LAST_VISIBLE_POSITION, 1);

	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @param profileName
	 *            name of login user
	 */

	public static void saveUsername(Context context, String profileName) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.PROFILE_ID, profileName);
		editor.commit();
	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @return name of login user.
	 */

	public static String getUserName(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(PREF_KEYS.PROFILE_ID, "");

	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @param profileName
	 *            email id of login user
	 */

	public static void saveUseremailid(Context context, String p_emailid) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.EMAIL_ID, p_emailid);
		editor.commit();
	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @return email id of login user.
	 */

	public static String getUseremailid(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(PREF_KEYS.EMAIL_ID, "");

	}

	public static void saveAccessToken(Context context, String pAccessToken) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.ACCESS_TOKEN, pAccessToken);
		editor.commit();
	}

	public static String getAccessToken(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(PREF_KEYS.ACCESS_TOKEN, "");

	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @param profileName
	 *            name of login user
	 */

	public static void savePreferenceSetting(Context context,
			boolean p_preference) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putBoolean(PREF_KEYS.PREFERENCE_SETTINGS, p_preference);
		editor.commit();
	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @return name of login user.
	 */

	public static boolean getPreferenceSetting(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getBoolean(PREF_KEYS.PREFERENCE_SETTINGS, true);

	}

	/**
	 * @param context
	 *            context of an activity
	 * @param pProfileImage
	 *            user profile image
	 */

	public static void saveUserProfilePic(Context context, String pProfileImage) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.PROFILE_IMAGE, pProfileImage);
		editor.commit();
	}

	/**
	 * 
	 * @param context
	 *            context of an activity
	 * @return user profile image path
	 */

	public static String getUserProfilePic(Context context) {
		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(PREF_KEYS.PROFILE_IMAGE, "");

	}

	public static void saveUserData(Context context, String string) {

		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(USER_INFO, string);
		editor.commit();

	}

	/**
	 * @param p_context
	 *            Context of an activity.
	 * @return folder name for attachments
	 */
	public static String getFolderName(Context p_context) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(PREF_KEYS.FOLDER_NAME, "");

	}

	/**
	 * @param p_context
	 *            Context of an activity
	 * @param p_folderName
	 *            save folder name for keeping attachments
	 */

	public static void saveFolderName(Context p_context, String p_folderName) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.FOLDER_NAME, p_folderName);
		editor.commit();

	}

	public static String getSaveUserData(Context context) {

		SharedPreferences info = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getString(USER_INFO, "");

	}

	/**
	 * 
	 * @param mcontext
	 * @param userId
	 */
	public static void saveUserId(Context mcontext, Long userId) {
		SharedPreferences info = mcontext.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		// editor.putString(PREF_KEYS.USER_ID, userId);
		editor.putLong(PREF_KEYS.USER_ID, userId);
		editor.commit();

	}

	/**
	 * 
	 * @param ctx
	 * @return
	 */
	public static Long getUserID(Context ctx) {
		SharedPreferences info = ctx.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		// return info.getString(PREF_KEYS.USER_ID, "");
		return info.getLong(PREF_KEYS.USER_ID, 0);
	}

	/**
	 * 
	 * @param mcontext
	 * @param userId
	 */
	public static void saveProjectId(Context p_context, int p_projectId) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putInt(PREF_KEYS.PROJECT_ID, p_projectId);
		editor.commit();

	}

	/**
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getProjectId(Context p_context) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		return info.getInt(PREF_KEYS.PROJECT_ID, 0);
	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @return project id.
	 */

	public static String getDeviceId(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getString(PREF_KEYS.DEVICE_ID, "");

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @param p_deviceId
	 *            unique id of a device.
	 */
	public static void saveDeviceId(Context p_context, String p_deviceId) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		// editor.putString(PREF_KEYS.USER_ID, userId);
		editor.putString(PREF_KEYS.DEVICE_ID, p_deviceId);
		editor.commit();

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @param p_registrationId
	 *            GCM registered id of a device.
	 */
	public static void saveGCMRegistrationId(Context p_context,
			String p_registrationId) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		// editor.putString(PREF_KEYS.USER_ID, userId);
		editor.putString(PREF_KEYS.REGISTRATION_ID, p_registrationId);
		editor.commit();

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @return GCM Registration id.
	 */

	public static String getGCMRegistrationId(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getString(PREF_KEYS.REGISTRATION_ID, "");

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @return assignee email id.
	 */

	public static String getAssigneeEmail(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getString(PREF_KEYS.ASSIGNEE_EMAIL, "");

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @param p_email
	 *            email id of assignee.
	 */
	public static void saveAssigneeEmail(Context p_context, String p_email) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putString(PREF_KEYS.ASSIGNEE_EMAIL, p_email);
		editor.commit();

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @return assignee email id.
	 */

	public static int getCommentsNumber(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getInt(PREF_KEYS.COMMENT_NUMBER, 0);

	}

	/**
	 * @param p_context
	 *            Context of an application.
	 * @param p_email
	 *            email id of assignee.
	 */
	public static void saveCommentNumber(Context p_context, int p_number) {
		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putInt(PREF_KEYS.COMMENT_NUMBER, p_number);
		editor.commit();

	}

	/**
	 * Method that will save expand/collapse state
	 * 
	 * @param p_context
	 * @param p_flag
	 */

	public static void saveFlagForExpandTask(Context p_context, int p_flag) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putInt(PREF_KEYS.EXPAND_FLAG, p_flag);
		editor.commit();

	}

	
	/**
	 * Method that will give the last state of expand/collapse
	 * 
	 * @param p_context
	 * @return
	 */

	public static int getFlagForExpandTask(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getInt(PREF_KEYS.EXPAND_FLAG, 0);

	}
	
	public static void saveFlagForSingleExpandCollapseTask(Context p_context,
			boolean p_flag) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = info.edit();
		editor.putBoolean(PREF_KEYS.SINGLE_EXPAND_COLLAPSE, p_flag);
		editor.commit();

	}
	
	public static boolean getFlagForSingleExpandCollapseTask(Context p_context) {

		SharedPreferences info = p_context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);

		return info.getBoolean(PREF_KEYS.SINGLE_EXPAND_COLLAPSE, true);

	}


}
