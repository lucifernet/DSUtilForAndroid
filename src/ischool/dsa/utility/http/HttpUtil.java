package ischool.dsa.utility.http;

import ischool.dsa.client.cert.TrustAllCertManager;
import ischool.dsa.utility.Converter;
import ischool.dsa.utility.XmlHelper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.w3c.dom.Element;

public class HttpUtil {

	public static Element getElement(String urlString) {
		return XmlHelper.parseXml(getInputStream(urlString));
	}

	public static String getString(String urlString, Cancelable cancelable) {
		try {
			InputStream inputStream = getInputStream(urlString);
			return convertToString(inputStream, cancelable);
		} catch (Exception e) {
			throw new RuntimeException(
					"Getting String from urlString occured a Exception.", e);
		}
	}

	public static InputStream getInputStream(String urlString) {
		try {
			URL url = new URL(urlString);

			if (url.getProtocol().equalsIgnoreCase("https")) {
				SSLContext sc = SSLContext.getInstance("SSL");

				TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllCertManager() };
				sc.init(null, trustAllCerts, new java.security.SecureRandom());

				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
			}
			URLConnection urlConnection = url.openConnection();
			return urlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Getting InputStream from URL occured a Exception.", e);
		}
	}

	public static InputStream getInputStream(String urlString, String username,
			String password) throws IOException, NoSuchAlgorithmException,
			KeyManagementException {
		if (username == null)
			username = "";

		if (password == null)
			password = "";

		URL url = new URL(urlString);

		if (urlString.toLowerCase(Locale.getDefault()).startsWith("https://")) {
			SSLContext sc = SSLContext.getInstance("SSL");

			TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllCertManager() };
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}

		URLConnection uc = url.openConnection();
		if (!username.isEmpty()) {
			String s = username + ":" + password;
			byte[] b = s.getBytes("ASCII");
			String str = Converter.toBase64String(b);

			uc.addRequestProperty("Authorization", "Basic " + str);
		}

		uc.setConnectTimeout(100000);
		uc.setDoOutput(true);

		InputStream in = new BufferedInputStream(uc.getInputStream());
		return in;
	}

	public static InputStream postData(URLConnection urlConnection,
			String data, int timeoutMillis) {
		try {
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			httpConnection.setConnectTimeout(timeoutMillis);
			OutputStreamWriter outStream = new OutputStreamWriter(
					httpConnection.getOutputStream(), "UTF-8");

			outStream.write(data);
			outStream.flush();
			outStream.close();

			return httpConnection.getInputStream();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static InputStream postData(String urlString, String data,
			int timeoutMillis) {
		try {
			URL url = new URL(urlString);

			if (urlString.toLowerCase(Locale.getDefault()).startsWith(
					"https://")) {
				SSLContext sc = SSLContext.getInstance("SSL");

				TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllCertManager() };
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
			}

			URLConnection urlConnection = url.openConnection();

			return postData(urlConnection, data, timeoutMillis);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String postDataForString(String urlString, String data,
			int timeoutMillis, Cancelable cancelable) {

		try {
			InputStream is = postData(urlString, data, timeoutMillis);
			return convertToString(is, cancelable);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Element postDataForElement(String urlString, String data,
			int timeoutMilis) {
		try {
			InputStream is = postData(urlString, data, timeoutMilis);
			return XmlHelper.parseXml(is);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String convertToString(InputStream inputStream,
			Cancelable cancelable) {

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			StringBuilder sb = new StringBuilder(inputStream.available());
			String line;
			while ((line = rd.readLine()) != null
					&& (cancelable == null || !cancelable.isCanceled())) {
				sb.append(line);
			}
			rd.close();
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
