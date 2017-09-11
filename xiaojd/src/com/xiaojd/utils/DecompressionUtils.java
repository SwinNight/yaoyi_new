package com.xiaojd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class DecompressionUtils {

	/**
	 * 2014-04-01 张宇飞 解压包
	 * **/
	public List<String> Unpack(String fpath, String filepath) {
		fpath = fpath.replace("/", "\\");
		File file = new File(filepath);
		List strList = new ArrayList();
		try {
			ZipFile zipFile = new ZipFile(file);
			ZipInputStream zipInputStream = new ZipInputStream(
					new FileInputStream(file));
			ZipEntry zipEntry = null;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {

				String path = fpath + zipEntry.getName();
				strList.add(path);
				File wFile = new File(path);
				if (!wFile.getParentFile().exists()) {
					wFile.getParentFile().mkdirs();
				} else {
					FileOutputStream fileOutputStream = new FileOutputStream(
							wFile);
					InputStream fileInputStream = zipFile
							.getInputStream(zipEntry);

					int num = 0;
					byte b[] = new byte[1024];
					while ((num = fileInputStream.read(b)) != -1) {
						fileOutputStream.write(b, 0, num);
					}
					fileInputStream.close();
					fileOutputStream.close();
				}
			}
			zipInputStream.close();
			zipFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strList;
	}
	
	static public String fieldSplitStr = "" + (char) 7;
	static public String recSplitStr = "" + (char) 8;
	
	public static String[][] stringToArray(String retString) {
		String[] recs = retString.split(recSplitStr);
		String[][] ret = new String[recs.length][];
		for (int i = 0; i < recs.length; i++) {
			if (recs[i].endsWith(fieldSplitStr + fieldSplitStr)) {
				ret[i] = (recs[i].replaceAll(fieldSplitStr + "$", "#"))
						.split(fieldSplitStr);
				ret[i][ret[i].length - 1] = "";
			} else {
				ret[i] = recs[i].split(fieldSplitStr);
			}
		}
		
		return ret;

	}
}
