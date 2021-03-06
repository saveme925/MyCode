package com.travelcheck.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Parameter map knows offers convenience methods for chaining add()s as well as
 * URL encoding.
 * 
 * @author David M. Chandler
 */
public class ParameterMap implements Map<String, String> {

	private Map<String, String> map = new HashMap<String, String>();

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return map.entrySet();
	}

	public String get(Object key) {
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public String put(String key, String value) {
		return map.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends String> arg) {
		map.putAll(arg);
	}

	public String remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<String> values() {
		return map.values();
	}

	/**
	 * Convenience method returns this class so multiple calls can be chained.
	 * 
	 * @param name
	 * @param value
	 * @return This map
	 */
	public ParameterMap add(String name, String value) {
		map.put(name, value);
		return this;
	}

	/**
	 * Returns URL encoded data
	 * 
	 * @return URL encoded String
	 */
	public String urlEncode() {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key);
			String value = map.get(key);
			if (value != null) {
				sb.append("=");
				try {
					sb.append(URLEncoder.encode(value, RequestHandler.UTF8));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Return a URL encoded byte array in UTF-8 charset.
	 * 
	 * @return URL encoded bytes
	 */
	public byte[] urlEncodedBytes() {
		byte[] bytes = null;
		try {
			bytes = this.urlEncode().getBytes(RequestHandler.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

}