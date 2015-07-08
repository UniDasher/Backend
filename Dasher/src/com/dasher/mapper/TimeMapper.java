package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Time;

public interface TimeMapper {
	public int add(Time t);
	public int update(Time t);
	public List<Time> getBySid(@Param(value="sid") String sid);
	public Time getCurTimeBySId(@Param(value="sid") String sid,@Param(value="dw") int dw);
}
