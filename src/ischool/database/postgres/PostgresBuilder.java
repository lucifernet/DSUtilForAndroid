package ischool.database.postgres;

import ischool.database.ICreateTableAgent;
import ischool.database.IDBBuilder;
import ischool.dsa.utility.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresBuilder implements IDBBuilder {

	public static final String JDBC_DRIVER_STRING = "org.postgresql.Driver";
	private static final String CONNECT_URL_PREFIX = "jdbc:postgresql://";

	@Override
	public Connection createConnection(String host, int port, String dbName,
			String username, String password) {
		String urlString = CONNECT_URL_PREFIX + host + ":" + port;

		return ConnectionUtil.createConnection(JDBC_DRIVER_STRING, urlString,
				username, password);
	}

	@Override
	public void createIndex(Connection connection, String tableName,
			Object args, String... fields) throws SQLException {
		String indexType = "hash";
		if (args != null) {
			if (String.valueOf(args).equalsIgnoreCase("btree"))
				indexType = "btree";
		}

		StringBuilder sb = new StringBuilder("CREATE INDEX idx_");

		sb.append(tableName);
		for (String field : fields) {
			sb.append("_").append(field);
		}

		sb.append(" ON \"").append(tableName).append("\"");

		sb.append(" USING ").append(indexType).append(" (");
		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			sb.append(field);
			if (i < fields.length - 1)
				sb.append(",");
		}
		sb.append(");");

		Statement st = connection.createStatement();
		st.executeUpdate(sb.toString());
		st.close();
	}

	@Override
	public String getDriverName() {
		return JDBC_DRIVER_STRING;
	}

	@Override
	public ICreateTableAgent newCreateTableAgent() {
		return new PgCreateTableAgent();
	}

	@Override
	public boolean checkTableExists(Connection connection, String tableName)
			throws SQLException {
		String sql = "select (count(*)>0) as exists from pg_tables where tablename=?";

		PreparedStatement st = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			st = connection.prepareStatement(sql);
			st.setString(1, tableName);
			rs = st.executeQuery();

			if (rs.next()) {
				exists = rs.getBoolean(1);
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			ConnectionUtil.close(st, rs);
		}
		return exists;
	}

	@Override
	public String processQuote(boolean isQuote, String value) {
		if (!isQuote)
			return value;
		return "'" + value + "'";
	}

	@Override
	public String getQuotePrefix() {
		return "";
	}

}
