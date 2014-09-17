package ischool.dsa.client.target;

import java.util.Locale;

import ischool.dsa.client.target.dsns.DSNSTargetURLProvider;

public class AutoSwitchURLProvider implements ITargetURLProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ITargetURLProvider _provider;
	private String _original;
	
	public AutoSwitchURLProvider(String urlOrDsnsName) {
		_original = urlOrDsnsName;
		
		if (urlOrDsnsName.toLowerCase(Locale.getDefault()).startsWith("http://")
				|| urlOrDsnsName.toLowerCase(Locale.getDefault()).startsWith("https://"))
			_provider = new SingleTargetURLProvider(urlOrDsnsName);
		else
			_provider = new DSNSTargetURLProvider(urlOrDsnsName);
	}

	@Override
	public String getTargetURL() {
		return _provider.getTargetURL();
	}

	@Override
	public String getOriginal() {
		return _original;
	}
}
