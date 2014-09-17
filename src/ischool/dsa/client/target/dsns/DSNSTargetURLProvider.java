package ischool.dsa.client.target.dsns;

import java.util.Locale;

import ischool.dsa.client.accesspoint.AccessPoint;
import ischool.dsa.client.target.ITargetURLProvider;

public class DSNSTargetURLProvider implements ITargetURLProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _targetURL;
	private String _original;
	
	public DSNSTargetURLProvider(String dsnsName) {
		_original = dsnsName;
		
		String lowName = dsnsName.toLowerCase(Locale.getDefault());
		if(lowName.startsWith("http://") || lowName.startsWith("https://"))
			_targetURL = dsnsName;
		else
			_targetURL = AccessPoint.getDoorwayURL(dsnsName);
	}

	@Override
	public String getTargetURL() {
		return _targetURL;
	}

	@Override
	public String getOriginal() {
		return _original;
	}
}
