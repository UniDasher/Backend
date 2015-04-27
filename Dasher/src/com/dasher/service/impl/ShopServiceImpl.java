package com.dasher.service.impl;

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

}
