package ischool.utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
	public static JSONObject parseJSON(String jsonString) {
		try {
			return new JSONObject(jsonString);
		} catch (JSONException e) {
			return null;
		}
	}

	public static String getString(JSONObject json, String name) {
		try {
			return json.getString(name);
		} catch (JSONException e) {
			return StringUtil.EMPTY;
		}
	}
	
	public static int getInt(JSONObject json, String name) {
		try {
			return json.getInt(name);
		} catch (JSONException e) {
			return 0;
		}
	}
}
