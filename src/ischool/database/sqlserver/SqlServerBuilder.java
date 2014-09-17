package ischool.database.sqlserver;

import ischool.database.ICreateTableAgent;
import ischool.database.IDBBuilder;
import ischool.dsa.utility.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlServerBuilder implements IDBBuilder {

	public static final String JDBC_DRIVER_STRING = "net.sourceforge.jtds.jdbc.Driver";
	private static final String CONNECT_URL_PREFIX = "jdbc:jtds:sqlserver://";

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

		String indexType = "NONCLUSTERED";
		if (args != null) {
			if (String.valueOf(args).equalsIgnoreCase("CLUSTERED"))
				indexType = "CLUSTERED";
		}

		StringBuilder sb = new StringBuilder("CREATE ");
		sb.append(indexType).append(" INDEX [idx_");

		sb.append(tableName);
		for (String field : fields) {
			sb.append("_").append(field);
		}

		sb.append("] ON [").append(tableName).append("] (");

		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			sb.append("[").append(field).append("]");
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
		return new SqlServerCreateTableAgent();
	}

	@Override
	public boolean checkTableExists(Connection connection, String tableName)
			throws SQLException {
		String sql = "if exists(select * from dbo.sysobjects where id=object_id(N'["
				+ tableName
				+ "]') and OBJECTPROPERTY(id,N'IsUserTable')=1)select 1;else select 0;";

		Statement st = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(sql);

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
		return getQuotePrefix() + "'" + value + "'";
	}

	@Override
	public String getQuotePrefix() {
		return "N";
	}

}
