package com.dasher.service;

import java.util.List;

import com.dasher.model.ShopDishType;

public interface ShopDishTypeService {

	public boolean add(ShopDishType sdt);
	public boolean update(ShopDishType sdt);
	public boolean delete(ShopDishType sdt);
	public boolean updateSortNum(ShopDishType sdt);
	public String getMax();
	public ShopDishType getById(int id);
	public ShopDishType getByName(String name);
	public List<ShopDishType> list(int type,String searchStr);
	public List<ShopDishType> listBySid(String sid);
	public List<ShopDishType> listBySmid(String smid);
}
