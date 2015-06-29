package com.dasher.mapper;

import java.util.List;

import com.dasher.model.Earning;

public interface EarningMapper {
	public int add(Earning e);
	public int delete(Earning e);
	public Earning getByEid(String eid);
	public List<Earning> getByWid(String wid);
}
