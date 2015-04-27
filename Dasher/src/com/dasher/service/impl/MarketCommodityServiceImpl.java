package com.dasher.service.impl;

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

}
