package ischool.dsa.utility;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
	public static SecretKey getSecretKey(char[] password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		MessageDigest digester = MessageDigest.getInstance("MD5");

		for (int i = 0; i < password.length; i++) {
			digester.update((byte) password[i]);
		}
		byte[] passwordData = digester.digest();

		return new SecretKeySpec(passwordData, "AES");

	}

	public static byte[] encrypt(char[] password, String text)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		SecretKey secret = getSecretKey(password);

		Cipher cipher = Cipher.getInstance("AES");

		// NOTE: This is where the Exception is being thrown
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		byte[] ciphertext = cipher.doFinal(text.getBytes("UTF-8"));
		return (ciphertext);
	}

	public static byte[] decrypt(char[] password, byte[] bytes)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		SecretKey secret = getSecretKey(password);

		Cipher cipher = Cipher.getInstance("AES");

		// NOTE: This is where the Exception is being thrown
		cipher.init(Cipher.DECRYPT_MODE, secret);
		byte[] ciphertext = cipher.doFinal(bytes);
		return (ciphertext);
	}

	public static String encrypt(String data, String key) throws Exception {
		byte[] b = encrypt(key.toCharArray(), data);
		return Converter.toBase64String(b);
	}

	public static String decrypt(String base64String, String key)
			throws Exception {
		byte[] b = Converter.fromBase64String(base64String);
		b = decrypt(key.toCharArray(), b);
		return new String(b);
	}

	public static String decrypt(String base64String, String key, String charsetEncoding)
			throws Exception {
		byte[] b = Converter.fromBase64String(base64String);
		b = decrypt(key.toCharArray(), b);
		return new String(b, charsetEncoding);
	}

	public static void main(String[] args) throws Exception {
		String password = "1234";

		String data = AESHelper.decrypt("BgiyrpoWXWOtsgTMFVeKgA==", password);
		System.out.println(data);
	}
}
