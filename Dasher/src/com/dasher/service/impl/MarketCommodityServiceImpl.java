package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.MarketCommodityMapper;
import com.dasher.model.MarketCommodity;
import com.dasher.service.MarketCommodityService;

public class MarketCommodityServiceImpl implements MarketCommodityService {

	private MarketCommodityMapper marketCommodityMapper;
	
	public MarketCommodityMapper getMarketCommodityMapper() {
		return marketCommodityMapper;
	}

	public void setMarketCommodityMapper(MarketCommodityMapper marketCommodityMapper) {
		this.marketCommodityMapper = marketCommodityMapper;
	}


	public boolean add(MarketCommodity mc) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.add(mc)>0? true:false;
	}

	public boolean delete(MarketCommodity mc) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.delete(mc)>0? true:false;
	}

	public MarketCommodity getByMcid(String mcid) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.getByMcid(mcid);
	}

	public boolean update(MarketCommodity mc) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.update(mc)>0? true:false;
	}

	public int getListCount(String smid,String typeId, String searchStr) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.getListCount(smid,typeId, searchStr);
	}

	public List<MarketCommodity> list(String smid,String typeId, String searchStr,
			int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.list(smid,typeId, searchStr, startRow, pageSize);
	}

	public boolean deleteList(MarketCommodity mc) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.deleteList(mc)>0? true:false;
	}

	public int getCountBySmid(String smid) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.getCountBySmid(smid);
	}

	public List<MarketCommodity> listBySmid(String smid) {
		// TODO Auto-generated method stub
		return marketCommodityMapper.listBySmid(smid);
	}

}
