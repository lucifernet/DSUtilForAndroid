package ischool.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBBuilder {

	/**
	 * 取得所使用的 jdbc driver 名稱
	 * **/
	String getDriverName();

	/**
	 * 建立連線
	 * 
	 * @param host
	 *            : 資料庫所在位置, example : 10.1.1.163
	 * @param port
	 *            : 資料庫連接埠號
	 * @param dbName
	 *            : 資料庫名稱
	 * @param username
	 *            : 連線帳號
	 * @param password
	 *            : 連線密碼
	 * **/
	Connection createConnection(String host, int port, String dbName,
			String username, String password);

	/**
	 * 建立索引
	 * 
	 * @param connection
	 *            : 資料庫連線
	 * @param tableName
	 *            : 資料表
	 * **/
	void createIndex(Connection connection, String tableName, Object args,
			String... fields) throws SQLException;

	/**
	 * 取得建立資料表的代理物件
	 * **/
	ICreateTableAgent newCreateTableAgent();

	/**
	 * 查詢資料表是否存在
	 * **/
	boolean checkTableExists(Connection connection, String tableName)
			throws SQLException;

	/**
	 * 處理單引號, 因 sql server 並非原生 UTF-8 欄位, 所有雙引號欄位, 必須在單引號前置字元前加N 而 postgres
	 * 若該欄位型態為非字元時, 前置加 N 會出現問題, 故在此列為區分
	 * **/
	String processQuote(boolean isQuote, String value);

	String getQuotePrefix();
}
