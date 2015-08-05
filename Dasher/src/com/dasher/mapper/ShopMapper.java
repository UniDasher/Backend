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
	public Shop getBySid(@Param(value="sid") String sid,@Param(value="dw") int dw);
	public Shop getByName(@Param(value="name") String name);
	public List<Shop> list(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,
			@Param(value="pageSize") int pageSize,@Param(value="dw") int dw);
	public List<Shop> menuList();
	public List<Shop> getListByLati(@Param(value="minlon") double minlon,@Param(value="maxlon") double maxlon,
			@Param(value="minlat") double minlat,@Param(value="maxlat") double maxlat,@Param(value="dw") int dw);
	public List<Shop> getListByMenu(@Param(value="minlon") double minlon,@Param(value="maxlon") double maxlon,
			@Param(value="minlat") double minlat,@Param(value="maxlat") double maxlat,@Param(value="uid") String uid);
	public int updateEvaluate(Shop shop);
	public List<String> getShopType();
	public List<Shop> getListByLatiTest(@Param(value="dw") int dw);
	public List<Shop> getListByMenuTest(@Param(value="uid") String uid);
}
