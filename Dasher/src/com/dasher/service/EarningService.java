package com.dasher.service;

import java.util.List;

import com.dasher.model.Earning;
import com.dasher.model.Time;

public interface EarningService {
	public boolean add(Earning e);
	public boolean delete(Earning e);
	public Earning getByEid(String eid);
	public List<Earning> getByWid(String wid);
}
