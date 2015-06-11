package com.dasher.service;

import java.util.List;

import com.dasher.model.MenuEvaluate;

public interface MenuEvaluateService {

	public int WaiterCount(String wid);
	public int ShopCount(String sid);
	public boolean add(MenuEvaluate me);
	public MenuEvaluate getEvalByMid(String mid);
	public List<MenuEvaluate> ListWaiter(String wid,int startRow,int pageSize);
	public List<MenuEvaluate> ListShop(String sid,int startRow,int pageSize);
	
}
