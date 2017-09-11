package com.xiaojd.base.tools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAHelper {

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}
	 
	public static String getMac() {
		try {
			byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuilder builder = new StringBuilder();    
	        for (byte b : mac) {
	        	String s = "000000" + Integer.toHexString(b);    
	        	builder.append(s.substring(s.length() - 2));    
	        	builder.append("-");    
	        }    
	        builder.deleteCharAt(builder.length() -1);    
	        return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getMac()) ;
	}
	
	public static void main1(String[] args) throws Exception {
		getMac();
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 密钥位数
		keyPairGen.initialize(1024, new SecureRandom("网卡号".getBytes()));
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		String publicKeyString = getKeyString(publicKey);
		System.out.println("public:\n" + publicKeyString);

		String privateKeyString = getKeyString(privateKey);
		System.out.println("private:\n" + privateKeyString);

		// 加解密类
		Cipher cipher = Cipher.getInstance("RSA");// Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// 明文
		byte[] plainText = "我们都很好！".getBytes();

		// 加密
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = cipher.doFinal(plainText);

		// 通过密钥字符串得到密钥
		publicKey = getPublicKey(publicKeyString);
		privateKey = getPrivateKey(privateKeyString);

		// 解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] deBytes = cipher.doFinal(enBytes);

		publicKeyString = getKeyString(publicKey);
		System.out.println("public:\n" + publicKeyString);

		privateKeyString = getKeyString(privateKey);
		System.out.println("private:\n" + privateKeyString);

		String s = new String(deBytes);
		System.out.println(s);

	}

}
