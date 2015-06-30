package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.ShopMapper;
import com.dasher.model.Market;
import com.dasher.model.Shop;
import com.dasher.service.ShopService;

public class ShopServiceImpl implements ShopService {

	private ShopMapper shopMapper;

	public ShopMapper getShopMapper() {
		return shopMapper;
	}

	public void setShopMapper(ShopMapper shopMapper) {
		this.shopMapper = shopMapper;
	}

	public boolean add(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.add(s)>0? true:false;
	}

	public boolean delete(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.delete(s)>0? true:false;
	}

	public Shop getBySid(String sid) {
		// TODO Auto-generated method stub
		return shopMapper.getBySid(sid);
	}

	public boolean update(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.update(s)>0? true:false;
	}

	public Shop getByName(String name) {
		// TODO Auto-generated method stub
		return shopMapper.getByName(name);
	}

	public List<Shop> getListByLati(double longitude, double latitude,float distance) {
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		return shopMapper.getListByLati(minlon, maxlon, minlat, maxlat);
	}
	public List<Shop> getListByMenu(double longitude, double latitude,float distance) {
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		return shopMapper.getListByMenu(minlon, maxlon, minlat, maxlat);
	}

	public List<Shop> list(String searchStr, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return shopMapper.list(searchStr, startRow, pageSize);
	}

	public boolean updateLogo(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.updateLogo(s)>0? true:false;
	}

	public int getShopCount(String searchStr) {
		// TODO Auto-generated method stub
		return shopMapper.getShopCount(searchStr);
	}

	public List<Shop> menuList() {
		// TODO Auto-generated method stub
		return shopMapper.menuList();
	}
}
