package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Market;

public interface MarketMapper {

	public int add(Market m);
	public int update(Market m);
	public int delete(Market m);
	public int getListCount(@Param(value="searchStr") String searchStr);
	public Market getBySmid(String smid);
	public Market getByMarketName(String name);
	public List<Market> list(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<Market> menuList();
	public List<Market> getNearlist(@Param(value="minlon")double minlon,@Param(value="maxlon")double maxlon,@Param(value="minlat")double minlat,@Param(value="maxlat")double maxlat);
	public List<Market> getNearListMenu(@Param(value="minlon")double minlon,@Param(value="maxlon")double maxlon,@Param(value="minlat")double minlat,@Param(value="maxlat")double maxlat);
   
}
