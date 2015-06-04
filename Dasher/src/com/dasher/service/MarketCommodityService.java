package com.dasher.service;

import java.util.List;

import com.dasher.model.MarketCommodity;

public interface MarketCommodityService {

	public boolean add(MarketCommodity mc);
	public boolean update(MarketCommodity mc);
	public boolean delete(MarketCommodity mc);
	public MarketCommodity getByMcid(String mcid);
	public int getListCount(String smid,String typeId, String searchStr);
	public List<MarketCommodity> list(String smid,String typeId, String searchStr, int startRow, int pageSize);

	public int getCountBySmid(String smid);
	public boolean deleteList(MarketCommodity mc);
}
