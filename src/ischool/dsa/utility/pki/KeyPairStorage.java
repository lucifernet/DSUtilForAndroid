package ischool.dsa.utility.pki;
//
//import ischool.dsa.client.ContractConnection;
//import ischool.dsa.client.DSScope;
//import ischool.dsa.client.target.ITargetURLProvider;
//import ischool.dsa.client.target.dsns.DSNSTargetURLProvider;
//import ischool.dsa.utility.DSRequest;
//import ischool.dsa.utility.DSResponse;
//import ischool.dsa.utility.XmlUtil;
//
//import java.security.PublicKey;
//import java.util.HashMap;
//
//import org.w3c.dom.Element;
//
public class KeyPairStorage {
//	private static HashMap<String, PublicKey> _publicKeys;
//
//	static {
//		_publicKeys = new HashMap<String, PublicKey>();
//	}
//
//	public static PublicKey getContractPublicKey(String application, String contract) {
//		String key = getKey(application, contract);
//		if (_publicKeys.containsKey(key))
//			return _publicKeys.get(key);
//
//		ITargetURLProvider target = new DSNSTargetURLProvider(application);
//		ContractConnection cc = new ContractConnection(target, "info");
//
//		DSRequest req = new DSRequest();
//		Element content = XmlUtil.createElement("Request");
//		XmlUtil.addElement(content, "Contract", contract);
//		req.setContent(content);
//
//		DSResponse rsp = cc.sendRequest("Public.GetPublicKey", req);
//		content = rsp.getContent();
//		PublicKey pk = KeyPairUtil.loadPubKey(content);
//		_publicKeys.put(key, pk);
//
//		return pk;
//	}
//
//	public static PublicKey getApplicationPublicKey(String application) {
//		String key = getKey(application, "*");
//		if (_publicKeys.containsKey(key))
//			return _publicKeys.get(key);
//
//		ITargetURLProvider target = new DSNSTargetURLProvider(application);
//		ContractConnection cc = new ContractConnection(target, "info");
//
//		DSRequest req = new DSRequest();
//
//		DSResponse rsp = cc.sendRequest("Public.GetApplicationPublicKey", req);
//		Element content = rsp.getContent();
//		PublicKey pk = KeyPairUtil.loadPubKey(content);
//		_publicKeys.put(key, pk);
//
//		return pk;
//	}
//
//	public static PublicKey getScopePublicKey(DSScope scope) {
//		if (scope.isSpecifiedContract()) {
//			return getContractPublicKey(scope.getApplication(), scope.getContract());
//		}
//		return getApplicationPublicKey(scope.getApplication());
//	}
//
//	private static String getKey(String application, String contract) {
//		return application + "/" + contract;
//	}
}
