package com.dasher.mapper;

import com.dasher.model.Time;

public interface TimeMapper {
	public int add(Time t);
	public int update(Time t);
	public Time getById(String id);
}
