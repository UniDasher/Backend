package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketMenu;
import com.dasher.model.Menu;

public interface MarketMenuService {

	public boolean add(MarketMenu mm);
	public boolean receive(MarketMenu mm);
	public boolean updateStatus(MarketMenu mm);
	public int getCount(String status,String smid,String searchStr);
	public List<MarketMenu> list(String status,String smid,String searchStr,int startRow,int pageSize);

}
