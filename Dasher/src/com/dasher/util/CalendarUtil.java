package com.dasher.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.dasher.model.Time;

public class CalendarUtil {
	//判断两个时间段的大小
	//-1字符串格式错误
	//0时间一小于时间二
	//1时间一大于时间二
	public static int timeCompare(String time1,String time2){
		try{
			//获取当前时间
			Calendar c = Calendar.getInstance();
			//处理时间time--12:00
			c.set(Calendar.HOUR,Integer.parseInt(time1.split(":")[0]));
			c.set(Calendar.MINUTE,Integer.parseInt(time1.split(":")[1]));
			
			long c1=c.getTimeInMillis();
			
			c.set(Calendar.HOUR,Integer.parseInt(time2.split(":")[0]));
			c.set(Calendar.MINUTE,Integer.parseInt(time2.split(":")[1]));
			
			long c2=c.getTimeInMillis();
			
			return c1>=c2?1:0;
		}catch(Exception e){
			return -1;
		}
	}
	//判断当前时间是否在两个时间段内
	public static boolean contentTime(List<String> time){
		if(time.size()==0){
			return true;
		}
		try{
			//获取当前时间
			Calendar c = Calendar.getInstance();
			long cur=c.getTimeInMillis();
			
			for(int i=0;i<time.size();i=i+2){
				c.set(Calendar.HOUR,Integer.parseInt(time.get(i).split(":")[0]));
				c.set(Calendar.MINUTE,Integer.parseInt(time.get(i).split(":")[1]));
				long c1=c.getTimeInMillis();
				
				c.set(Calendar.HOUR,Integer.parseInt(time.get(i+1).split(":")[0]));
				c.set(Calendar.MINUTE,Integer.parseInt(time.get(i+1).split(":")[1]));
				long c2=c.getTimeInMillis();
				
				if(c1<=cur&&cur>=c2){
					return true;
				}
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}
	//判断两个时间段的大小
	//-1字符串格式错误
	//0时间一小于当前时间
	//1时间一大于当前时间
	public static int timeCurCompare(String time1){
		try{
			//获取当前时间
			Calendar c = Calendar.getInstance();
			long c2=c.getTimeInMillis();
			
			//处理时间time--12:00
			c.set(Calendar.HOUR,Integer.parseInt(time1.split(":")[0]));
			c.set(Calendar.MINUTE,Integer.parseInt(time1.split(":")[1]));
			long c1=c.getTimeInMillis();
			
			return c1>c2?1:0;
		}catch(Exception e){
			return -1;
		}
	}
	//判断商家是否营业
	public static boolean IsBusiness(Time t){
		if(t.getFlag()==0){
			return false;
		}
		try{
			List<String> time=new ArrayList<String>();
			String time1=t.getTime1();
			if(time1!=""){
				time.add(time1.split("-")[0]);
				time.add(time1.split("-")[1]);
			}
			String time2=t.getTime2();
			if(time2!=""){
				time.add(time2.split("-")[0]);
				time.add(time2.split("-")[1]);
			}
			String time3=t.getTime3();
			if(time3!=""){
				time.add(time3.split("-")[0]);
				time.add(time3.split("-")[1]);
			}
			String time4=t.getTime4();
			if(time4!=""){
				time.add(time4.split("-")[0]);
				time.add(time4.split("-")[1]);
			}
			String time5=t.getTime5();
			if(time5!=""){
				time.add(time5.split("-")[0]);
				time.add(time5.split("-")[1]);
			}
			return contentTime(time);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
