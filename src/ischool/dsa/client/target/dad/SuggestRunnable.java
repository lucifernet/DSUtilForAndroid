package ischool.dsa.client.target.dad;

import ischool.dsa.client.ContractConnection;
import ischool.dsa.client.accesspoint.IAccessPoint;
import ischool.dsa.utility.http.Cancelable;

public class SuggestRunnable implements Runnable {

	public static final String SERVICE_ECHO = "Echo";

	private DadTargetURLProvider _provider;
	private IAccessPoint _accesspoint;

	public SuggestRunnable(DadTargetURLProvider provider,
			IAccessPoint accesspoint) {
		_provider = provider;
		_accesspoint = accesspoint;
	}

	@Override
	public void run() {

		if (_accesspoint.getRelativeURLs().size() == 0)
			return;

		String firstURL = _accesspoint.getRelativeURLs().iterator().next();

		if (_accesspoint.getRelativeURLs().size() == 1) {
			_provider.suggestURL(firstURL);
			return;
		}

		while (true) {

			long maxDuring = Long.MAX_VALUE;
			String suggestURL = firstURL;

			for (String url : _accesspoint.getRelativeURLs()) {
				ContractConnection cc = new ContractConnection(url, "info");

				long t1 = System.currentTimeMillis();
				try {
					cc.sendRequest(SERVICE_ECHO, new Cancelable());
				} catch (Exception ex) {
				}

				long during = System.currentTimeMillis() - t1;
				if (maxDuring > during) {
					maxDuring = during;
					suggestURL = url;
				}
			}

			_provider.suggestURL(suggestURL);

			// 休息 60 秒再重新評估
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {

			}
		}
	}
}
