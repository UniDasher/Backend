package com.dasher.service;

import com.dasher.model.User;

public interface UserService {

	public boolean addUser(User u);
	public User getByUId(String uid);
	public User getUserByAccount(String account);
	public boolean update(User u);
	public boolean delete(User u);
	public int userLoin(String account,String pwd);
	public boolean updatePwd(User u);
	public int getPwd(String uid,String pwd);
	public boolean userApply(User u);
}
