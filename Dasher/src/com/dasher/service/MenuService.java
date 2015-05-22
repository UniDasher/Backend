package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Menu;

public interface MenuService {

	public boolean add(Menu m);
	public boolean receive(Menu m);
	public boolean updateStatus(Menu m);
	public int getCount(String status, String sid, String searchStr, String startDate, String endDate);
	public List<Menu> list(String status, String sid, String searchStr, String startDate, String endDate,int curPage, int countPage);
	public int getListByUidCount(int type,String searchStr);
    public List<Menu> getListByUid(int type,String searchStr,int curPage, int countPage);
    public Menu getByMid(String mid);
    public int CountByStatus(String uid,String status);
	public List<Menu> listByStatus(String uid,String status, int curPage, int countPage);
	public boolean updateMealDate(Menu m);
}
