package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.UserMapper;
import com.dasher.model.User;
import com.dasher.service.UserService;
import com.dasher.util.MyMD5Util;

public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public boolean addUser(User u) {
		// TODO Auto-generated method stub
		return userMapper.addUser(u)>0? true:false;
	}

	public boolean delete(User u) {
		// TODO Auto-generated method stub
		return userMapper.delete(u)>0? true:false;
	}

	public User getByUId(String uid) {
		// TODO Auto-generated method stub
		return userMapper.getByUId(uid);
	}

	public boolean update(User u) {
		// TODO Auto-generated method stub
		return userMapper.update(u)>0? true:false;
	}

	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		return userMapper.getUserByAccount(account);
	}

	public int userLoin(String account, String pwd) {
		// TODO Auto-generated method stub
		int flag=-1;
		User u=userMapper.getUserByAccount(account);
		if(u==null)
		   flag=1;
		else
		{
			try {
				if(!MyMD5Util.validPassword(pwd, u.getPassword()))
					flag=2;
				else
					flag=0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	
	public boolean updatePwd(User u) {
		// TODO Auto-generated method stub
		return userMapper.updatePwd(u)>0? true:false;
	}

	public int getPwd(String uid, String pwd) {
		// TODO Auto-generated method stub
		int flag=-1;
		User u=userMapper.getByUId(uid);
		if(u!=null)
		{
			try {
				if(!MyMD5Util.validPassword(pwd,u.getPassword()))
					flag=2;
				else
					flag=0;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			flag=1;
		
		return flag;
	}

	public boolean userApply(User u) {
		// TODO Auto-generated method stub
		return userMapper.userApply(u)>0? true:false;
	}

	public List<User> getUserByStatus(int status) {
		// TODO Auto-generated method stub
		return userMapper.getUserByStatus(status);
	}
	public List<User> searchUser(int type, String searchStr, int startRow,
			int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.searchUser(type, searchStr, startRow, pageSize);
	}
	public List<User> getUserByPage(int type, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.getUserByPage(type, startRow, pageSize);
	}

	public int getUserByStatus2(int type,String searchStr) {
		// TODO Auto-generated method stub
		return userMapper.getUserByStatus2(type,searchStr);
	}

	

	
	

}
