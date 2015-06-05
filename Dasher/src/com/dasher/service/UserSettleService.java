package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.UserSettle;

public interface UserSettleService {

	public boolean add(UserSettle us);
	public boolean update(UserSettle us);
	public int getCount(String searchStr);
	public List<UserSettle> list(String searchStr,int startRow,int pageSize);
	public List<UserSettle> getListByWid(String wid);
}
