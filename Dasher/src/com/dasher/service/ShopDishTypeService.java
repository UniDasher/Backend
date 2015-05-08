package com.dasher.service;

import java.util.List;

import com.dasher.model.ShopDishType;

public interface ShopDishTypeService {

	public boolean add(ShopDishType sdt);
	public boolean update(ShopDishType sdt);
	public boolean delete(ShopDishType sdt);
	public ShopDishType getById(int id);
	public ShopDishType getByName(String name);
	public List<ShopDishType> list();
	public String getMax();
	public boolean updateSortNum(ShopDishType sdt);
}
