package com.dasher.service.impl;

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

}
