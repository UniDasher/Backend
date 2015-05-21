package com.dasher.service;

import java.util.List;

import com.dasher.model.MenuDish;

public interface MenuDishService {

	public boolean add(MenuDish md);
	public List<MenuDish> getListByMid(String mid);
}
