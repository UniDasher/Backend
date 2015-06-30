package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.EarningMapper;
import com.dasher.model.Earning;
import com.dasher.service.EarningService;

public class EarningServiceImpl implements EarningService {

	private EarningMapper earningMapper;
	
	public EarningMapper getEarningMapper() {
		return earningMapper;
	}

	public void setEarningMapper(EarningMapper earningMapper) {
		this.earningMapper = earningMapper;
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
