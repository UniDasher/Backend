package com.dasher.mapper;

import com.dasher.model.UserAddress;

public interface UserAddressMapper {
	public int add(UserAddress ua);
	public int update(UserAddress ua);
	public int delete(UserAddress ua);
}
