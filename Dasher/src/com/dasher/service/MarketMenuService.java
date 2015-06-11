package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketMenu;

public interface MarketMenuService {

	public int getCount(String status,String smid,String searchStr,String startDate,String endDate);
	public int getListByUidCount(int type,String searchStr);
	public boolean add(MarketMenu mm);
	public boolean receive(MarketMenu mm);
	public boolean updateStatus(MarketMenu mm);
	public boolean updateDate(MarketMenu mm);
	public List<MarketMenu> list(String status,String smid,String searchStr,String startDate,
			String endDate,int startRow,int pageSize);
	public MarketMenu getByMid(String mid);
    public List<MarketMenu> getListByUid(int type,String searchStr,int curPage,int countPage);
    public List<MarketMenu> ListByUid(String type,String searchStr);
    
}
