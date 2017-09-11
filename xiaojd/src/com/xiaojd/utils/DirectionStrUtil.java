package com.xiaojd.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class DirectionStrUtil {
	private Pattern pattern ;
	
	/**
	 * 2014-03-24    杨子波
	 *截取字符串中【】及里面的内容
	 * 返回Matcher
	 * **/
	public Matcher getPatFindMatcher(String str){
		pattern = Pattern.compile("【(.*?)】");
	    Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	
	/**
	 * 2014-03-24    杨子波
	 *截取字符串中【】及里面以外的内容
	 * 返回String[]
	 * **/
	public String[] getSptString(String str){
		String[] sptcontext = str.split("【.*?】+");
		return sptcontext;
	}
	
	/**
	 * 2014-03-24    杨子波
	 *格式化说明书内容且拼凑说明书的html代码
	 *返回String
	 * **/
	public String getDirectionGroup(String str){
		/*String content = com.xiaojd.base.tools.StringUtils.find(str,"<DIV class=top_padding_panel>([\\u0000-\\uffff]*)<A name=goto_document_remark");
		content = content.replaceAll("<DIV style=\"DISPLAY: none\" id=content_remark[\\u0000-\\uffff]*?css_remark_body_content.*</A>", "");
		content = content.replaceAll("<A class=css_remark_edit_btn title.*?</A>","").replaceAll("<A name=content_item_.*?></A>", "");
		content = content.replaceAll("(<IMG .*?>|MediComSoftware|合理用药信息支持系统单机版)","");*/
		
		StringBuffer strbuf = new StringBuffer();
		if(StringUtils.isNotBlank(str)){
			String[] getcontent = str.split("<DIV class=para_title>"); //获取说明书标题
			strbuf.append("<DIV>");
			for(int i = 0 ; i< getcontent.length ; i++){
				if(i > 0){
					if(i == getcontent.length-1){
						String [] kw = getcontent[i].split("<DIV class=errata_body_title>"); //堪误
						if(kw.length > 1){
							strbuf.append("<a name=content_item_"+i+"></a><DIV class=para_title>");
							strbuf.append(kw[0]);
							strbuf.append("<a name=content_item_"+(i+1)+"></a><DIV class=errata_body_title>");
							strbuf.append(kw[1]);
						}else{
							strbuf.append("<a name=content_item_"+i+"></a><DIV class=para_title>");
							strbuf.append(kw[0]);
						}
					}else{
						strbuf.append("<a name=content_item_"+i+"></a><DIV class=para_title>");
						strbuf.append(getcontent[i]);
					}
				}else{
					strbuf.append(getcontent[i]);
				}
			}
			strbuf.append("</DIV>");
		}
		return strbuf.toString();
		
		/*
		str = str.replace("\n\t", "").replace("\n", "");
		if(str.startsWith("<DIV class=css_top_padding_div>")){
			return str;
		}
		int findcount = 1;
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer strbuf = new StringBuffer();
		if(StringUtils.isNotBlank(str)){
			Matcher mcher = getPatFindMatcher(str);//获取说明书标题
			while(mcher.find()){
				map.put(String.valueOf(findcount), findcount+" "+mcher.group(0));
				findcount++;
			}
			strbuf.append("<div class=css_top_padding_div>");
			String[] getcontent = getSptString(str);//获取说明书标题下的内容
			for(int i = 0 ; i< getcontent.length ; i++){
				if(i == 0){
					strbuf.append("<div class=css_doc_content_doctitle>"+getcontent[i]+"</div>");
					continue;
				}
				strbuf.append("<div style=display:block;><a name=content_item_"+i+"></a><div class=css_doc_content_title>");
				strbuf.append(map.get(String.valueOf(i)));
				strbuf.append("</div></div>");
				strbuf.append("<div class=css_doc_content_text>");
				strbuf.append(getcontent[i]);
				strbuf.append("</div>");
			}
			strbuf.append("</div>");
		}
		return strbuf.toString();
		*/
	}
	public String formatSms(String str){
		str = str.replace("\n\t", "").replace("\n", "");
		int findcount = 1;
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer strbuf = new StringBuffer();
		if(StringUtils.isNotBlank(str)){
			Matcher mcher = getPatFindMatcher(str);//获取说明书标题
			while(mcher.find()){
				map.put(String.valueOf(findcount), mcher.group(0));
				findcount++;
			}
			strbuf.append("<div align=\"left\">");
			String[] getcontent = getSptString(str);//获取说明书标题下的内容
			for(int i = 0 ; i< getcontent.length ; i++){
				if(i == 0){
					strbuf.append("<h3 align=\"center\">"+getcontent[i]+"</h3>");
					continue;
				}
				strbuf.append("<p>");
				strbuf.append(map.get(String.valueOf(i)));
				strbuf.append("</p>");
				strbuf.append("<p>");
				strbuf.append(getcontent[i]);
				strbuf.append("</p>");
			}
			strbuf.append("<div>");
		}
		return strbuf.toString();
	}
	public String getDirectionGroup_backup(String str){
		str = str.replace("\n\t", "&nbsp;").replace("\n", "&nbsp;");
		int findcount = 1;
		int contentitem = 2;
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer strbuf = new StringBuffer();
		if(StringUtils.isNotBlank(str)){
			strbuf.append("<table border=0 cellpadding=0 cellspacing=0 width=100%>");
			strbuf.append("<tbody>&nbsp;<tr>&nbsp;<td style=width:180px;background:#FCFCFC>");
			strbuf.append("<div id=catalog oncontextmenu=return&nbsp;false; ondragstart=return&nbsp;false;>");
			strbuf.append("<div class=toctitle><h3>目录</h3></div><dl style=display:block;overflow-x:hidden;overflow-y:auto;height:380px id=full-all >");
			Matcher mcher = getPatFindMatcher(str);//获取说明书标题
			
			while(mcher.find()){
				strbuf.append("<dd><a href=#Anchor"+findcount+">"+ findcount +" "+ mcher.group(0)+ " </a></dd>");
				map.put(String.valueOf(findcount), findcount+"&nbsp;&nbsp;"+mcher.group(0));
				findcount++;
			}
			strbuf.append("</dl></div></td>");
			strbuf.append("<td><div id=instr_content><div class=css_top_padding_div>");
			String[] getcontent = getSptString(str);//获取说明书标题下的内容
			
			for(int i = 0 ; i< getcontent.length ; i++){
				if(i == 0){
					strbuf.append("<div class=css_doc_content_doctitle>"+getcontent[i]+"</div>"
							+ "<a name=content_item_"+contentitem+"></a>");
					contentitem++;
					continue;
				}
				strbuf.append("<div style=display:block;><a name=Anchor"+i+"></a><div class=css_doc_content_title>");
				strbuf.append("&nbsp;"+map.get(String.valueOf(i)));
				strbuf.append("</div></div>");
				strbuf.append("<div class=css_doc_content_text>");
				strbuf.append(""+getcontent[i]);
				strbuf.append("</div>");
				strbuf.append("<a name=content_item_"+contentitem+">&nbsp;</a>");
				contentitem++;
			}
			strbuf.append("</div></td></tr></tbody></table>");
		}
		return strbuf.toString();
	}
}