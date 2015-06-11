package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MenuEvaluate;

public interface MenuEvaluateMapper {

	public int add(MenuEvaluate me);
	public int WaiterCount(String wid);
	public int ShopCount(String sid);
	public MenuEvaluate getEvalByMid(String mid);
	public List<MenuEvaluate> ListWaiter(@Param(value="wid")String wid,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<MenuEvaluate> ListShop(@Param(value="sid")String sid,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	
}
