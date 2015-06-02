package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.MenuDishMapper;
import com.dasher.model.MenuDish;
import com.dasher.service.MenuDishService;

public class MenuDishServiceImpl implements MenuDishService {

	private MenuDishMapper menuDishMapper;
	
	public MenuDishMapper getMenuDishMapper() {
		return menuDishMapper;
	}

	public void setMenuDishMapper(MenuDishMapper menuDishMapper) {
		this.menuDishMapper = menuDishMapper;
	}

	public boolean add(MenuDish md) {
		// TODO Auto-generated method stub
		return menuDishMapper.add(md)>0? true:false;
	}

	public List<MenuDish> getListByMid(String mid) {
		// TODO Auto-generated method stub
		return menuDishMapper.getListByMid(mid);
	}
	
}
