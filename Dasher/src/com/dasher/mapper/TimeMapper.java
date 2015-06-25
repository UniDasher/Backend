package com.dasher.mapper;

import java.util.List;
import com.dasher.model.Time;

public interface TimeMapper {
	public int add(Time t);
	public int update(Time t);
	public List<Time> getBySid(String sid);
}
