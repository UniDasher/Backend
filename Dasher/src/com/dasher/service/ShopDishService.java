package com.dasher.service;

import java.util.List;

import com.dasher.model.ShopDish;

public interface ShopDishService {
	
	public boolean add(ShopDish sd);
	public boolean update(ShopDish sd);
	public boolean delete(ShopDish sd);
	public ShopDish getByDid(String did);
	public int getShopDishCount(String sid,String typeId,String searchStr);
	public List<ShopDish> list(String sid,String typeId,String searchStr,int startRow,int pageSize);
	public boolean deleteList(ShopDish sd);
	public int getCountBySid(String sid);
	public List<ShopDish> listBySid(String sid);
}
