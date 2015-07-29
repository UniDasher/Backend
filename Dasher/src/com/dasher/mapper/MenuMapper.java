package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Menu;

public interface MenuMapper {

	public int add(Menu m);
	public int receive(Menu m);
	public int updateStatus(Menu m);
	public int getCount(@Param(value="status") String status,@Param(value="sid") String sid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate);
	public int getListByUidCount(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate);
	public int CountByStatus(@Param(value="uid") String uid,@Param(value="status") String status);
	public int updateMealDate(Menu m);
	public int updateOverTime(@Param(value="mealEndDate") String mealEndDate,@Param(value="updateDate") String updateDate);
    public int updateStatus2(Menu m);
	public Menu getByMid(String mid);
	public List<Menu> list(@Param(value="status") String status,@Param(value="sid") String sid,@Param(value="searchStr") String searchStr,@Param(value="startDate") String startDate,@Param(value="endDate") String endDate,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
	public List<Menu> getListByUid(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate")String startDate,@Param(value="endDate")String endDate,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
    public List<Menu> listByStatus(@Param(value="uid") String uid,@Param(value="status") String status,@Param(value="curPage") int curPage,@Param(value="countPage") int countPage);
	public List<Menu> getListByStr(@Param(value="type") int type,@Param(value="uid") String uid,@Param(value="userType") int userType);
	public List<Menu> getNearlist(@Param(value="minlon") double minlon,@Param(value="maxlon")double maxlon,@Param(value="minlat")double minlat,@Param(value="maxlat")double maxlat);
	public List<Menu> getListOverTime(@Param(value="mealEndDate") String mealEndDate);
	public List<Menu> getNearListBySid(@Param(value="sid") String sid,@Param(value="uid") String uid);
	public List<Menu> getOverList();
}
