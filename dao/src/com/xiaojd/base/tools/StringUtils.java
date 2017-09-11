/*
 * $Revision$
 * $Date$
 */
package com.xiaojd.base.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.xiaojd.entity.util.Config;
/**
 * @author xiexinwei
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
	
	
	/**
	 * 防拦截html转换代码
	 */
	public static final String HTML_JING = "html_j"; // #
	public static final String HTML_IMG = "html_i"; // <img
	public static final String HTML_A = "html_a";  // <a
	public static final String HTML_FRAME = "html_f"; // <frame
	
	public static String replaceAllHTML(String content) {
		if(null == content || content.length() < 1)
			return content;
		
		return content.replaceAll(StringUtils.HTML_JING, "#").replaceAll(
				StringUtils.HTML_IMG, "<img").replaceAll(StringUtils.HTML_A, "<a")
				.replaceAll(StringUtils.HTML_FRAME, "<frame");
	}
	
	static public Date double2Date(double s) {
		try {
			return new Date(new SimpleDateFormat ("yyyy-MM-dd").parse("1900-01-00").getTime() + (long)((s-1)*24*60*60*1000));
		} catch (ParseException e) {
			return new Date();
		}
	}
	/**
     * startString endString 都能转换为时间后进行比较 
     * @param startString
     * @param endString
     * @return 只有endString > startString 才返回 true
     */
    public  static boolean compareTime(String startString,String endString) {
    	   DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	   if(startString == null || "".equals(startString) || endString == null || "".equals(endString)) {
    		   return false;
    	   }
		   try {
		           Date start = df.parse(startString);
		           Date end = df.parse(endString);
		           if (end.getTime() > start.getTime()) {
		              return true;
		           }
		     } catch (Exception exception) {
		           exception.printStackTrace();
		     }
		     return false;
      }
	
	static public String currDateTime(){
		return	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	static public String readUrl(File f) throws IOException {
		String dataline;
		String ret = "";
		try {
			InputStream inputStream = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream));
//			while ((dataline = in.readLine()) != null) {
//				ret = ret + dataline + "\r\n";
//			}
			StringBuffer tmp = new StringBuffer();
			while ((dataline = in.readLine()) != null) {
//				System.out.println(i++);
				tmp.append(dataline + "\r\n");
			}
			in.close();
			inputStream.close();
			ret = tmp.toString();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static String sendPost(String url,String param)
	 {
	  String result="";
	  try{
	   URL httpurl = new URL(url);
	   HttpURLConnection httpConn = (HttpURLConnection)httpurl.openConnection();       
	   httpConn.setDoOutput(true);
	   httpConn.setDoInput(true);
	   PrintWriter out = new PrintWriter(httpConn.getOutputStream());
	   out.print(param);
	   out.flush();
	   out.close();
	   
//	   InputStream is = httpConn.getInputStream();
//	   is = new BufferedInputStream(is);
//	   Reader in = new InputStreamReader(is);
	   
	   BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

	   String line;
	   while ((line = in.readLine())!= null)
	   {
	    result += line; 
	   }
	   in.close();
	  }catch(Exception e){
	   System.out.println("没有结果！"+e);
	  }
	  return result;
	 }
	
	
	static public String readUrl(URL url) throws IOException {

		URLConnection connection = (URLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6) Gecko/20050223 Firefox/1.0.1");
		connection.connect();

		
		
		InputStream urlStream = connection.getInputStream();

		// if (!connection.getHeaderField("Content-Type").startsWith("text"))
		// return "";

		String charSet = connection.getContentEncoding();
		if (connection.getHeaderField("Content-Type") != null
				&& connection.getHeaderField("Content-Type").toUpperCase()
						.indexOf("UTF-8") > 0) {
			charSet = "UTF-8";
		}

		java.io.BufferedReader reader = null;
		if (charSet != null && charSet.length() > 0) {
			reader = new java.io.BufferedReader(new java.io.InputStreamReader(
					urlStream, charSet));
		} else
			reader = new java.io.BufferedReader(new java.io.InputStreamReader(
					urlStream));

		String line = "";

		StringBuffer sb = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			// Pattern p =
			// Pattern.compile("&#([0-9]*)?;",Pattern.CASE_INSENSITIVE);
			// Matcher m = p.matcher(line);
			// String str = "";
			// int start = 0;
			// int end = 0;
			// while (m.find()) {
			// start = m.start();
			// str += line.substring(end,start)+(char)
			// Integer.parseInt(m.group(1));
			// end = m.end();
			// }
			//			
			// str += line.substring(end);
			//			
			// sb.append(str);

			sb.append(line);
			sb.append("\r\n");
			// if (sb.length()>100000) break;
		}

		return sb.toString();

	}

	static public String readUrl(String url) throws IOException {

		URL URL = new URL(url);

		return readUrl(URL);

	}

	static public String getNestedString(String context, String start, String end) {
		int a = context.indexOf(start);
		int b = context.indexOf(end,a);	
		if (a>=0&&b>=0){
			return context.substring(a+start.length(),b);
		}
		return null;
	}
	
	static public String[] getNestedStrings(String context, String start, String end) {
		Pattern p = Pattern.compile(start + "([\\u0000-\\uffff]*?)" + end,
				Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
		Matcher m = p.matcher(context);
		
		Vector v = new Vector();

		while (m.find()) {
			v.add(m.group(1));
		}
		String[] ret = new String[v.size()];
		for (int i=0;i<v.size();i++){
			ret[i] = ""+v.get(i);
		}
		return ret;
	}
	
	static public String findAfter(String context, String before) {
		if (before.trim().length() == 0)
			return context;

		String reg = before + "\\s*(\\S+)";
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);
		Matcher m = p.matcher(context);
		String ret = "";
		while (m.find()) {
			String tmp = deleteSpace(deleteHTML(m.group(1)));
			int length = tmp.trim().length();
			if (length > 0)
				ret+=tmp+"\n";
		}
		
		return ret;
	}

	static public String findBefore(String context, String next) {
		if (next.trim().length() == 0)
			return context;

		String reg = "\\s*(.*)\\s*" + next;
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);
		Matcher m = p.matcher(context);
		String ret = "";
		while (m.find()) {
			String tmp = deleteSpace(deleteHTML(m.group(1)));
			int length = tmp.trim().length();
			if (length > 0)
				ret+=tmp+"\n";
		}
		
		return ret;
	}

	static public String find(String context, String reg) {
		
		Pattern p = Pattern.compile(preProcess(reg), Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);
		Matcher m = p.matcher(context);
		while (m.find()) {
			return (m.group(1));
		}
		return "";
	}
	
	
	static public String deleteHTML(String context) {
		try {
			String reg = "<(style|script)[\\u0000-\\uffff]*?/\\1>|<!--[\\u0000-\\uffff]*?-->|<[\\u0000-\\uffff]*?>";
			// String reg =
			// "<(style|script)[\\u0000-\\uffff]*?/\\1>|<!--[\\u0000-\\uffff]*?-->|<(.|\\r\\n)*?>";
			// String reg =
			// "<(style|script)[s|^s]*?/\\1>|<!--[s|^s]*?-->|<(.|\\r\\n)*?>";
			Pattern p = Pattern.compile(reg, Pattern.MULTILINE);
			Matcher m = p.matcher(context);
			context = m.replaceAll("");
			context = context.replaceAll("\\r\\n[\\s| ]*\\r", "\n");
			context = context.replaceAll("&nbsp;", " ");

			// context = context.replaceAll("/", "");

			return context;

		} catch (Exception e) {
			return context;
		}

	}
	
	
	static public String deleteSpace(String context) {
		return context.replaceAll("\\s+", "");
	}
	

	static String n = "(?:[一|二|三|四|五|六|七|八|九]|[1|2|3|4|5|6|7|8|9]|[１|２|３|４|５|６|７|８|９])";
	static String lineRegex = "(?:①|②|③|④|⑤|第nn|nn、|nn\\.|nn。|nn,|nn，|nn:|nn：|nn）)\\D|　　|【|\\(nn\\)|（nn）|<nn>|《nn》|〈nn〉".replaceAll("nn", n);
	
	public static String getAddress(){
		try {
			byte[] bs = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			String ret = "";
			for (int i=0;i<bs.length;i++){
				ret+=bs[i];
			}
			return ret;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static void main(String[] args) throws IOException {
//		System.out.println(md("我爱大大1234567"));
		System.out.println(demd(md(demd(demd(demd(md(md("13916610898"))))))));

		System.out.println(demd("^MTU7ODgyODmbNjk="));		

	}
	
	public static String line(String context){
		Pattern p = Pattern.compile(lineRegex); 
		
		
		Matcher m = p.matcher(context); 

		StringBuffer sb = new StringBuffer(); 

		 while (m.find()) { 

	    	 m.appendReplacement(sb, "\n"+m.group(0)); 

		 } 

		 m.appendTail(sb); 
		 return sb.toString();
	}
	


	static public String getClassName(String str){
		str = str.toLowerCase();
		return StringUtils.capitaliseAllWords(str.replace('_', ' ')).replaceAll(" ", "");
	}

	static public String getFieldName(String str){
		str = getClassName(str);
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}

	static public int stringToInt(String s){
		return Integer.parseInt(s);
	}

	static public Timestamp stringToTimestamp(String s) {
			return Timestamp.valueOf(s+" 00:00:00.0");
	}

	static public String urlEncode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s,"utf8");
	}

	
	static public String stringToString(String s){
		return s;
	}

	static public double stringToDouble(String s){
		return Double.parseDouble(s);
	}
	
	
	/**
	* 压缩字符串为 byte[]
	* 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法
	* 保存为字符串
	*
	* @param str 压缩前的文本
	* @return
	*/

	public static final byte[] compress(String str) {
		if (str == null)
			return null;

//		String[] a = str.split(""+(char)8);
//		for (int i=0;i<a.length;i++){
//			System.out.println(a[i]);
//		}		
		
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("1"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return compressed;
	}
	
	
	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		System.out.println("解压前长度"+compressed.length);
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = "";
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			
			try {
				while((zin.getNextEntry()) != null){
					while ((offset = zin.read(buffer)) != -1) {
						out.write(buffer, 0, offset);
					}
//					System.out.println("test:"+out.toString());
					decompressed += out.toString();
					out.close();	
					out = new ByteArrayOutputStream();
				}
								
			} catch (Exception e) {
				e.printStackTrace();
			}
//			decompressed = out.toString();
//			String[] a = decompressed.split(""+(char)8);
//			for (int i=0;i<a.length;i++){
//				System.out.println(a[i]);
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
	
	static public String rsGetString(ResultSet rs,String name) throws SQLException {
		String ret = rs.getString(name);
		if (ret == null){
			ret = "";
		}
		if (Config.getValue("his", "encode").length()>0){
			try {
				ret = new String(ret.getBytes(Config.getValue("his", "encode")),"GBK");
			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
			}		
		}
		return ret;
	}

	static public String rsGetString(ResultSet rs,int name) throws SQLException {
		String ret = rs.getString(name);
		if (ret == null){
			ret = "";
		}
		if (Config.getValue("his", "encode").length()>0){
			try {
				ret = new String(ret.getBytes(Config.getValue("his", "encode")),"GBK");
			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
			}		
		}
		return ret;
	}
	
	static public String md(String s){
		if (s == null || s.startsWith("^")) {
			return s; 
		}
		return "^"+new String(transBytes(Base64.encode(s.getBytes()),false));
	}

	
	static public String demd(String s){
		if (s == null || !s.startsWith("^")) {
			return s; 
		}
		return new String(Base64.decode(transBytes((s.substring(1).toCharArray()),true)));			
	}

	static private char[] cs1 = {'a','z','1','A','2','G','3','4','L','5','6','7','8','K','9','0','b','m','l','f','I'};
	static private char[] cs2 = new char[cs1.length];
	static{
		for (int i = 0; i < cs2.length; i++) {
			cs2[i]=cs1[cs1.length-i-1];
			
		}  
	}
	
 	static public char[] transBytes(char[] bs,boolean r){
		if (bs == null)
			return bs;
		
		if (r){
			for (int i = 0; i < bs.length; i++) {
				for (int j = 0; j < cs1.length; j++) {
					if (bs[i]==cs1[j]) {
						bs[i]=cs2[j];
						break;
					}
				}
			}
		} else {
			for (int i = 0; i < bs.length; i++) {
				for (int j = 0; j < cs2.length; j++) {
					if (bs[i]==cs2[j]) {
						bs[i]=cs1[j];
						break;
					}
				}
			}			
		}
		
		return bs;
	}

	static public Iterator<String> findAll(String context, String reg) {
		reg = preProcess(reg);
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				+ Pattern.MULTILINE);
		Matcher m = p.matcher(context);
		Vector<String> ret = new Vector<String>();
		while (m.find()) {
			ret.add((m.group(1)));
		}
		return ret.iterator();
	}

	static private String preProcess(String reg) {
		reg = reg.replaceAll("\\^\\^\\^", "([^>]+)((<[^>]*?>|\\\\s)*)");
		reg = reg.replaceAll("\\$\\$\\$", "(?:(?:<.*?>|\\\\s)*)([^<]+)");
		reg = reg.replaceAll("ddd", "([\\\\d|\\\\.]+)");
		reg = reg.replaceAll("###", "[\\\\u0000-\\\\uffff]");
		return reg;
	}

	static public String find(String context, String startReg,String endReg) {
	//		String ret = org.apache.commons.lang.StringUtils.getNestedString(context,startReg,endReg);
	//		return ret == null?"":ret;
			return find(context, startReg+"(###*?)"+endReg);
		}

	static public String repeat(String src, int repeat) {
		String ret = "";
		for (int i = 0; i < repeat; i++) {
			ret += src;
		}
		return ret;
	}
	
	static public String sqlFormat(String sql) {
		return sql.replaceFirst("select|SELECT", "select SQL_CALC_FOUND_ROWS");
	}
	
	public static boolean containsChinese(String str){
		char[] arr = str.toCharArray();
		for(char a:arr){
			if(a>=0x4e00 && a<=0x9fbb){
				return true;
			}
		}
		return false;
	}
	
}
