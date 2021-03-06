package com.travelcheck.library.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.travelcheck.network.ParameterMap;

/**
 * @author Sachit Wadhawan
 * 
 */
public abstract class ApiCall {
	/**
	 * @return
	 */
	public abstract String getPath();

	/**
	 * @param params
	 */
	public void addParameters(ParameterMap params) {

	}

	/**
	 * @return
	 */
	public byte[] getRequestObject() {
		return null;
	}
	
	/**
	 * @return
	 */
	public Object getObjectValue() {
		return null;
	}

	/**
	 * @param response
	 * @throws APIException
	 * @throws JSONException
	 */
	public void populateFromResponse(String response) throws APIException,
			JSONException {
		/*
		 * sessionId = response.getString("session_id"); if
		 * (sessionId.equals("")) sessionId = "-";
		 */
	}

	/*
	 * public String getSessionId() { return sessionId; }
	 * 
	 * public void setSessionId(String sessionId) { this.sessionId = sessionId;
	 * }
	 */

	/**
	 * @param response
	 * @return
	 * @throws APIException
	 * @throws JSONException
	 */
	public abstract APIException parseErrorMessage(JSONObject response)
			throws APIException, JSONException;

	/**
	 * Method to return the result got from the api call
	 * 
	 * @return result, if any problem occur then return null
	 */
	public Object getResult() {
		return null;
	}

}
