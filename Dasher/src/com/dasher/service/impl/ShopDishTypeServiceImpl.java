package com.dasher.service.impl;

import com.dasher.mapper.ShopDishTypeMapper;
import com.dasher.model.ShopDishType;
import com.dasher.service.ShopDishTypeService;

public class ShopDishTypeServiceImpl implements ShopDishTypeService {

	private ShopDishTypeMapper shopDishTypeMapper;
	
	public ShopDishTypeMapper getShopDishTypeMapper() {
		return shopDishTypeMapper;
	}


	public void setShopDishTypeMapper(ShopDishTypeMapper shopDishTypeMapper) {
		this.shopDishTypeMapper = shopDishTypeMapper;
	}


	public boolean add(ShopDishType sdt) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.add(sdt)>0? true:false;
	}

}
