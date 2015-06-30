package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Earning;

public interface EarningMapper {
	public int add(Earning e);
	public int delete(Earning e);
	public Earning getByEid(String eid);
	public List<Earning> getByWid(String wid);
	public List<Earning> listWeek(String wid);
	public List<Earning> listMonth(String wid);
	public List<Earning> listDay(@Param(value="wid")String wid,@Param(value="str")int str);
}
