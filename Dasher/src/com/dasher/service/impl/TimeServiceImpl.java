package com.dasher.service.impl;

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

}
