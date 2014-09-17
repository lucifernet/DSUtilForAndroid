package ischool.dsa.client.token;

import ischool.dsa.utility.XmlUtil;

import org.w3c.dom.Element;

public class InternalTokenProvider implements ITokenProvider {

	@Override
	public Element getSecurityToken() {
		Element stt = XmlUtil.createElement("SecurityToken");
		stt.setAttribute("Type", "Internal");
		return stt;
	}

}
