package com.dasher.service;

import java.util.List;

import com.dasher.model.MarketCommodity;

public interface MarketCommodityService {
	
	public int getListCount(String smid,String typeId, String searchStr);
	public int getCountBySmid(String smid);
	public boolean add(MarketCommodity mc);
	public boolean update(MarketCommodity mc);
	public boolean delete(MarketCommodity mc);
	public boolean deleteList(MarketCommodity mc);
	public MarketCommodity getByMcid(String mcid);
	public List<MarketCommodity> list(String smid,String typeId, String searchStr, int startRow, int pageSize);
    public List<MarketCommodity> listBySmid(String smid);
}
