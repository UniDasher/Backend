package com.dasher.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Earning;

public interface EarningService {
	public boolean add(Earning e);
	public boolean delete(Earning e);
	public Earning getByEid(String eid);
	public List<Earning> getByWid(String wid);
	public List<Earning> listMonth(String wid);
	public List<Earning> listWeek(String wid);
	public List<Earning> listDay(String wid,int str);
	public Map<String, Object> getEarnTotal(String uid);
	public List<Map<String, Object>> getEarnWeek(String uid);
	public List<Earning> getEarnList(String myloginId, String startDate,
			String endDate);

}
