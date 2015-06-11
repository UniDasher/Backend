package com.dasher.service;

import java.util.List;

import com.dasher.model.ShopDish;

public interface ShopDishService {

	public int getShopDishCount(String sid,String typeId,String searchStr);
	public int getCountBySid(String sid);
	public boolean add(ShopDish sd);
	public boolean update(ShopDish sd);
	public boolean delete(ShopDish sd);
	public boolean deleteList(ShopDish sd);
	public ShopDish getByDid(String did);
	public List<ShopDish> list(String sid,String typeId,String searchStr,int startRow,int pageSize);
	public List<ShopDish> listBySid(String sid);
}
