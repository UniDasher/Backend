package com.dasher.mapper;

import java.util.List;

import com.dasher.model.User;

public interface UserMapper {

	public int addUser(User u);
	public User getByUId(String uid);
	public User getUserByAccount(String account);
	public int update(User u);
	public int delete(User u);
	public int updatePwd(User u);
	public int userApply(User u);
	public List<User> getUserByStatus(int status);
	public List<User> getUserByPage(int type,int startRow,int pageSize);
	public List<User> searchUser(int type,String searchStr,int startRow,int pageSize);
	public int getUserByStatus2(int type,String searchStr);
	
}
