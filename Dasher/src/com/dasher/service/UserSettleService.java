package com.dasher.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dasher.model.UserSettle;

public interface UserSettleService {
	
	public boolean settleUser(HttpServletRequest request,String uid,String myloginId,String fileName);
	public boolean settleUserAll(HttpServletRequest request,String searchStr,String myloginId,String fileName);
	
	
	public boolean add(UserSettle us);
	public boolean update(UserSettle us);
	public int getCount(String searchStr);
	public List<UserSettle> list(String searchStr,int startRow,int pageSize);
	public List<UserSettle> getListByWid(String wid);
	
}
