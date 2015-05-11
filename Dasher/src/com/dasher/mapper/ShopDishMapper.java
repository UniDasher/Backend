package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.ShopDish;

public interface ShopDishMapper {
	public int add(ShopDish sd);
	public int update(ShopDish sd);
	public int delete(ShopDish sd);
	public ShopDish getByDid(String did);
	public List<ShopDish> list(@Param(value="sid") String sid,@Param(value="typeId") String typeId,@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
}
