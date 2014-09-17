package ischool.dsa.client.target.dad;

import ischool.dsa.client.accesspoint.AccessPoint;
import ischool.dsa.client.accesspoint.IAccessPoint;
import ischool.dsa.client.target.ITargetURLProvider;

public class DadTargetURLProvider implements ITargetURLProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IAccessPoint _accessPoint;
	private String _original;
	private String _suggestURL;

	public DadTargetURLProvider(String dsnsName) {
		_original = dsnsName;
		_accessPoint = AccessPoint.lookup(dsnsName);

		Thread t = new Thread(new SuggestRunnable(this, _accessPoint));
		t.start();
	}

	public DadTargetURLProvider(String dsns, String dsnsName) {
		_accessPoint = AccessPoint.lookup(dsns, dsnsName);

		Thread t = new Thread(new SuggestRunnable(this, _accessPoint));
		t.start();
	}

	@Override
	public String getTargetURL() {
		if (_suggestURL != null && !_suggestURL.isEmpty())
			return _suggestURL;

		for (String url : _accessPoint.getRelativeURLs())
			return url;

		return "";
	}

	@Override
	public String getOriginal() {
		return _original;
	}
	
	public synchronized void suggestURL(String url) {
		_suggestURL = url;
	}
}
