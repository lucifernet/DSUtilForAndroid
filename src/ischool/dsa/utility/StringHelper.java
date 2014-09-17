package ischool.dsa.utility;

public class StringHelper {
	public static final String EMPTY = "";

	public static boolean isNullOrEmpty(String s) {
		if (s == null)
			return true;
		if (s.length() == 0)
			return true;
		return false;
	}

	public static boolean isNullOrWhiteSpace(String s) {
		if (isNullOrEmpty(s))
			return true;
		if (s.trim().length() == 0)
			return true;
		return false;
	}

	public static String getString(String s, String defaultString) {
		if (isNullOrWhiteSpace(s))
			return defaultString;
		return s;
	}

	public static String getString(String s) {
		return getString(s, EMPTY);
	}
}
