package ischool.database;

import java.util.ArrayList;

public class ColumnCollection extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ColumnCollection create(String... columns) {
		ColumnCollection cc = new ColumnCollection();

		for (String column : columns)
			cc.add(column);

		return cc;
	}

	public String getCombineString() {
		return getCombineString(',', "", "");
	}

	public String getCombineString(char sep) {
		return getCombineString(sep,"","");
	}
	
	public String getCombineString(char sep, String leftQuote, String rightQuote) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.size(); i++) {
			String s = this.get(i);
			sb.append(leftQuote).append(s).append(rightQuote);

			if (i < this.size() - 1)
				sb.append(sep);
		}
		return sb.toString();
	}

	public String getCombineStringWithQuote() {
		return getCombineString(',', "\"", "\"");
	}
}
