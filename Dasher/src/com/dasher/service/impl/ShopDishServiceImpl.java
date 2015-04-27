package com.dasher.service.impl;

import com.dasher.mapper.ShopDishMapper;
import com.dasher.model.ShopDish;
import com.dasher.service.ShopDishService;

public class ShopDishServiceImpl implements ShopDishService {

	private ShopDishMapper shopDishMapper;
	
	
	public ShopDishMapper getShopDishMapper() {
		return shopDishMapper;
	}


	public void setShopDishMapper(ShopDishMapper shopDishMapper) {
		this.shopDishMapper = shopDishMapper;
	}


	public boolean add(ShopDish sd) {
		// TODO Auto-generated method stub
		return shopDishMapper.add(sd)>0? true:false;
	}

}
