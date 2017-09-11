package com.xiaojd.utils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.record.formula.functions.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.Region;
import com.xiaojd.base.tools.StringUtils;
import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.util.Config;

/**
 * 拼装报表
 * **/
public class ReportExcelUtil {
	
	public static int ParseInt(String key) {
		int m=0;
		try{
			 m=Integer.parseInt(key);
		}catch(Exception e) {
			m=0;
		}
		return m;
	}
	
	public static double ParseDouble(String key) {
		double m=0;
		try{
			 m= Double.parseDouble(key);
		}catch(Exception e) {
			m=0;
		}
		return m;
	}
	
	
	public static String DateFormate(String time,int style) {
		DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat formatT= new SimpleDateFormat("yyyy-MM-dd");
		
		DateFormat format1= new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat format2= new SimpleDateFormat("MM/dd HH/mm");
		DateFormat format3= new SimpleDateFormat("MM月dd日HH时mm分");
	
		Date date = null;
		if(time ==null || "".equals(time)) {
			return "";
		}
		String toDate="";
		try {    
			if(style ==0) {
	            date = format.parse(time);   
	            toDate= format1.format(date);
			} 
			if(style ==1){
				date = format.parse(time); 
				toDate = format2.format(date);
			}
			if(style ==2){
				date = formatT.parse(time); 
				toDate = format1.format(date);
			}
			if(style ==3) {
				date = format.parse(time); 
				toDate = format3.format(date);
			}
			
	  } catch (ParseException e) {    
	           e.printStackTrace();    
	  } 
      return toDate;
	}
	
