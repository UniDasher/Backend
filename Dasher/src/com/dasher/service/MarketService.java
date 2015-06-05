package com.dasher.service;

import java.util.List;

import com.dasher.model.Market;

public interface MarketService {

	public boolean add(Market m);
	public boolean update(Market m);
	public boolean delete(Market m);
	public Market getBySmid(String smid);
	public Market getByMarketName(String name);
	public int getListCount(String searchStr);
	public List<Market> list(String searchStr,int startRow,int pageSize);
	public List<Market> menuList();
}
