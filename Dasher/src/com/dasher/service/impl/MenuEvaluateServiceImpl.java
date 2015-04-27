package com.dasher.service.impl;

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

}