	public static void setRegionStyle(HSSFSheet sheet, Region region, HSSFCellStyle cs) {
		 for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
		      HSSFRow row = sheet.getRow(i);
			  for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
				   HSSFCell cell = row.createCell((short) j);//创建每一个cell
				   cell.setCellStyle(cs);
			  }
		 }
	}

	public static List<HSSFSheet> exportCF(EngPtCf cf,List<EngPtDrug> drugs,HSSFWorkbook demoWorkBook){
		int rowNo = 0;
		int sheetNo = 0;
		Hashtable<Integer, HSSFSheet> sheetTable = new Hashtable<Integer, HSSFSheet>();
		
		HSSFCellStyle cellStyleF = demoWorkBook.createCellStyle();	
		cellStyleF.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
// 上下居
		cellStyleF.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		//黑体 小四 加粗   标题
		HSSFCellStyle cellStyleMainF = demoWorkBook.createCellStyle();//标题	
		cellStyleMainF.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleMainF.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleMainF.setWrapText(true);
		HSSFFont fontF = demoWorkBook.createFont();
		fontF.setFontHeightInPoints((short) 12);//字体大小
		fontF.setColor(HSSFColor.BLACK.index);//颜色
		fontF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		fontF.setFontName("黑体");//字体
		cellStyleMainF.setFont(fontF);
		
		//-----------------------
		HSSFCellStyle cellStyle = demoWorkBook.createCellStyle();	
		cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		//黑体 小二 加粗   标题
		HSSFCellStyle cellStyleMain = demoWorkBook.createCellStyle();//标题	
		cellStyleMain.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleMain.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleMain.setWrapText(true);
		HSSFFont font = demoWorkBook.createFont();
		font.setFontHeightInPoints((short) 17);//字体大小
		font.setColor(HSSFColor.BLACK.index);//颜色
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontName("黑体");//字体
		cellStyleMain.setFont(font);
		
		//仿宋_GB2312 五号 填写时主要的字体 
		HSSFFont fontGB= demoWorkBook.createFont();
		fontGB.setFontHeightInPoints((short) 7);//字体大小
		fontGB.setColor(HSSFColor.BLACK.index);//颜色
		fontGB.setFontName("仿宋_GB2312");//字体
		HSSFCellStyle cellStyleFront = demoWorkBook.createCellStyle();	
		cellStyleFront.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleFront.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleFront.setWrapText(true);
		cellStyleFront.setFont(fontGB);
		// 
		
		//仿宋_GB2312 五号 填写时主要的字体  带网格线
		HSSFFont fontGBLine= demoWorkBook.createFont();
		fontGBLine.setFontHeightInPoints((short) 7);//字体大小
		fontGBLine.setColor(HSSFColor.BLACK.index);//颜色
		fontGBLine.setFontName("仿宋_GB2312");//字体
		
		HSSFCellStyle cellStyleCenter = demoWorkBook.createCellStyle();	
		cellStyleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleCenter.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleCenter.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleCenter.setWrapText(true);
		cellStyleCenter.setFont(fontGBLine);

		//仿宋_GB2312 五号 填写  带网格线 加粗
		HSSFFont fontGBBold= demoWorkBook.createFont();
		fontGBBold.setFontHeightInPoints((short) 7);//字体大小
		fontGBBold.setColor(HSSFColor.BLACK.index);//颜色
		fontGBBold.setFontName("仿宋_GB2312");//字体
		fontGBBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		HSSFCellStyle cellStyleCenterBold = demoWorkBook.createCellStyle();	
		cellStyleCenterBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleCenterBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleCenterBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleCenterBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleCenterBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleCenterBold.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleCenterBold.setWrapText(true);
		cellStyleCenterBold.setFont(fontGBBold);

		//左边，黑体 小五  带网格线
		HSSFFont fontLeft= demoWorkBook.createFont();
		fontLeft.setFontHeightInPoints((short) 9);//字体大小
		fontLeft.setColor(HSSFColor.BLACK.index);//颜色
		fontLeft.setFontName("黑体");//字体
		fontLeft.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		HSSFCellStyle cellStyleLeft = demoWorkBook.createCellStyle();	
		cellStyleLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleLeft.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 上下居
		cellStyleLeft.setWrapText(true);
		cellStyleLeft.setFont(fontLeft);
		// 
		String  title = "苏州市立医院门诊处方"; 
		String patientNo ="";
		
	   try{	    
				    rowNo = 0;
				    sheetNo =0;
				    HSSFSheet hSheetx  = demoWorkBook.createSheet("处方号:"+cf.getId()+"_"+cf.getName());
					sheetTable.put(sheetNo, hSheetx);
						
					HSSFRow  row = sheetTable.get(sheetNo).createRow(rowNo++);
					setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleMain);//统一样式
					HSSFCell cell = row.getCell(0);
					cell.setCellValue(title);
					sheetTable.get(sheetNo).addMergedRegion(new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)));
					
					
				   row = sheetTable.get(sheetNo).createRow(rowNo++);
				  setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
				   cell = row.getCell(0);
				   cell.setCellValue("姓名");
				   cell = row.getCell(1);
				   cell.setCellValue(cf.getName());
				   cell = row.getCell(2);
				   cell.setCellValue("性别");
				   cell = row.getCell(3);
				   cell.setCellValue(cf.getSex());
				   cell = row.getCell(4);
				   cell.setCellValue("年龄");
				   cell = row.getCell(5);
				   cell.setCellValue(cf.getAge());
				   cell = row.getCell(6);
				   cell.setCellValue("日期");
				   cell = row.getCell(7);
				   cell.setCellValue(cf.getPresDateTime());
			      row = sheetTable.get(sheetNo).createRow(rowNo++);
				  setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
				   cell = row.getCell(0);
				   cell.setCellValue("处方号");
				   cell = row.getCell(1);
				   cell.setCellValue(cf.getId());
				   cell = row.getCell(2);
				   cell.setCellValue("个人编号");
				   cell = row.getCell(3);
				   cell.setCellValue(cf.getPatientNo());
				   cell = row.getCell(4);
				   cell.setCellValue("付费类别");
				   cell = row.getCell(5);
				   cell.setCellValue(cf.getPayType());
					   
			      row = sheetTable.get(sheetNo).createRow(rowNo++);
				  setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
				   cell = row.getCell(0);
				   cell.setCellValue("临床诊断");
				   cell = row.getCell(1);
				   cell.setCellValue(cf.getDiagnose());
				   sheetTable.get(sheetNo).addMergedRegion(new Region(rowNo-1, (short) 1, rowNo-1, (short) (7)));
				   
				   for(int i=0;i<drugs.size();i++) {
					      EngPtDrug drug = drugs.get(i);
					      row = sheetTable.get(sheetNo).createRow(rowNo++);
						  setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
						   cell = row.getCell(0);
						   cell.setCellValue(i+1);
						   cell = row.getCell(1);
						   cell.setCellValue(drug.getDrugName()+" " +drug.getQuantity()+"*"+drug.getDispenseUnit());
						   sheetTable.get(sheetNo).addMergedRegion(new Region(rowNo-1, (short) 1, rowNo-1, (short) (7)));
						   
					       row = sheetTable.get(sheetNo).createRow(rowNo++);
						   setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
						   cell = row.getCell(1);
						   cell.setCellValue("用法 "+drug.getAdminDose()+"  "+drug.getAdminRoute() + " " +drug.getAdminFrequency());
						   sheetTable.get(sheetNo).addMergedRegion(new Region(rowNo-1, (short) 1, rowNo-1, (short) (7)));
					   
				   }
				   
				 //--------------------------------------------------
				      row = sheetTable.get(sheetNo).createRow(rowNo++);
					  setRegionStyle(sheetTable.get(sheetNo),new Region(rowNo-1, (short) 0, rowNo-1, (short) (7)),cellStyleCenter);//统一样式
					   cell = row.getCell(0);
					   cell.setCellValue("医师编号");
					   cell = row.getCell(1);
					   cell.setCellValue(cf.getDocId());
					   cell = row.getCell(2);
					   cell.setCellValue("医生");
					   cell = row.getCell(3);
					   cell.setCellValue(cf.getDocName());
					   cell = row.getCell(4);
					   cell.setCellValue("总计");
					   cell = row.getCell(5);
					   cell.setCellValue(cf.getTotalAmount());	      
		}catch(Exception e){
			e.printStackTrace();
		}
		List<HSSFSheet> list = new ArrayList<HSSFSheet>();
		for (int i = 0; i <= sheetNo; i++) {
			list.add(list.size(), sheetTable.get(i));
		}
		return list;
	}
	}