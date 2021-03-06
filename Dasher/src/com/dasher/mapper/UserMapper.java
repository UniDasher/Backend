package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.User;

public interface UserMapper {

	public int addUser(User u);
	public int update(User u);
	public int delete(User u);
	public int updatePwd(User u);
	public int userApply(User u);
	public int getUserByStatus2(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate")String startDate,@Param(value="endDate")String endDate);
	public int updateLogo(User u);
	public int updateUserName(User u);
	public int updatePhone(User u);
	public int updateEmail(User u);
	public int updateBalance(@Param(value="uid") String uid,@Param(value="curUserBalance") float curUserBalance);
	public int forgetPwd(User u);
	public int balanceListCount(@Param(value="searchStr") String searchStr);
	public int serveApply(User u);
	public int cheakUser(User u);
	public User getByUId(String uid);
	public User getUserByTel(String mobilePhone);
	public List<User> searchUser(@Param(value="type") int type,@Param(value="searchStr") String searchStr,@Param(value="startDate")String startDate,@Param(value="endDate")String endDate,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<User> balanceList(@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public List<User> settleList(@Param(value="searchStr") String searchStr);
	public List<User> applyList();
	public int updateStatus(User u);
	public int updateEvaluate(User user);
	public int updateTrueName(User u);	
}
