package com.travelcheck.util;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

/**
 * This class will be used to handle all the error types.
 * 
 * @author Sachit Wadhawan
 * 
 */
public class ErrorUtils {
	private static ErrorUtils _selfInstance = null;
	private StringBuffer messageString = null;
	private List<View> widgetList = null;

	/**
	 * private constructor as we are implementing singleton design pattern here.
	 */

	private ErrorUtils() {

	}

	// -------------------------------------------------------------------------
	/**
	 * Method to get the singleton instance of the class.
	 * 
	 * @return the instance of this class.
	 */

	public static ErrorUtils getInstance() {
		if (_selfInstance == null) {
			_selfInstance = new ErrorUtils();
		}
		return _selfInstance;
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to add message to the error string.
	 * 
	 * @param p_message_string
	 *            the message string to be appended.
	 */
	public void addMessage(String p_message_string) {
		if (p_message_string == null || p_message_string.trim().length() == 0) {
			return;
		}
		if (messageString == null) {
			messageString = new StringBuffer(p_message_string);
			return;
		}

		messageString = messageString.append("\n").append(p_message_string);
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to clear the message string.
	 */
	public void clearMessage() {
		messageString = null;
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to get message string.
	 * 
	 * @return the message string containing the error message.
	 */
	public String getMessageString() {
		if (messageString == null) {
			return "";
		}
		return messageString.toString();
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to get and clear the message string.
	 * 
	 * @return the message string containing the error message.
	 */
	public String getAndClearMessageString() {
		String l_message_error = getMessageString();
		clearMessage();
		return l_message_error;
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to add message to the error string.
	 * 
	 * @param p_message_string
	 *            the message string to be appended.
	 * @param p_widget
	 *            the widget in which the error is coming.
	 */
	public void addMessage(String p_message_string, View p_widget) {
		if (p_message_string == null || p_message_string.trim().length() == 0
				|| p_widget == null) {
			return;
		}

		if (widgetList == null) {
			widgetList = new ArrayList<View>();

		}
		widgetList.add(p_widget);
		addMessage(p_message_string);
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to get and clear the message string.
	 * 
	 * @return the message string containing the error message.
	 */
	public String getAndClear() {
		String l_message_error = getMessageString();
		clear();
		return l_message_error;
	}

	public void clear() {

		clearMessage();
		clearWidgetList();
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to clear widget list
	 */
	private void clearWidgetList() {
		widgetList.clear();
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to know if the error is present in the Message.
	 * 
	 * @return true if the message string has some errors, false otherwise.
	 */
	public boolean hasError() {
		// No need to check widget list.
		// if(widgetList != null && widgetList.isEmpty()) {
		// return false;
		// }
		return getMessageString().length() > 0;
	}

	// -------------------------------------------------------------------------
	/**
	 * Method to get first widget in the list
	 * 
	 * @return the View object in which the error was occurred first.
	 */
	public View getFirstWidget() {
		if (widgetList == null || widgetList.isEmpty()) {
			return null;
		}

		return widgetList.get(0);
	}

	// -------------------------------------------------------------------------

	public void addBlankMessage(String p_message_string, View p_widget) {
		if (p_widget == null) {
			return;
		}

	}
}
