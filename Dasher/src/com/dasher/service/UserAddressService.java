package com.dasher.service;

import com.dasher.model.UserAddress;

public interface UserAddressService {
	public boolean add(UserAddress ua);
	public boolean update(UserAddress ua);
	public boolean delete(UserAddress ua);
}
