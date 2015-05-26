package com.dasher.service.impl;

import com.dasher.mapper.MarketMenuMapper;
import com.dasher.model.MarketMenu;
import com.dasher.service.MarketMenuService;

public class MarketMenuServiceImpl implements MarketMenuService {

	private MarketMenuMapper marketMenuMapper;
	
	public MarketMenuMapper getMarketMenuMapper() {
		return marketMenuMapper;
	}

	public void setMarketMenuMapper(MarketMenuMapper marketMenuMapper) {
		this.marketMenuMapper = marketMenuMapper;
	}

	public boolean add(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.add(mm)>0? true:false;
	}

	public boolean receive(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.receive(mm)>0? true:false;
	}

}
