package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.ShopMapper;
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

	public List<Shop> getListByLati(int longitude, int latitude,
			int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return shopMapper.getListByLati(longitude, latitude, startRow, pageSize);
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

}
