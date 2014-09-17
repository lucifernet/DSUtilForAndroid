package ischool.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public abstract class AbstractCreateTableAgent implements ICreateTableAgent {
	protected String _tableName;
	protected ArrayList<ColumnInfo> _columns;
	protected ArrayList<ColumnCollection> _uniques;
	protected ArrayList<ForeignKeyInfo> _foreignKeys;
	protected ColumnCollection _pk;

	protected AbstractCreateTableAgent() {
		_columns = new ArrayList<ColumnInfo>();
		_uniques = new ArrayList<ColumnCollection>();
		_foreignKeys = new ArrayList<ForeignKeyInfo>();
	}

	@Override
	public void setTableName(String tableName) {
		_tableName = tableName;
	}

	@Override
	public void addAutoIncrementColumn(String columnName, boolean isBigInt) {
		int t = Types.INTEGER;
		if (isBigInt)
			t = Types.BIGINT;

		ColumnInfo column = new ColumnInfo(columnName, t,
				ColumnInfo.UNLIMITED_COLUMN_LENGTH, true, false,
				ColumnInfo.NO_DEFAULT_VALUE);
		_columns.add(column);
	}

	@Override
	public void addColumn(String columnName, int type, int limitLength,
			boolean allowNull, String defaultValue) {
		ColumnInfo column = new ColumnInfo(columnName, type, limitLength,
				false, allowNull, defaultValue);
		_columns.add(column);
	}

	@Override
	public void addBooleanColumn(String columnName, boolean allowNull,
			boolean defaultValue) {
		this.addColumn(columnName, Types.BOOLEAN,
				ColumnInfo.UNLIMITED_COLUMN_LENGTH, allowNull,
				String.valueOf(defaultValue));
	}

	@Override
	public void setUnique(String... columns) {
		ColumnCollection uniq = ColumnCollection.create(columns);
		_uniques.add(uniq);
	}

	@Override
	public void setForeignKey(String targetTableName,
			ForeignKeyAction onUpdateAction, ForeignKeyAction onDeleteAction,
			ColumnCollection selfColumns, ColumnCollection targetColumns) {
		ForeignKeyInfo fk = new ForeignKeyInfo(targetTableName, selfColumns,
				targetColumns, onUpdateAction, onDeleteAction);
		_foreignKeys.add(fk);
	}

	@Override
	public void setPrimaryKey(String... column) {
		_pk = ColumnCollection.create(column);
	}

	@Override
	public void createToDB(Connection connection) throws SQLException {
		String sql = generateSQL();
		Statement st = null;
		try {
			st = connection.createStatement();
			st.executeUpdate(sql);
		} finally {
			st.close();
		}
	}
}
