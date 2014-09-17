package ischool.dsa.utility;

import org.w3c.dom.Element;


public class SqlHelper {
	public static String getString(String value) {
		value = value.replace("'", "''");
		value = value.replace("\\", "\\\\");
		return value;
	}
	
	public static String getString(Element element) {
		String value = XmlHelper.convertToString(element);
		value = value.replace("'", "''");
		value = value.replace("\\", "\\\\");
		return value;
	}
	
	public static String trim(String sql){
		if(sql == null)
			return "";
		if(sql.isEmpty())
			return sql;
		sql = sql.trim();
		while(sql.startsWith("　")){
			sql = sql.substring(1);
		}
		while(sql.endsWith("　")){
			sql = sql.substring(0,sql.lastIndexOf("　"));
		}
		return sql;
	}
}

