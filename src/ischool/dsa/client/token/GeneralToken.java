package ischool.dsa.client.token;

import org.w3c.dom.Element;

public class GeneralToken implements ITokenProvider {

	private Element mElm ;
	
	public GeneralToken(Element elm) {
		mElm = elm;
	}
	
	@Override
	public Element getSecurityToken() {
		return mElm;
	}

}
