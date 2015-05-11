package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.ShopDish;

public interface ShopDishService {
	
	public boolean add(ShopDish sd);
	public boolean update(ShopDish sd);
	public boolean delete(ShopDish sd);
	public ShopDish getByDid(String did);
	public List<ShopDish> list(String sid,String typeId,String searchStr,int startRow,int pageSize);

}
