package com.dasher.service.impl;

import java.util.List;

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

	public boolean delete(Market m) {
		// TODO Auto-generated method stub
		return marketMapper.delete(m)>0? true:false;
	}

	public Market getBySmid(String smid) {
		// TODO Auto-generated method stub
		return marketMapper.getBySmid(smid);
	}

	public boolean update(Market m) {
		// TODO Auto-generated method stub
		return marketMapper.update(m)>0? true:false;
	}

	public Market getByMarketName(String name) {
		// TODO Auto-generated method stub
		return marketMapper.getByMarketName(name);
	}

	public int getListCount(String searchStr) {
		// TODO Auto-generated method stub
		return marketMapper.getListCount(searchStr);
	}

	public List<Market> list(String searchStr, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return marketMapper.list(searchStr, startRow, pageSize);
	}

	public List<Market> menuList() {
		// TODO Auto-generated method stub
		return marketMapper.menuList();
	}

	public List<Market> getNearList(float longitude, float latitude,
			float distance) {
		// TODO Auto-generated method stub
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		List<Market> list=marketMapper.getNearlist(minlon, maxlon, minlat, maxlat);
		return list;
	}
}
