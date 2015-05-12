package com.dasher.mapper;

import java.util.List;

import com.dasher.model.UserAddress;

public interface UserAddressMapper {
	public int add(UserAddress ua);
	public int update(UserAddress ua);
	public int delete(UserAddress ua);
	public int updateStatus(UserAddress ua);
	public UserAddress getById(int id);
	public List<UserAddress> list(String uid);
	public UserAddress getByStatus();
}
