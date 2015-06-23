package com.dasher.service.impl;

import java.util.List;

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


	public boolean delete(ShopDishType sdt) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.delete(sdt)>0? true:false;
	}


	public ShopDishType getById(int id) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.getById(id);
	}


	public boolean update(ShopDishType sdt) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.update(sdt)>0? true:false;
	}


	public ShopDishType getByName(String name) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.getByName(name);
	}


	public List<ShopDishType> list(int type) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.list(type);
	}


	public String getMax() {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.getMax();
	}

	public boolean updateSortNum(ShopDishType sdt) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.updateSortNum(sdt)>0? true:false;
	}


	public List<ShopDishType> listBySid(String sid) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.listBySid(sid);
	}


	public List<ShopDishType> listBySmid(String smid) {
		// TODO Auto-generated method stub
		return shopDishTypeMapper.listBySmid(smid);
	}

}
