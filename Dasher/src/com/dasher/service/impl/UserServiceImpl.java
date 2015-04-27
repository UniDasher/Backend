package com.dasher.service.impl;

import com.dasher.mapper.UserMapper;
import com.dasher.model.User;
import com.dasher.service.UserService;

public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public boolean addUser(User u) {
		// TODO Auto-generated method stub
		return userMapper.addUser(u)>0? true:false;
	}

	

}
