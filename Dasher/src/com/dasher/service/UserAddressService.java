package com.dasher.service;

import java.util.List;

import com.dasher.model.UserAddress;

public interface UserAddressService {
	
	public boolean add(UserAddress ua);
	public boolean update(UserAddress ua);
	public boolean delete(UserAddress ua);
	public boolean updateStatus(UserAddress ua);
	public UserAddress getById(int id);
	public List<UserAddress> list(String uid);
	public UserAddress getByStatus();
}
