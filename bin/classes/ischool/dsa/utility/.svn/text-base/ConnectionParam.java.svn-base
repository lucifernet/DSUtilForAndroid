package ischool.dsa.utility;

import ischool.dsa.exception.DSAServerException;

import org.w3c.dom.Element;

public class ConnectionParam {
	private String user;
	private String pwd;
	private String driver;
	private String url;
	private String crypto;
	private String CRYPTO_KEY;

	public ConnectionParam(Element connectionElement, String CRYPTO_KEY) {
		this.driver = XmlUtil.getElementText(connectionElement, "Driver")
				.trim();
		this.url = XmlUtil.getElementText(connectionElement, "URL").trim();
		this.user = XmlUtil.getElementText(connectionElement, "UserName")
				.trim();
		this.pwd = XmlUtil.getElementText(connectionElement, "Password").trim();
		this.CRYPTO_KEY = CRYPTO_KEY;
		this.crypto = connectionElement.getAttribute("Crypto");
	}

	public ConnectionParam(String driver, String url, String user, String pwd,
			String crypto, String CRYPTO_KEY) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.crypto = crypto;
		this.CRYPTO_KEY = CRYPTO_KEY;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		try {
			if (crypto.equalsIgnoreCase("username_password")) {
				return TripleDESHelper.decode(user, CRYPTO_KEY);
			}
		} catch (Exception ex) {
			throw new DSAServerException(DSStatus
					.ConnectionError("Connection Data Decode Failure."), ex);
		}
		return user;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		try {
			if (crypto.equalsIgnoreCase("Password")) {
				return TripleDESHelper.decode(pwd, CRYPTO_KEY);
			} else if (crypto.equalsIgnoreCase("username_password")) {
				return TripleDESHelper.decode(pwd, CRYPTO_KEY);
			}
		} catch (Exception ex) {
			throw new DSAServerException(DSStatus
					.ConnectionError("Connection Data Decode Failure."), ex);
		}
		return pwd;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriver() {
		return driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setCrypto(String crypto) {
		this.crypto = crypto;
	}

	public String getCrypto() {
		return crypto;
	}

	public ConnectionParam clone() {
		return new ConnectionParam(driver, url, user, pwd, crypto, CRYPTO_KEY);
	}
}
