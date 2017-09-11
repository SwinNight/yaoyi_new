package com.xiaojd.utils;

import com.xiaojd.entity.util.Config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsPtUtils
{
  Logger loger = LoggerFactory.getLogger(SmsPtUtils.class);

  public static String sendSms(String content, String params)
  {
    String url = Config.getValue("server", "smsConfigSendUrl");
    String name = Config.getValue("server", "smsConfigUser");
    String password = Config.getValue("server", "smsConfigPassword");
    String userNamePass = "?username=" + name + "&password=" + password;
    StringBuffer sb1 = new StringBuffer();
    sb1.append(userNamePass);
    sb1.append("&attime=");
/*    try {
		content = URLEncoder.encode(content,"GBK");
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}*/
    sb1.append("&content=" + content);
    sb1.append("&params=" + params);
    String param = sb1.toString();

    String sr = sendPostSms(url, param);
    if ((sr != null) && (!("".equals(sr))) && (sr.indexOf("result=") > -1)) {
      String code = sr.split("&")[0].split("=")[1];
      return code;
    }
    return "-1";
  }

  public static String checkSmsNumber()
  {
    Map smsMap = new HashMap();
    String url = Config.getValue("server", "smsCheckNumberUrl");
    String name = Config.getValue("server", "smsConfigUser");
    String password = Config.getValue("server", "smsConfigPassword");
    String userNamePass = "?username=" + name + "&password=" + password;

    String sr2 = sendPostSms(url, userNamePass);
    if ((sr2 != null) && (!("".equals(sr2))) && (sr2.indexOf("result=0") > -1)) {
      String number = sr2.split("&")[1].split("=")[1];
      return number;
    }
    return "-1";
  }

  @SuppressWarnings("deprecation")
public static String sendPostSms(String url, String param)
  {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url+param);
      System.out.println(url+param);
      URLConnection conn = realUrl.openConnection();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
    }
    finally
    {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return result;
  }
}