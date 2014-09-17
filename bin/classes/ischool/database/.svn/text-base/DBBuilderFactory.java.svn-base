package ischool.database;

import ischool.database.postgres.PostgresBuilder;
import ischool.database.sqlserver.SqlServerBuilder;

public class DBBuilderFactory {
	public static IDBBuilder newBuilder(String driver) {
		if (driver == null)
			return new PostgresBuilder();

		if (driver.equalsIgnoreCase(SqlServerBuilder.JDBC_DRIVER_STRING))
			return new SqlServerBuilder();

		if (driver.equalsIgnoreCase(PostgresBuilder.JDBC_DRIVER_STRING))
			return new PostgresBuilder();

		return new PostgresBuilder();
	}
}
