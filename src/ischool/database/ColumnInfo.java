package ischool.database;

public class ColumnInfo {

	public static final int UNLIMITED_COLUMN_LENGTH = -1;
	public static final String NO_DEFAULT_VALUE = null;

	private String _columnName;
	private int _types;
	private int _limitLength;
	private boolean _autoIncrement;
	private boolean _allowNull;
	private String _defaultValue;

	public ColumnInfo(String columnName, int type, int limitLength,
			boolean autoIncrement, boolean allowNull, String defaultValue) {
		init(columnName, type, limitLength, autoIncrement, allowNull,
				defaultValue);
	}

	public String getColumnName() {
		return _columnName;
	}

	public int getType() {
		return _types;
	}

	public int getLimitLength() {
		return _limitLength;
	}

	public boolean isAutoIncrement() {
		return _autoIncrement;
	}

	public boolean isAllowNull() {
		return _allowNull;
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	private void init(String columnName, int type, int limitLength,
			boolean autoIncrement, boolean allowNull, String defaultValue) {
		_columnName = columnName;
		_types = type;
		_limitLength = limitLength;
		_autoIncrement = autoIncrement;
		_allowNull = allowNull;
		_defaultValue = defaultValue;
	}
}