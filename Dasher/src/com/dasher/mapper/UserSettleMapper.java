package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.UserSettle;

public interface UserSettleMapper {

	public int add(UserSettle us);
	public int update(UserSettle us);
	public int getCount(@Param(value="searchStr") String searchStr);
	public List<UserSettle> list(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<UserSettle> getListByWid(String wid);
	
}
