package com.dasher.service.impl;

import com.dasher.mapper.MarketMapper;
import com.dasher.model.Market;
import com.dasher.service.MarketService;

public class MarketServiceImpl implements MarketService {

	private MarketMapper marketMapper;
	
	
	public MarketMapper getMarketMapper() {
		return marketMapper;
	}

	public void setMarketMapper(MarketMapper marketMapper) {
		this.marketMapper = marketMapper;
	}

	
	public boolean add(Market m) {
		// TODO Auto-generated method stub
		return marketMapper.add(m)>0? true:false;
	}

}
