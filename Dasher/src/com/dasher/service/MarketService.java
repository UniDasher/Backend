package com.dasher.service;

import java.util.List;

import com.dasher.model.Market;
import com.dasher.model.Menu;

public interface MarketService {

	public int getListCount(String searchStr);
	public boolean add(Market m);
	public boolean update(Market m);
	public boolean delete(Market m);
	public Market getBySmid(String smid);
	public Market getByMarketName(String name);
	public List<Market> list(String searchStr,int startRow,int pageSize);
	public List<Market> menuList();
	public List<Market> getNearList(float longitude,float latitude,float distance);
}
