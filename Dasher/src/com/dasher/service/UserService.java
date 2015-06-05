package com.dasher.service;

import java.util.List;

import com.dasher.model.User;

public interface UserService {

	public boolean addUser(User u);
	public User getByUId(String uid);
	public User getUserByTel(String mobilePhone);
	public boolean update(User u);
	public boolean delete(User u);
	public int userLoin(String mobilePhone,String pwd);
	public boolean updatePwd(User u);
	public int getPwd(String uid,String pwd);
	public boolean userApply(User u);
	public List<User> searchUser(int type,String searchStr,int startRow,int pageSize);
	public int getUserByStatus2(int type,String searchStr);
	public boolean updateLogo(User u);
	public boolean updateUserName(User u);
	public boolean updatePhone(User u);
	public boolean updateEmail(User u);
	public List<User> balanceList(int startRow,int pageSize);
	
}
