package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.ServerSettle;

public interface ServerSettleMapper {

	public int add(ServerSettle ss);

	public int getCount(@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,
			@Param(value="endDate") String endDate);

	public List<ServerSettle> list(@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,
			@Param(value="endDate") String endDate,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	
}
