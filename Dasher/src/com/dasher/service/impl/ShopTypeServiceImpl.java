package com.dasher.service.impl;

import com.dasher.mapper.ShopTypeMapper;
import com.dasher.model.ShopType;
import com.dasher.service.ShopTypeService;

public class ShopTypeServiceImpl implements ShopTypeService {

	private ShopTypeMapper shopTypeMapper;

	public ShopTypeMapper getShopTypeMapper() {
		return shopTypeMapper;
	}

	public void setShopTypeMapper(ShopTypeMapper shopTypeMapper) {
		this.shopTypeMapper = shopTypeMapper;
	}



	public boolean add(ShopType st) {
		// TODO Auto-generated method stub
		return shopTypeMapper.add(st)>0? true:false;
	}

}
