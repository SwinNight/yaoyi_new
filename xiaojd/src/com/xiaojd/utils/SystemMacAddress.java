package com.xiaojd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 获取系统mac地址    杨子波
 * 2014-04-16
 * **/
public class SystemMacAddress {

	//获取系统版本
	private static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	//获取linux的mac地址
	private static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux下的命令一般取eth0 作为本地主网卡 显示信息中包含有MAC地址信息 用process流
			process = Runtime.getRuntime().exec("ifconfig ");

			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串 物理网卡地址
				index = line.toLowerCase().indexOf("hwaddr");
				// 找到了地址
				if (index != -1) {
					// 取出mac地址并去除两边空格
					mac = line.substring(index + 7).trim();

					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	//获取Windows2008的mac地址
	private static String getWindows2008MACAddress() {
		// 获取本地IP对象
		InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia)
					.getHardwareAddress();
			// 下面代码是把mac地址拼装成String
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}

			// 把字符串所有小写字母改为大写成为正规的mac地址并返回
			return sb.toString().toUpperCase();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}

	//获取Windows2003的mac地址
	private static String getWindows2003MACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// windows下的命令，显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[physical
				index = line.toLowerCase().indexOf("physical address");

				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						// 取出mac地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}
	
	public static String getOsMacAddress(){
		try{
			String os = getOSName();  
			if (os.toLowerCase().indexOf("windows") >= 0) {  
	           if(os.toLowerCase().indexOf("2003") >= 0){
	        	   return getWindows2003MACAddress();
	           }else{
	        	   // if(os.toLowerCase().indexOf("2008") >= 0)考虑本机是windows2007暂时去掉
	        	   return getWindows2008MACAddress();
	           }
	        }else{  
	           return getLinuxMACAddress();
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return "00-00-00-00-00-00";
	}
}
