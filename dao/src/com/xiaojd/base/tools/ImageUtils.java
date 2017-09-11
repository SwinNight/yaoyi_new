package com.xiaojd.base.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtils {
	static public void tran(String fileName) throws IOException{
		File file = new File(fileName);
		Image src = ImageIO.read(file);
		
		float tagSize = 300;			//目标图大小
		
		int old_w = src.getWidth(null);
		int old_h = src.getHeight(null);
		int new_w,new_h;
		float tmp = old_w>old_h?old_w / tagSize:old_h / tagSize;
				
		new_w = Math.round(old_w/tmp);
		new_h = Math.round(old_h/tmp);
		
		BufferedImage tag = new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null);
		FileOutputStream newImage = new FileOutputStream(fileName.replaceAll(".JPG", "1.JPG"));
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newImage);
		encoder.encode(tag);
		newImage.close();
		
	}

public static void main(String args[]) throws IOException{
		tran("C:/Documents and Settings/Administrator/My Documents/我接收到的文件/照片0729 063.JPG");
	}
}

