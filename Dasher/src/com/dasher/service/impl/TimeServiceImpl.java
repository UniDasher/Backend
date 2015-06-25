package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.TimeMapper;
import com.dasher.model.Time;
import com.dasher.service.TimeService;

public class TimeServiceImpl implements TimeService {

	private TimeMapper timeMapper;
	
	public TimeMapper getTimeMapper() {
		return timeMapper;
	}

	public void setTimeMapper(TimeMapper timeMapper) {
		this.timeMapper = timeMapper;
	}

	public boolean add(Time t) {
		// TODO Auto-generated method stub
		return timeMapper.add(t)>0? true:false;
	}

	public boolean update(Time t) {
		// TODO Auto-generated method stub
		return timeMapper.update(t)>0? true:false;
	}

	public Time getById(String id) {
		// TODO Auto-generated method stub
		return timeMapper.getById(id);
	}

	public Time getBySid(String sid) {
		// TODO Auto-generated method stub
		return timeMapper.getBySid(sid);
	}

	public boolean delete(Time t) {
		// TODO Auto-generated method stub
		return timeMapper.delete(t)>0? true:false;
	}

	public List<Time> list(String sid) {
		// TODO Auto-generated method stub
		return timeMapper.list(sid);
	}

}
