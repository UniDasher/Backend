package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketCommodity;

public interface MarketCommodityMapper {

	public int add(MarketCommodity mc);
	public int update(MarketCommodity mc);
	public int delete(MarketCommodity mc);
	public MarketCommodity getByMcid(String mcid);
	public int getListCount(@Param(value="smid")String smid,@Param(value="searchStr") String searchStr);
	public List<MarketCommodity> list(@Param(value="smid")String smid,@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
   
	public int getCountBySmid(String smid);
	public int deleteList(MarketCommodity mc);
	

}
