package com.dasher.mapper;

import java.util.List;

import com.dasher.model.Shop;

public interface ShopMapper {

	public int add(Shop s);
	public Shop getBySid(String sid);
	public Shop getByName(String name);
	public int update(Shop s);
	public int delete(Shop s);
	public List<Shop> getListByLati(int longitude,int latitude,int startRow,int pageSize);
	public List<Shop> list(String searchStr, int startRow,int pageSize);
}
