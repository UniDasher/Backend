package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.UserSettleMapper;
import com.dasher.model.UserSettle;
import com.dasher.service.UserSettleService;

public class UserSettleServiceImpl implements UserSettleService {

	private UserSettleMapper userSettleMapper;
	
	
	public UserSettleMapper getUserSettleMapper() {
		return userSettleMapper;
	}


	public void setUserSettleMapper(UserSettleMapper userSettleMapper) {
		this.userSettleMapper = userSettleMapper;
	}


	public boolean add(UserSettle us) {
		// TODO Auto-generated method stub
		return userSettleMapper.add(us)>0? true:false;
	}


	public boolean update(UserSettle us) {
		// TODO Auto-generated method stub
		return userSettleMapper.update(us)>0? true:false;
	}


	public int getCount(String searchStr) {
		// TODO Auto-generated method stub
		return userSettleMapper.getCount(searchStr);
	}


	public List<UserSettle> list(String searchStr, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return userSettleMapper.list(searchStr, startRow, pageSize);
	}


	public List<UserSettle> getListByWid(String wid) {
		// TODO Auto-generated method stub
		return userSettleMapper.getListByWid(wid);
	}

}
