package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Shop;

public interface ShopMapper {

	public int add(Shop s);
	public int update(Shop s);
	public int delete(Shop s);
	public int getShopCount(@Param(value="searchStr") String searchStr);
	public int updateLogo(Shop s);
	public Shop getBySid(String sid);
	public Shop getByName(String name);
	public List<Shop> getListByLati(@Param(value="longitude")int longitude,@Param(value="latitude") int latitude,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<Shop> list(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<Shop> menuList();
}
