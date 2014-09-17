package ischool.dsa.utility;

import ischool.dsa.exception.DSAServerException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.w3c.dom.Element;

public class ConnectionUtil {

	public static Connection createConnection(Element connectionInfo,
			String CRYPTO_KEY) {

		if (connectionInfo == null)
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Element can not be null."));

		ConnectionParam cp = new ConnectionParam(connectionInfo, CRYPTO_KEY);
		return createConnection(cp);
	}

	public static Connection createConnection(ConnectionParam param) {
		return createConnection(param.getDriver(), param.getUrl(),
				param.getUser(), param.getPwd());
	}

	public static Connection createConnection(String driver, String url,
			String user, String pwd) {
		if (driver.isEmpty())
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Driver can not be empty."));

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Class Not Found:"
							+ driver), ex);
		}

		Properties pr = new Properties();
		pr.put("characterEncoding", "UTF-8");
		pr.put("useUnicode", "TRUE");
		pr.put("user", user);
		pr.put("password", pwd);

		// while (true) {
		try {
			Connection sqlConnection = DriverManager.getConnection(url, pr);
			return sqlConnection;
		} catch (SQLException ex) {
			// String msg = ex.getMessage();
			// if (msg == null)
			// msg = "";
			// if (!msg.contains("too many clients already"))
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Createtion occur a SQLException."),
					ex);
			// else {
			// try {
			// Thread.sleep(1000);
			// } catch (Exception e) {
			// }
			// }
			// }
		}
	}

	public static Connection tryCreateConnection(Element connectionInfo,
			String CRYPTO_KEY) {

		if (connectionInfo == null)
			return null;

		String driver = XmlUtil.getElementText(connectionInfo, "Driver").trim();
		String url = XmlUtil.getElementText(connectionInfo, "URL").trim();
		String user = XmlUtil.getElementText(connectionInfo, "UserName").trim();
		String pwd = XmlUtil.getElementText(connectionInfo, "Password").trim();

		String crypto = connectionInfo.getAttribute("Crypto");

		try {
			if (crypto.equalsIgnoreCase("Password")) {
				pwd = TripleDESHelper.decode(pwd, CRYPTO_KEY);
			} else if (crypto.equalsIgnoreCase("username_password")) {
				user = TripleDESHelper.decode(user, CRYPTO_KEY);
				pwd = TripleDESHelper.decode(pwd, CRYPTO_KEY);
			}
		} catch (Exception ex) {
			return null;
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			return null;
		}

		Properties pr = new Properties();
		pr.put("characterEncoding", "UTF-8");
		pr.put("useUnicode", "TRUE");
		pr.put("user", user);
		pr.put("password", pwd);

		// while (true) {
		try {
			Connection sqlConnection = DriverManager.getConnection(url, pr);
			return sqlConnection;
		} catch (SQLException ex) {
			ex.printStackTrace();
			// String msg = ex.getMessage();
			// if (msg == null)
			// msg = "";
			// if (!msg.contains("too many clients already"))
			return null;
			// else {
			// try {
			// Thread.sleep(1000);
			// } catch (Exception e) {
			// }
			// }
		}
		// }
	}

	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
		}
	}

	public static void close(Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		} catch (SQLException ex) {

		}
	}

	public static void close(PreparedStatement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		} catch (SQLException ex) {

		}
	}

	public static void commit(Connection connection) {
		try {
			connection.commit();
		} catch (SQLException ex) {
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Commit Failure."), ex);
		}
	}

	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException ex) {
			throw new DSAServerException(
					DSStatus.ConnectionError("Connection Rollback Failure."),
					ex);
		}
	}

	// public static void main(String[] args) {
	// Connection connection = createConnection("org.postgresql.Driver",
	// "jdbc:postgresql://localhost/dsa_server", "postgres",
	// "postgres");
	// try {
	// Statement st = connection.createStatement();
	// ResultSet rs = st.executeQuery("select * from abcde");
	// while (rs.next()) {
	//
	// }
	// rs.close();
	// st.close();
	// } catch (SQLException ex) {
	// // ex.printStackTrace();
	// }
	//
	// ConnectionUtil.closeConnection(connection);
	//
	// try {
	// Statement st = connection.createStatement();
	// ResultSet rs = st.executeQuery("select now()");
	// while (rs.next()) {
	// System.out.print(rs.getString(1));
	// }
	// rs.close();
	// st.close();
	// } catch (SQLException ex) {
	// ex.printStackTrace();
	// }
	//
	// ConnectionUtil.closeConnection(connection);
	// }
}
