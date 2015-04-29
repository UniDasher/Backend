package com.dasher.mapper;

import com.dasher.model.User;

public interface UserMapper {

	public int addUser(User u);
	public User getByUId(String uid);
	public User getUserByAccount(String account);
	public int update(User u);
	public int delete(User u);
	public int updatePwd(User u);
	public int userApply(User u);
}
