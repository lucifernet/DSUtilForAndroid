package ischool.dsa.utility;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class TripleDESHelper {
	public synchronized static String encode(String plaintxt, String key)
			throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(key.getBytes());
		String str = Base64.encodeToString(b, Base64.DEFAULT);

		return encode(plaintxt, str.getBytes());
	}

	public synchronized static String encode(String plaintxt, byte[] key)
			throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key, "TripleDES");

		Cipher nCipher = Cipher.getInstance("TripleDES");
		nCipher.init(Cipher.ENCRYPT_MODE, keySpec);

		byte[] cipherbyte = nCipher.doFinal(plaintxt.getBytes());

		return new String(Base64.encodeToString(cipherbyte, Base64.DEFAULT));
	}

	public synchronized static String decode(String base64txt, String key)
			throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(key.getBytes());
		String str = Base64.encodeToString(b, Base64.DEFAULT);

		return decode(base64txt, str.getBytes());
	}

	public synchronized static String decode(String base64txt, byte[] key)
			throws Exception {

		SecretKeySpec keySpec = new SecretKeySpec(key, "TripleDES");

		Cipher nCipher = Cipher.getInstance("TripleDES");
		nCipher.init(Cipher.DECRYPT_MODE, keySpec);

		byte[] encData = Base64.decode(base64txt.getBytes(), Base64.DEFAULT);
		byte[] decryptedtxt = nCipher.doFinal(encData);

		return new String(decryptedtxt);
	}

	public synchronized static String encode(String plaintxt) throws Exception {
		return encode(plaintxt, "");
	}

	public synchronized static String decode(String base64txt) throws Exception {
		return decode(base64txt, "");
	}

	public static void main(String[] arg) throws Exception {
		String key = "prometheus.lie@gmail.com";
		String result = TripleDESHelper.encode("12345", key);
		System.out.println(result);
		result = TripleDESHelper.decode(result, key);
		System.out.println(result);
	}
}
