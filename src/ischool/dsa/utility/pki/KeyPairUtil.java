package ischool.dsa.utility.pki;
//
//import ischool.dsa.exception.DSAServerException;
//import ischool.dsa.utility.Converter;
//import ischool.dsa.utility.DSStatus;
//import ischool.dsa.utility.XmlHelper;
//import ischool.dsa.utility.XmlUtil;
//
//import java.lang.ref.Reference;
//import java.math.BigInteger;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.Key;
//import java.security.KeyException;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Signature;
//import java.security.spec.KeySpec;
//import java.security.spec.RSAPrivateCrtKeySpec;
//import java.security.spec.RSAPublicKeySpec;
//import java.util.Collections;
//import java.util.Iterator;
//
//import javax.crypto.Cipher;
//
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
public class KeyPairUtil {
//
//	public final static String Algorithm = "SHA1withRSA";
//	public final static String KeyPairAlgorithm = "RSA";
//
//	public static KeyPair generatorKeyPair() {
//		return generatorKeyPair(1024);
//	}
//
//	public static KeyPair generatorKeyPair(int keysize) {
//		try {
//			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator
//					.getInstance(KeyPairAlgorithm);
//
//			SecureRandom secrand = new SecureRandom();
//
//			keygen.initialize(keysize, secrand); // 初始化金鑰產生器
//			return keygen.genKeyPair();
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static Element genPrivKeyElement(KeyPair keys) {
//		byte[] encodedPrivKey = keys.getPrivate().getEncoded();
//
//		String xmlprikey = PvkConvert.privatekeyinfoToXMLRSAPriKey(
//				encodedPrivKey).replaceAll("[ \t\n\r]", ""); // remove an CrLf
//		// etc..
//
//		return XmlHelper.parseXml(xmlprikey);
//	}
//
//	public static Element genPubKeyElement(KeyPair keys) {
//		byte[] encodedPrivKey = keys.getPrivate().getEncoded();
//		String xmlpubkey = PvkConvert.privatekeyinfoToXMLRSAPubKey(
//				encodedPrivKey).replaceAll("[ \t\n\r]", ""); // remove an CrLf
//
//		return XmlHelper.parseXml(xmlpubkey);
//	}
//
//	public static PublicKey loadPubKey(Element e) {
//		try {
//			KeyFactory kf = KeyFactory.getInstance(KeyPairAlgorithm);
//
//			BigInteger modulus = new BigInteger(1,
//					Converter.fromBase64String(XmlUtil.getElementText(e,
//							"Modulus")));
//			BigInteger exponent = new BigInteger(1,
//					Converter.fromBase64String(XmlUtil.getElementText(e,
//							"Exponent")));
//
//			KeySpec pubks = new RSAPublicKeySpec(modulus, exponent);
//
//			PublicKey publicKey = kf.generatePublic(pubks);
//
//			return publicKey;
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static KeyPair loadKeyPair(Element e) {
//		PrivateKey priKey = loadPriKey(e);
//		PublicKey pubKey = loadPubKey(e);
//
//		KeyPair keys = new KeyPair(pubKey, priKey);
//		return keys;
//	}
//
//	public static PrivateKey loadPriKey(Element e) {
//		try {
//			// 從檔案讀取 RSA Key。
//			XmlHelper xmlkey = new XmlHelper(e);
//
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//			// 建立 PrivateKeySpec
//			RSAPrivateCrtKeySpec pks = new RSAPrivateCrtKeySpec(
//					PvkConvert.getBigInteger(xmlkey, "Modulus"),
//					PvkConvert.getBigInteger(xmlkey, "Exponent"),
//					PvkConvert.getBigInteger(xmlkey, "D"),
//					PvkConvert.getBigInteger(xmlkey, "P"),
//					PvkConvert.getBigInteger(xmlkey, "Q"),
//					PvkConvert.getBigInteger(xmlkey, "DP"),
//					PvkConvert.getBigInteger(xmlkey, "DQ"),
//					PvkConvert.getBigInteger(xmlkey, "InverseQ"));
//
//			// 產生 PrivateKey
//			PrivateKey pk = keyFactory.generatePrivate(pks);
//
//			return pk;
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static String sign(String privateKey, String content) {
//		PrivateKey prikey = Converter.fromBase64String(privateKey,
//				PrivateKey.class);
//
//		return sign(prikey, content);
//	}
//
//	public static String sign(PrivateKey privateKey, String content) {
//		try {
//			Signature signet = java.security.Signature.getInstance(Algorithm);
//
//			signet.initSign(privateKey);
//
//			signet.update(content.getBytes());
//
//			byte[] signed = signet.sign(); // 對訊息的數位簽章
//
//			String signature = Converter.toBase64String(signed);
//			return signature;
//
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static boolean validSignature(String publicKey, String content,
//			String signature) {
//		PublicKey pubkey;
//		try {
//			pubkey = Converter.fromBase64String(publicKey, PublicKey.class);
//		} catch (Exception ex) {
//			throw new DSAServerException(
//					DSStatus.UnhandledException("PublicKey recovery failure : "
//							+ publicKey), ex);
//		}
//		return validSignature(pubkey, content, signature);
//	}
//
//	public static boolean validSignature(PublicKey publicKey, String content,
//			String signature) {
//		byte[] signed = Converter.fromBase64String(signature);
//
//		try {
//			java.security.Signature signetcheck = java.security.Signature
//					.getInstance(Algorithm);
//			signetcheck.initVerify(publicKey);
//			signetcheck.update(content.getBytes());
//
//			if (!signetcheck.verify(signed))
//				return false;
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//		return true;
//	}
//
//	public static String encrypt(Key publicKey, String msg) {
//		try {
//			Cipher c = Cipher.getInstance(publicKey.getAlgorithm());
//			c.init(Cipher.ENCRYPT_MODE, publicKey);
//
//			byte[] b = c.doFinal(msg.getBytes());
//			return Converter.toBase64String(b);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static String decrypt(Key privateKey, String base64String) {
//		try {
//			byte[] source = Converter.fromBase64String(base64String);
//			Cipher c = Cipher.getInstance(privateKey.getAlgorithm());
//			c.init(Cipher.DECRYPT_MODE, privateKey);
//
//			byte[] b = c.doFinal(source);
//
//			return new String(b);
//
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//	}
//
//	public static Element signElement(KeyPair kp, Element target)
//			throws NoSuchAlgorithmException,
//			InvalidAlgorithmParameterException, KeyException, MarshalException,
//			XMLSignatureException {
//
//		Element copy = XmlUtil.copyElement(target);
//
//		// Create a DOM XMLSignatureFactory that will be used to
//		// generate the enveloped signature.
//		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
//
//		DOMSignContext dsc = new DOMSignContext(kp.getPrivate(), copy);
//
//		// Create a Reference to the enveloped document (in this case,
//		// you are signing the whole document, so a URI of "" signifies
//		// that, and also specify the SHA1 digest algorithm and
//		// the ENVELOPED Transform.
//		Reference ref = fac.newReference("", fac.newDigestMethod(
//				DigestMethod.SHA1, null), Collections.singletonList(fac
//				.newTransform(Transform.ENVELOPED,
//						(TransformParameterSpec) null)), null, null);
//
//		// Create the SignedInfo.
//		SignedInfo si = fac
//				.newSignedInfo(fac.newCanonicalizationMethod(
//						CanonicalizationMethod.INCLUSIVE,
//						(C14NMethodParameterSpec) null), fac
//						.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
//						Collections.singletonList(ref));
//
//		KeyInfoFactory kif = fac.getKeyInfoFactory();
//		KeyValue kv = kif.newKeyValue(kp.getPublic());
//		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
//
//		// Create the XMLSignature, but don't sign it yet.
//		XMLSignature signature = fac.newXMLSignature(si, ki);
//
//		// Marshal, generate, and sign the enveloped signature.
//		signature.sign(dsc);
//
//		Element returnElement = XmlUtil.replaceElement(target, copy);
//
//		return returnElement;
//	}
//
//	public static void validElement(PublicKey publicKey, Element source)
//			throws Exception {
//
//		Element copy = XmlUtil.copyElement(source);
//
//		// Find Signature element.
//		if (source == null)
//			throw new Exception("Source element can not be null.");
//
//		if (copy == null)
//			throw new Exception("Copy source fail.");
//
//		
//		NodeList nl = copy.getOwnerDocument().getElementsByTagNameNS(XMLSignature.XMLNS,
//				"Signature");
//
//		if (nl.getLength() == 0) {
//			throw new Exception("Cannot find Signature element : \n" + XmlHelper.convertToString(copy, true));
//		}
//		Element signed = (Element)nl.item(0);
//		 
////		Element signed = XmlUtil.selectElementByAttribute(copy,
////		 "Signature", "xmlns", XMLSignature.XMLNS);
//
//		// Create a DOMValidateContext and specify a KeySelector
//		// and document context.
//		
//		DOMValidateContext valContext = new DOMValidateContext(publicKey,
//				signed);
//		
//		valContext.setProperty("javax.xml.crypto.dsig.cacheReference",
//				Boolean.TRUE);
//
//		// Unmarshal the XMLSignature.
//		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
//		XMLSignature signature = fac.unmarshalXMLSignature(valContext);
//
//		// Validate the XMLSignature.
//		boolean coreValidity = signature.validate(valContext);
//
//		if (coreValidity)
//			return;
//
//		// Check core validation status.
//		boolean sv = signature.getSignatureValue().validate(valContext);
//
//		StringBuilder err = new StringBuilder(
//				"Signature failed core validation\n");
//
//		err.append("signature validation status: " + sv);
//
//		if (!sv) {
//
//			// Check the validation status of each Reference.
//			@SuppressWarnings("rawtypes")
//			Iterator i = signature.getSignedInfo().getReferences()
//						.iterator();
//			for (int j = 0; i.hasNext(); j++) {
//				boolean refValid = ((Reference) i.next())
//							.validate(valContext);
//				err.append("\nref[" + j + "] validity status: " + refValid);
//			}
//		}
//		throw new Exception(err.toString());
//
//	}
//
////	public static void main(String[] args) {
////		Element root = XmlUtil.createElement("Something");
////		XmlUtil.addElement(root, "Hello", "World");
////
////		KeyPair kp = generatorKeyPair(2048);
////
////		Element signedElement;
////		try {
////			signedElement = signElement(kp, root);
////		} catch (Exception e) {
////			e.printStackTrace();
////			return;
////		}
////
////		String signedString = XmlHelper.convertToString(root);
////	
////		try {
////			validElement(kp.getPublic(), signedElement);
////			System.out.println("valid ok");
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
}
