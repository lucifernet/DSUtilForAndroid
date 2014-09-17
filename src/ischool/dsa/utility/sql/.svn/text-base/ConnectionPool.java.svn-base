package ischool.dsa.utility.sql;

import ischool.dsa.exception.DSAServerException;
import ischool.dsa.utility.ConnectionParam;
import ischool.dsa.utility.ConnectionUtil;
import ischool.dsa.utility.Converter;
import ischool.dsa.utility.DSStatus;
import ischool.dsa.utility.XmlUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.w3c.dom.Element;

public class ConnectionPool {
	private ArrayList<ConnectionAgent> _pools;
	private Queue<Connection> _availablePool;
	private Semaphore _sync;
	private ConnectionParam _param;
	private int _maxCount;
	private int _currentNameNumber = 0;

	public ConnectionPool(String driver, String url, String username,
			String password, String crypto, String CRYPTO_KEY, int maxCount) {
		ConnectionParam param = new ConnectionParam(driver, url, username,
				password, crypto, CRYPTO_KEY);
		init(param, maxCount);
	}

	public ConnectionPool(ConnectionParam param, int maxCount) {
		init(param, maxCount);
	}

	private void init(ConnectionParam param, int maxCount) {
		_maxCount = maxCount;
		if (_maxCount < 1)
			_maxCount = 1;

		_param = param;
		_pools = new ArrayList<ConnectionAgent>();
		_availablePool = new LinkedBlockingQueue<Connection>();
		_sync = new Semaphore(_maxCount);
	}

	public Connection getConnection(Object getter, int transactionLevel) {
		Connection connection = getConnection(getter);
		try {
			if(connection.isClosed()){
				this.reportBadConnection(connection);
				return getConnection(getter, transactionLevel);
			}
			connection.setTransactionIsolation(transactionLevel);
		} catch (SQLException ex) {
			this.reportBadConnection(connection);
			throw new DSAServerException(
					DSStatus.UnhandledException("setTransactionIsolation occured an exception."),
					ex);
		}
		return connection;
	}

	public Connection getConnection(Object getter) {

		// System.out.print("acquire" + _sync.availablePermits() + "\n");
		try {
			_sync.acquire();
		} catch (InterruptedException e) {

		}

		synchronized (_availablePool) {
			if (_availablePool.isEmpty()) {
				if (_pools.size() < _maxCount) {
					Connection connection = ConnectionUtil
							.createConnection(_param);
					// try {
					// connection
					// .setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					// } catch (SQLException ex) {
					// }
					_currentNameNumber++;
					if (_currentNameNumber > 1000)
						_currentNameNumber = 0;

					ConnectionAgent agent = new ConnectionAgent(connection,
							String.valueOf(_currentNameNumber));
					_pools.add(agent);
					_availablePool.offer(connection);
				}
			}

			Connection connection = _availablePool.poll();
			ConnectionAgent a = find(connection);

			if (a != null)
				a.get(getter);

			return connection;
		}
	}

	public boolean returnConnection(Connection connection) {
		synchronized (_availablePool) {
			ConnectionAgent a = find(connection);
			if (a == null)
				return false;
			try {
				_availablePool.add(connection);
				a.release();
				return true;
			} finally {
				_sync.release();
			}
		}
	}

	public void reportBadConnection(Connection connection) {
		try {
			synchronized (_pools) {
				ConnectionAgent a = find(connection);
				if (a != null) {
					a.closeConnection();
					_pools.remove(a);
				}
			}
		} finally {
			_sync.release();
		}
	}

	public synchronized void release() {
		for (ConnectionAgent connection : _pools) {
			connection.closeConnection();
		}
		_pools.clear();
		_availablePool.clear();
		_sync = new Semaphore(_maxCount);
	}

	public synchronized Element report() {
		Element e = XmlUtil.createElement("Pool");
		e.setAttribute("Count", String.valueOf(_pools.size()));
		e.setAttribute("MaxCount", String.valueOf(_maxCount));
		String cstring = Converter.toDateString(Calendar.getInstance()
				.getTime());
		e.setAttribute("Now", cstring);

		for (ConnectionAgent agent : _pools) {
			XmlUtil.appendElement(e, agent.report());
		}

		return e;
	}

	private ConnectionAgent find(Connection connection) {

		for (ConnectionAgent agent : _pools) {
			if (agent.getConnection() == connection) {
				return agent;
			}
		}
		return null;
	}
}

class ConnectionAgent {
	private Connection _connection;
	private Object _getter;
	private Calendar _getTime;
	private boolean _used;
	private String _name;

	public ConnectionAgent(Connection connection, String name) {
		_connection = connection;
		_used = false;
		_name = name;
	}

	public void get(Object getter) {
		_used = true;
		_getter = getter;
		_getTime = Calendar.getInstance();
	}

	public void release() {
		_used = false;
		_getTime = null;
		_getter = null;
	}

	public void closeConnection() {
		release();
		ConnectionUtil.closeConnection(_connection);
	}

	public boolean isUsed() {
		return _used;
	}

	public String getGetter() {
		if (_used)
			return _getter.toString();
		return "";
	}

	public Calendar getGetTime() {
		if (_used)
			return _getTime;
		return Calendar.getInstance();
	}

	public Connection getConnection() {
		return _connection;
	}

	public String getName() {
		return _name;
	}

	public Element report() {
		Element e = XmlUtil.createElement("Connection");
		e.setAttribute("InUsed", String.valueOf(this.isUsed()));
		e.setAttribute("Getter", this.getGetter());

		Calendar c = getGetTime();
		Calendar now = Calendar.getInstance();

		String cstring = Converter.toDateString(c.getTime());
		e.setAttribute("GetTime", cstring);

		long during = now.getTimeInMillis() - c.getTimeInMillis();
		e.setAttribute("During", String.valueOf(during));
		e.setAttribute("Name", this.getName());
		return e;
	}
}
