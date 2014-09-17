package ischool.dsa.utility;

import java.sql.SQLException;

public class ExceptionUtil {
	public static String getErrorMessage(Throwable ex) {
		StringBuilder sb = new StringBuilder();
		sb.append(ex.getMessage()).append("\n");

		for (StackTraceElement st : ex.getStackTrace()) {
			sb.append(st.toString()).append("\n");
		}

		sb.append("\n");

		if (ex instanceof SQLException) {
			SQLException sqlEx = (SQLException) ex;
			if (sqlEx.getNextException() != null) {
				String inner = getErrorMessage(sqlEx.getNextException());
				sb.append(inner);
			}
		}

		if (ex.getCause() != null) {
			String inner = getErrorMessage(ex.getCause());
			sb.append(inner);
		}

		return sb.toString();
	}
}
