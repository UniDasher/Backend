package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketMenu;

public interface MarketMenuMapper {

	public int add(MarketMenu mm);
	public int receive(MarketMenu mm);
	public int updateStatus(MarketMenu mm);
	public int getCount(@Param(value="status") String status,@Param(value="smid") String smid,
			@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,
			@Param(value="endDate") String endDate);
	public List<MarketMenu> list(@Param(value="status") String status,@Param(value="smid") String smid,
			@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,
			@Param(value="endDate") String endDate,@Param(value="startRow") int startRow,
			@Param(value="pageSize") int pageSize);
	public MarketMenu getByMid(String mid);
	public int getListByUidCount(@Param(value="type") int type,@Param(value="searchStr") String searchStr);
    public List<MarketMenu> getListByUid(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
 
}
