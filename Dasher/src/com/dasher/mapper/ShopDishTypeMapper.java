package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.ShopDishType;

public interface ShopDishTypeMapper {

	public int add(ShopDishType sdt);
	public int update(ShopDishType sdt);
	public int delete(ShopDishType sdt);
	public ShopDishType getById(int id);
	public ShopDishType getByName(String name);
	public List<ShopDishType> list();
	public String getMax();
	public int updateSortNum(ShopDishType sdt);
	public List<ShopDishType> listBySid(@Param(value="sid") String sid);
	
}
