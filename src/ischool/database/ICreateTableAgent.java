package ischool.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ICreateTableAgent {

	void setTableName(String tableName);

	void addAutoIncrementColumn(String columnName, boolean isBigInt);

	void addColumn(String columnName, int type, int limitLength,
			boolean allowNull, String defaultValue);

	void addBooleanColumn(String columnName, boolean allowNull,
			boolean defaultValue);

	void setUnique(String... columns);

	void setForeignKey(String targetTableName, ForeignKeyAction onUpdateAction,
			ForeignKeyAction onDeleteAction, ColumnCollection selfColumns,
			ColumnCollection targetColumns);

	void setPrimaryKey(String... column);

	void createToDB(Connection connection) throws SQLException;

	String generateSQL();
}
