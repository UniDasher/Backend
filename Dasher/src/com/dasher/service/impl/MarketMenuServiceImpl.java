package com.dasher.service.impl;

import java.util.List;

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

	public boolean updateStatus(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.updateStatus(mm)>0? true:false;
	}

	public int getCount(String status, String smid, String searchStr,String startDate,String endDate) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getCount(status, smid, searchStr,startDate,endDate);
	}

	public List<MarketMenu> list(String status, String smid, String searchStr,String startDate,String endDate,
			int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return marketMenuMapper.list(status, smid, searchStr,startDate,endDate, startRow, pageSize);
	}

	public MarketMenu getByMid(String mid) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getByMid(mid);
	}

	public List<MarketMenu> getListByUid(int type, String searchStr,
			int curPage, int countPage) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getListByUid(type, searchStr, curPage, countPage);
	}

	public int getListByUidCount(int type, String searchStr) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getListByUidCount(type, searchStr);
	}

	public List<MarketMenu> ListByUid(String type, String searchStr) {
		// TODO Auto-generated method stub
		return marketMenuMapper.ListByUid(type, searchStr);
	}

	public boolean updateDate(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.updateDate(mm)>0? true:false;
	}

}
