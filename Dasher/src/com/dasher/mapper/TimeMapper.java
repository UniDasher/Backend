package com.dasher.mapper;

import java.util.List;
import com.dasher.model.Time;

public interface TimeMapper {
	public int add(Time t);
	public int update(Time t);
	public int delete(Time t);
	public Time getById(String id);
	public Time getBySid(String sid);
	public List<Time> list(String sid);
}
