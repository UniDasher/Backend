package com.dasher.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dasher.mapper.EarningMapper;
import com.dasher.model.Earning;
import com.dasher.service.EarningService;
import com.dasher.util.DateUtil;

public class EarningServiceImpl implements EarningService {

	private EarningMapper earningMapper;
	
	public EarningMapper getEarningMapper() {
		return earningMapper;
	}

	public void setEarningMapper(EarningMapper earningMapper) {
		this.earningMapper = earningMapper;
	}
	
	//获取送餐人的总收益
	public Map<String, Object> getEarnTotal(String wid) {
		SimpleDateFormat sdfLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfShort=new SimpleDateFormat("yyyy-MM-dd");
		//获取当前的时间
		Calendar c = Calendar.getInstance();
		Date endDate=c.getTime();
		//获取本周第一天
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(sdfLong.parse(sdfShort.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date startDate=c.getTime();
        //1表示type=2，0表示没有条件type
		Map<String, Object> map=earningMapper.getEarnTotal(wid,sdfLong.format(startDate),sdfLong.format(endDate),1);
		map=(map==null)?earningMapper.getEarnTotal(wid,sdfLong.format(startDate),sdfLong.format(endDate),0):map;
		if(map==null){
			map=new HashMap<String, Object>();
			map.put("weekEarn", 0);
			map.put("totalEarn", 0);
			map.put("totalMoney", 0);
			map.put("balance", 0);
			map.put("settleDate", DateUtil.getCurrentDateStr());
		}else if(!map.containsKey("settleDate")){
			map.put("settleDate", DateUtil.getCurrentDateStr());
		}
		
		return map;
	}
	
	//获取用户最近一周的收益
	public List<Map<String, Object>> getEarnWeek(String wid) {
		SimpleDateFormat sdfLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfShort=new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		//获取当前的时间
		Calendar c = Calendar.getInstance();
		Date endDate=c.getTime();
		for(int i=0;i<7;i++){
			try {
	            c.add(Calendar.DATE,i==0?0:-1);
	            c.setTime(sdfLong.parse(sdfShort.format(c.getTime()) + " 00:00:00"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        Date startDate=c.getTime();
	        Map<String, Object> map=earningMapper.getEarnWeek(wid,sdfLong.format(startDate),sdfLong.format(endDate));
	        if(map==null){
	        	map=new HashMap<String, Object>();
	        	map.put("carriageMoney", 0);
	        	map.put("dishMoney", 0);
	        }
	        map.put("date", sdfShort.format(startDate));
	        list.add(map);
			endDate=startDate;
		}
		return list;
	}
	
	public List<Earning> getEarnList(String wid, String startDate,
			String endDate) {
		SimpleDateFormat sdfLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startDate=startDate+" 00:00:00";
	    endDate=endDate+" 24:00:00";
		try {
			startDate=sdfLong.format(sdfLong.parse(startDate));
			endDate=sdfLong.format(sdfLong.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return earningMapper.getEarnList(wid,startDate,endDate);
	}
	
	public boolean add(Earning e) {
		// TODO Auto-generated method stub
		return earningMapper.add(e)>0? true:false;
	}

	public boolean delete(Earning e) {
		// TODO Auto-generated method stub
		return earningMapper.delete(e)>0? true:false;
	}

	public List<Earning> getByWid(String wid) {
		// TODO Auto-generated method stub
		return earningMapper.getByWid(wid);
	}

	public Earning getByEid(String eid) {
		// TODO Auto-generated method stub
		return earningMapper.getByEid(eid);
	}

	public List<Earning> listWeek(String wid) {
		// TODO Auto-generated method stub
		return earningMapper.listWeek(wid);
	}

	public List<Earning> listDay(String wid, int str) {
		// TODO Auto-generated method stub
		return earningMapper.listDay(wid, str);
	}

	public List<Earning> listMonth(String wid) {
		// TODO Auto-generated method stub
		return earningMapper.listMonth(wid);
	}
	
}
