package com.travelcheck.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONHelper {

	// -------------------------------------------------------------------------
	public static String convertObjectToJSONString(String p_id, Object p_obj) {
		return convertObjectToJSONString(p_obj);
	}

	// --------------------------------------------------------------------------
	public static String convertObjectToJSONString(Object p_obj) {
		String l_return_str = null;

		if (p_obj == null) {
			return "null";
		}

		if (p_obj instanceof JSONizable) {
			JSONObject l_json = new JSONObject((JSONizable) p_obj);
			l_return_str = l_json.toString();
			return l_return_str;
		}
		if (p_obj instanceof Collection) {
			return convertArrayToJSON(((Collection) p_obj).toArray())
					.toString();
		}
		if (p_obj instanceof Map) {
			return (new JSONObject((Map) p_obj)).toString();
		}
		if (p_obj.getClass().isArray()) {
			return convertArrayToJSON((Object[]) p_obj).toString();
		}

		return ("'" + p_obj.toString() + "'");

	}

	// --------------------------------------------------------------------------
	/**
	 * Recursive method to convert the Object[] to JSON Array.
	 * 
	 * @param p_array
	 *            :Object[] Object[] to be converted to JSON Array.
	 * @return JSONArray Converted JSON Array
	 */
	public static JSONArray convertArrayToJSON(Object[] p_array) {
		if (p_array == null) {
			return null;
		}
		JSONArray l_object = null;
		int l_size = p_array.length;

		l_object = new JSONArray();

		for (int l_i = 0; l_i < l_size; l_i++) {
			Object l_value = p_array[l_i];

			if (l_value == null) {
				l_object.put(org.json.JSONObject.NULL);
				continue;
			}
			if (l_value instanceof Map) {
				l_object.put((new JSONObject((Map) l_value)).toJSON());
			} else if (l_value instanceof Collection) {
				l_object.put(convertArrayToJSON(
						((Collection) l_value).toArray()).toString());
			} else if (l_value instanceof Object[]) {
				l_object.put(convertArrayToJSON((Object[]) l_value));
			} else if (l_value instanceof JSONizable) {
				JSONObject l_json_rep = new JSONObject((JSONizable) l_value);
				l_object.put(l_json_rep.toJSON());
			} else {
				l_object.put(l_value.toString());
			}
		}

		return l_object;
	}

	// --------------------------------------------------------------------------
	public static List<Object> convertJSONArrayToList(JSONArray p_array) throws JSONException {
		List<Object> l_list = null;

		l_list = new ArrayList<Object>();
		for (int l_i = 0; l_i < p_array.length(); l_i++) {
			Object p_value = p_array.get(l_i);
			if (p_value instanceof JSONArray) {
				l_list.add(convertJSONArrayToList((JSONArray) p_value));
			} else if (p_value instanceof org.json.JSONObject) {
				l_list.add(convertJSONToMap((org.json.JSONObject) p_value));
			} else {
				l_list.add(p_value);
			}
		}

		return l_list;
	}

	// --------------------------------------------------------------------------
	public static Map<String, Object> convertJSONStringToMap(
			String p_json_string) {
		try {
			org.json.JSONObject l_obj = new org.json.JSONObject(p_json_string);

			return convertJSONToMap(l_obj);
		} catch (Exception e) {

			return null;
		}
	}

	// --------------------------------------------------------------------------
	public static List<Object> convertJSONStringToList(String p_json_string) {
		try {
			JSONArray l_array = new JSONArray(p_json_string);

			return convertJSONArrayToList(l_array);
		} catch (Exception e) {

			return null;
		}
	}

	// --------------------------------------------------------------------------
	public static Map<String, Object> convertJSONToMap(
			org.json.JSONObject p_json_obj) throws JSONException {
		Iterator l_iter = null;
		Map<String, Object> l_values = new HashMap<String, Object>();

		l_iter = p_json_obj.keys();
		while (l_iter.hasNext()) {
			String l_key = (String) l_iter.next();
			Object l_value = p_json_obj.get(l_key);
			System.out.println(l_value.getClass());
			if (p_json_obj.isNull(l_key)) {
				l_values.put(l_key, null);
				continue;
			}
			if (l_value instanceof org.json.JSONObject) {
				l_values.put(l_key,
						convertJSONToMap((org.json.JSONObject) l_value));
				continue;
			}
			if (l_value instanceof JSONArray) {
				l_values.put(l_key, convertJSONArrayToList((JSONArray) l_value));
				continue;
			}

			l_values.put(l_key, l_value);

		}

		return l_values;
	}

}