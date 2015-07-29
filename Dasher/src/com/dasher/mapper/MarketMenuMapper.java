package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketMenu;

public interface MarketMenuMapper {

	public int add(MarketMenu mm);
	public int receive(MarketMenu mm);
	public int updateStatus(MarketMenu mm);
	public int getCount(@Param(value="status") String status,@Param(value="smid") String smid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate);
	public int getListByUidCount(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate")String startDate,@Param(value="endDate")String endDate);
	public int updateDate(MarketMenu mm);
	public int updateStatus2(MarketMenu mm);
	public MarketMenu getByMid(String mid);
	public List<MarketMenu> getNearlist(@Param(value="minlon")double minlon,@Param(value="maxlon")double maxlon,@Param(value="minlat")double minlat,@Param(value="maxlat")double maxlat);
    public List<MarketMenu> list(@Param(value="status") String status,@Param(value="smid") String smid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<MarketMenu> getListByUid(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate")String startDate,@Param(value="endDate")String endDate,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
    
	public List<MarketMenu> getNearListSmid(@Param(value="smid") String smid);
	public List<MarketMenu> getListOverTime(@Param(value="mealEndDate") String mealEndDate);
	public int updateOverTime(@Param(value="mealEndDate") String mealEndDate,@Param(value="updateDate") String updateDate);
	public List<MarketMenu> ListByUid(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="userType") int userType);
}
