package com.dasher.service.impl;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	public boolean delete(ShopDish sd) {
		// TODO Auto-generated method stub
		return shopDishMapper.delete(sd)>0? true:false;
	}

	public boolean update(ShopDish sd) {
		// TODO Auto-generated method stub
		return shopDishMapper.update(sd)>0? true:false;
	}

	public ShopDish getByDid(String did) {
		// TODO Auto-generated method stub
		return shopDishMapper.getByDid(did);
	}

	public List<ShopDish> list(String sid,String typeId,String searchStr, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return shopDishMapper.list(sid, typeId, searchStr, startRow, pageSize);
	}

}
