package com.dasher.mapper;

import java.util.List;

import com.dasher.model.MenuDish;

public interface MenuDishMapper {

	public int add(MenuDish md);
	public List<MenuDish> getListByMid(String mid);
}
