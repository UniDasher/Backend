package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Shop;

public interface ShopService {

	public boolean add(Shop s);
	public Shop getBySid(String sid);
	public Shop getByName(String name);
	public boolean update(Shop s);
	public boolean delete(Shop s);
	public List<Shop> getListByLati(int longitude,int latitude,int startRow,int pageSize);
	public List<Shop> list(String searchStr, int startRow,int pageSize);
	public int getShopCount(String searchStr);
	public boolean updateLogo(Shop s);
}
