package com.travelcheck.library.util;

import android.widget.TextView;

import com.travelcheck.R;

/**
 * Class to define the constants used in the application.
 * 
 */
public class Constants {

	public static String PREF_FILE_NAME = "com.mindstar.prefrences";

	public interface HTTP_METHODS {
		final String GET = "GET";
		final String POST = "POST";
	}

	public interface INTENT_CONSTANTS {

		String USER_DETAIL = "USER_DETAIL";

	}
	
	public interface ACTIVITY_STATES {
		/** Activity type login */
		final byte SPLASH = 0;
		final byte MAIN_APP = 1;
		}

}
