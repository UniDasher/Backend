package com.dasher.service;

import java.util.List;

import com.dasher.model.MarketMenu;

public interface MarketMenuService {

	public int getCount(String status,String smid,String searchStr,String startDate,String endDate);
	public int getListByUidCount(int type,String searchStr,String startDate,String endDate);
	public boolean add(MarketMenu mm);
	public int receive(MarketMenu mm);
	public boolean updateStatus(MarketMenu mm);
	public boolean updateDate(MarketMenu mm);
	public boolean updateStatus2(MarketMenu mm);
	public MarketMenu getByMid(String mid);
	public List<MarketMenu> getNearList(double longitude,double latitude,float distance);
	public List<MarketMenu> list(String status,String smid,String searchStr,String startDate,String endDate,int startRow,int pageSize);
	public List<MarketMenu> getListByUid(int type,String searchStr,String startDate,String endDate,int curPage,int countPage);
	public List<MarketMenu> ListByUid(int type, String uid, int userType);
	public List<MarketMenu> getNearListSmid(String smid);
	public void getListOverTime();
	public boolean updateStatus_2(MarketMenu menu);
	public boolean menuComplete(String mid, int evalShop, int evalServer,String myloginId);
	
    
}
