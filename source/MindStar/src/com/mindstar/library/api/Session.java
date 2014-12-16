package com.mindstar.library.api;

import java.io.IOException;

import com.mindstar.library.network.HttpBaseConnectionProvider;
import com.mindstar.library.network.NetworkConstant;
import com.mindstar.library.util.Constants;
import com.mindstar.library.util.ErrorExplanations;
import com.mindstar.network.ParameterMap;

/**
 * @author Pavan
 * 
 */
public class Session {
	// public String sessionId = "-";
	/**
	 * 
	 */
	boolean isDebug = true;
	private String responseStatus;
	private String responeMessage = null;;

	/**
	 * @param call
	 * @param cp
	 * @throws APIException
	 */
	public void invoke(ApiCall call, HttpBaseConnectionProvider cp)
			throws APIException {
		/*
		 * HttpBaseConnectionProvider cp = HttpBaseConnectionProvider
		 * .getInstance();
		 */
		try {
			executeCall(call, cp);
		} catch (APIException e) {
			throw e;
		}
	}

	/**
	 * @param call
	 * @param cp
	 * @throws APIException
	 */
	private void executeCall(ApiCall call, HttpBaseConnectionProvider cp)
			throws APIException {
		ParameterMap params = new ParameterMap();
		call.addParameters(params);
		try {
			String url = NetworkConstant.BASE_URL + "/" + call.getPath();

			String res = "";

			// execute GET request
			if (cp.getHttpMethod().equalsIgnoreCase(Constants.HTTP_METHODS.GET)) {
				// trustAllHosts();
				res = cp.doGet(url, params);
			}

			// execute POST request
			else if (cp.getHttpMethod().equalsIgnoreCase(
					Constants.HTTP_METHODS.POST))
				// trustAllHosts();
				res = cp.doPost(url, params, NetworkConstant.CONTENT_TYPE,
						call.getRequestObject(), call.getObjectValue());
			// String response;
			// try {
			// response =res; /*new JSONObject(new JSONTokener(res))*/;
			// } catch (JSONException e) {
			// throw new APIException("ERROR", ErrorExplanations.get("ERROR"));
			// }
			// if (response.has("error")) {
			// String code = response.optString("error");
			// throw new APIException(/*Utility.decodeString(*/code/*)*/,
			// ErrorExplanations.get(/*Utility.decodeString(*/code)/*)*/);
			// }
			try {
				call.populateFromResponse(res);
			} catch (APIException e) {
				throw new APIException("ERROR", ErrorExplanations.get("ERROR"));
				// throw new IllegalArgumentException(e.getMessage());

			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		} catch (IOException ioe) {
			throw new APIException("ERROR", ErrorExplanations.get("ERROR"));
		}

		/*
		 * } catch (APIException api) { throw api; }
		 */
		finally {
			cp.closeConnection();
		}
	}

	/**
	 * @param call
	 * @param cp
	 * @throws APIException
	 */
	@SuppressWarnings("unused")
	private void refreshSession(ApiCall call, HttpBaseConnectionProvider cp)
			throws APIException {
		/*
		 * sessionId = "-"; SessionStart sessionStart = new SessionStart(); try
		 * { executeCall(sessionStart, cp); } catch (APIException e) { throw e;
		 * } executeCall(call, cp);
		 */
	}
}
