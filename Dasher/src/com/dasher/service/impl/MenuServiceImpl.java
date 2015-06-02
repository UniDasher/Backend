package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.MenuMapper;
import com.dasher.model.Menu;
import com.dasher.service.MenuService;

public class MenuServiceImpl implements MenuService {

	private MenuMapper menuMapper;
	
	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	public boolean add(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.add(m)>0? true:false;
	}

	public boolean receive(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.receive(m)>0? true:false;
	}

	public boolean updateStatus(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.updateStatus(m)>0? true:false;
	}

	public List<Menu> list(String status, String sid, String searchStr,
			String startDate, String endDate, int curPage, int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.list(status, sid, searchStr, startDate, endDate, curPage, countPage);
	}

	public int getCount(String status, String sid, String searchStr,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return menuMapper.getCount(status, sid, searchStr, startDate, endDate);
	}

	public List<Menu> getListByUid(int type, String searchStr, int curPage,
			int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.getListByUid(type, searchStr, curPage, countPage);
	}

	public int getListByUidCount(int type, String searchStr) {
		// TODO Auto-generated method stub
		return menuMapper.getListByUidCount(type, searchStr);
	}

	public Menu getByMid(String mid) {
		// TODO Auto-generated method stub
		return menuMapper.getByMid(mid);
	}

	public int CountByStatus(String uid,String status) {
		// TODO Auto-generated method stub
		return menuMapper.CountByStatus(uid,status);
	}

	public List<Menu> listByStatus(String uid,String status, int curPage, int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.listByStatus(uid,status, curPage, countPage);
	}

	public boolean updateMealDate(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.updateMealDate(m)>0? true:false;
	}
	

}
