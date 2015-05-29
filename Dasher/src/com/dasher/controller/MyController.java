package com.dasher.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.dasher.util.DateUtil;

public class MyController {
	public String getString(HttpServletRequest request,String paramName){
		String str = request.getParameter(paramName);
		if(str==null||str.length()<=0)
			str = "";
		return str.trim();
	}
	public int getInt(HttpServletRequest request,String paramName){
		String str = request.getParameter(paramName);
		int num = 0;
		try{
			if(str!=null&&str.length()>0)
				num = Integer.parseInt(str.trim());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		return num;
	}
	public Date getDate(HttpServletRequest request,String paramName){
		String strDate = request.getParameter(paramName).trim();
		if(strDate==null||strDate.length()==0)
			return null;
		else
			return DateUtil.parseStringToDate(strDate);
	}
	public String getJsonString(HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
        try {
	        BufferedReader reader = request.getReader();
			char[]buff = new char[1024];
			int len;
			while((len = reader.read(buff)) != -1) {
			  sb.append(buff,0, len);
			}
	    } catch (IOException e) {
	        e.printStackTrace();
        }
	    return sb.toString();
	}
}
