package com.dasher.service;

import java.util.List;

import com.dasher.model.Market;
import com.dasher.model.Shop;

public interface ShopService {

	public int getShopCount(String searchStr);
	public boolean add(Shop s);
	public boolean update(Shop s);
	public boolean delete(Shop s);
	public boolean updateLogo(Shop s);
	public Shop getBySid(String sid);
	public Shop getByName(String name);
	public List<Shop> getListByLati(double longitude,double latitude,float distance);
	public List<Shop> getListByMenu(double longitude,double latitude,float distance, String uid);
	public List<Shop> list(String searchStr, int startRow,int pageSize);
	public List<Shop> menuList();
	public boolean updateEvaluate(Shop shop);
	
}
