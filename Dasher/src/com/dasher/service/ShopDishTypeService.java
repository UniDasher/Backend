package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.ShopDishType;

public interface ShopDishTypeService {

	public boolean add(ShopDishType sdt);
	public boolean update(ShopDishType sdt);
	public boolean delete(ShopDishType sdt);
	public ShopDishType getById(int id);
	public ShopDishType getByName(String name);
	public List<ShopDishType> list(int type);
	public String getMax();
	public boolean updateSortNum(ShopDishType sdt);
	public List<ShopDishType> listBySid(String sid);
	public List<ShopDishType> listBySmid(String smid);
}
