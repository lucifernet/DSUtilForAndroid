package ischool.dsa.utility;

import java.net.URL;

public class DSAccessPoint {

	private URL url;

	private String name;

	private DSAccessPoint(URL url, String name) {
		this.url = url;
		this.name = name;
	}

	public static DSAccessPoint createInstance(URL url) {

		return new DSAccessPoint(url, "");
	}

	public static DSAccessPoint createInstance(String name) {

		try {

			return createInstance(
					"http://dsns1.ischool.com.tw/dsns/dsns", name);

		} catch (Exception e) {

			return null;
		}
	}

	public static DSAccessPoint createInstance(String dsnsURL, String name) {

		if (name.startsWith("http://")) {
			try {
				URL url = new URL(name);
				return new DSAccessPoint(url, name);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		try {
			DSRequest request = new DSRequest();
			XmlUtil.addElement(request.getBody(), "a", name);
			
			DSConnection con = new DSConnection(dsnsURL, "", "");
			DSResponse dsrsp = con.sendRequest("DS.NameService.GetDoorwayURL", request);
			String doorway = XmlUtil.getElementText(dsrsp.getBody(), "DoorwayURL");
			URL url = new URL(doorway);
			return new DSAccessPoint(url, name);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public URL getURL() {

		return url;
	}

	public String getName() {

		return name;
	}
}
