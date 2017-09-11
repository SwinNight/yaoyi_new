package com.xiaojd.base.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * MD5加密算法
 *
 */
public class MD5 {
	public MD5() {
		
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));// 若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
			// resultSb.append(byteToNumString(b[i]));//使用本函数则返回加密结果的10进制数字字串，即全数字形式
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToString(md.digest(resultString.getBytes("UTF-8")));
		} catch (Exception ex) {

		}
		return resultString;
	}

	public static char toG(String origin) {
		char c = origin.charAt(0);
		if (c == '0')
			return 'g';
		else if (c == '1')
			return 'h';
		else if (c == '2')
			return 'i';
		else if (c == '3')
			return 'j';
		else if (c == '4')
			return 'k';
		else if (c == '5')
			return 'l';
		else if (c == '6')
			return 'm';
		else if (c == '7')
			return 'n';
		else if (c == '8')
			return 'o';
		else if (c == '9')
			return 'p';
		else if (c == 'a')
			return 'q';
		else if (c == 'b')
			return 'r';
		else if (c == 'c')
			return 's';
		else if (c == 'd')
			return 't';
		else if (c == 'e')
			return 'u';
		else if (c == 'f')
			return 'v';
		else
			return 'g';
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "头孢克洛胶囊";
		System.out.println(MD5Encode(s));
	}
}
