package com.travelcheck.library.util;


/**
 * Class to define the constants used in the application.
 * 
 */
public class Constants {

	public static String PREF_FILE_NAME = "com.travelcheck.prefrences";
	public static String DIRECTORY_PATH = "/TravelCheckApp/Media/Capture/";

	public interface HTTP_METHODS {
		final String GET = "GET";
		final String POST = "POST";
	}

	public interface INTENT_CONSTANTS {

		String USER_DETAIL = "USER_DETAIL";
		int REQUEST_CAMERA	=	100;

	}

	public interface ACTIVITY_STATES {
		/** Activity type login */
		final byte SPLASH = 0;
		final byte MAIN_APP = 1;
		final byte SHARE_IMAGE = 2;
	}

	public interface DATABASE_VARIABLES {

		final String CONTACTS = "contact_number";

	}

}
