package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.MarketMenuMapper;
import com.dasher.model.MarketMenu;
import com.dasher.model.Menu;
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

	public boolean updateStatus(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.updateStatus(mm)>0? true:false;
	}

	public int getCount(String status, String smid, String searchStr) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getCount(status, smid, searchStr);
	}

	public List<MarketMenu> list(String status, String smid, String searchStr,
			int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return marketMenuMapper.list(status, smid, searchStr, startRow, pageSize);
	}

}
