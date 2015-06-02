package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.MenuEvaluateMapper;
import com.dasher.model.MenuEvaluate;
import com.dasher.service.MenuEvaluateService;

public class MenuEvaluateServiceImpl implements MenuEvaluateService {

	private MenuEvaluateMapper menuEvaluateMapper;
	
	public MenuEvaluateMapper getMenuEvaluateMapper() {
		return menuEvaluateMapper;
	}

	public void setMenuEvaluateMapper(MenuEvaluateMapper menuEvaluateMapper) {
		this.menuEvaluateMapper = menuEvaluateMapper;
	}

	public boolean add(MenuEvaluate me) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.add(me)>0? true:false;
	}

	public MenuEvaluate getEvalByMid(String mid) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.getEvalByMid(mid);
	}

	public List<MenuEvaluate> ListShop(String sid, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.ListShop(sid, startRow, pageSize);
	}

	public List<MenuEvaluate> ListWaiter(String wid, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.ListWaiter(wid, startRow, pageSize);
	}

	public int ShopCount(String sid) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.ShopCount(sid);
	}

	public int WaiterCount(String wid) {
		// TODO Auto-generated method stub
		return menuEvaluateMapper.WaiterCount(wid);
	}

}
