package com.dasher.service.impl;

import com.dasher.mapper.UserAddressMapper;
import com.dasher.model.UserAddress;
import com.dasher.service.UserAddressService;

public class UserAddressServiceImpl implements UserAddressService {

	private UserAddressMapper userAddressMapper;
	
	
	public UserAddressMapper getUserAddressMapper() {
		return userAddressMapper;
	}


	public void setUserAddressMapper(UserAddressMapper userAddressMapper) {
		this.userAddressMapper = userAddressMapper;
	}


	public boolean add(UserAddress ua) {
		// TODO Auto-generated method stub
		return userAddressMapper.add(ua)>0? true:false;
	}


	public boolean delete(UserAddress ua) {
		// TODO Auto-generated method stub
		return userAddressMapper.delete(ua)>0? true:false;
	}


	public boolean update(UserAddress ua) {
		// TODO Auto-generated method stub
		return userAddressMapper.update(ua)>0? true:false;
	}

}
