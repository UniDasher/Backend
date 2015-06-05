package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Market;

public interface MarketMapper {

	public int add(Market m);
	public int update(Market m);
	public int delete(Market m);
	public Market getBySmid(String smid);
	public Market getByMarketName(String name);
	public int getListCount(@Param(value="searchStr") String searchStr);
	public List<Market> list(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<Market> menuList();

}
