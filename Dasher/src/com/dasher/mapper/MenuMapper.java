package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Menu;

public interface MenuMapper {

	public int add(Menu m);
	public int receive(Menu m);
	public int updateStatus(Menu m);
	public int getCount(@Param(value="status") String status,@Param(value="sid") String sid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate);
	public List<Menu> list(@Param(value="status") String status,@Param(value="sid") String sid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
	public int getListByUidCount(@Param(value="type") int type,@Param(value="searchStr") String searchStr);
    public List<Menu> getListByUid(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
    public Menu getByMid(String mid);
    public int CountByStatus(@Param(value="uid") String uid,@Param(value="status") String status);
	public List<Menu> listByStatus(@Param(value="uid") String uid,@Param(value="status") String status,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
	public int updateMealDate(Menu m);
	public List<Menu> getListByStr(@Param(value="type") int type,@Param(value="uid") String uid);
	    
}
