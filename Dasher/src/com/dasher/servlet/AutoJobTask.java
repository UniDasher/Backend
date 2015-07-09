package com.dasher.servlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.dasher.service.MenuService;


public class AutoJobTask {
	@Autowired
	private MenuService menuService;
	
    public void doBiz() {
	  // 执行业务逻辑
	  // ........
    	System.out.print("asasas");
    	menuService.getListOverTime();
    }
}
