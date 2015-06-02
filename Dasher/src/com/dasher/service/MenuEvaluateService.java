package com.dasher.service;

import java.util.List;

import com.dasher.model.MenuEvaluate;

public interface MenuEvaluateService {

	public boolean add(MenuEvaluate me);
	public MenuEvaluate getEvalByMid(String mid);
	public List<MenuEvaluate> ListWaiter(String wid,int startRow,int pageSize);
	public int WaiterCount(String wid);
	public List<MenuEvaluate> ListShop(String sid,int startRow,int pageSize);
	public int ShopCount(String sid);
}
