package com.dasher.service.impl;

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

}
