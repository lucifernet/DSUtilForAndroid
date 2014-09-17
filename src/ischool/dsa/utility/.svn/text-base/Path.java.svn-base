package ischool.dsa.utility;

public class Path {
	public static String combine(String path1, String... path2) {
		path1 = path1.replace("\\", "/");

		if (path1.endsWith("/")) {
			path1 = path1.substring(0, path1.length() - 1);
		}

		StringBuilder sb = new StringBuilder(path1);
		for (String path : path2) {
			path = path.replace("\\", "/");
			if (!path.startsWith("/")) {
				sb.append('/');
			}
			sb.append(path);
		}
		return sb.toString();
	}
}
