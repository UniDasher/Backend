package com.dasher.service;

import java.util.List;

import com.dasher.model.User;

public interface UserService {
	
	public int userLoin(String mobilePhone,String pwd);
	public int getPwd(String uid,String pwd);
	public int getUserByStatus2(int type,String searchStr);
	public int balanceListCount(String searchStr);
	public boolean addUser(User u);
	public boolean update(User u);
	public boolean delete(User u);
	public boolean updatePwd(User u);
	public boolean userApply(User u);
	public boolean updateLogo(User u);
	public boolean updateUserName(User u);
	public boolean updatePhone(User u);
	public boolean updateEmail(User u);
	public boolean updateBalance(String uid, float curUserBalance);
	public boolean forgetPwd(User u);
	public boolean serveApply(User u);
	public boolean cheakUser(User u);
	public User getByUId(String uid);
	public User getUserByTel(String mobilePhone);
	public List<User> searchUser(int type,String searchStr,int startRow,int pageSize);
	public List<User> balanceList(String searchStr,int startRow,int pageSize);
	public List<User> settleList(String searchStr);
	public List<User> applyList();
	public boolean updateStatus(User u);
	public boolean updateEvaluate(User user);
	public boolean updateTrueName(User u);
}
