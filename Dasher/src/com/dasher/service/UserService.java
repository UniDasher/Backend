package com.dasher.service;

import java.util.List;

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
	public List<User> searchUser(int type,String searchStr,int startRow,int pageSize);
	public int getUserByStatus2(int type,String searchStr);
	public boolean updateLogo(User u);
}
