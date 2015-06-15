package com.dasher.mapper;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Login;

public interface LoginMapper {
	public int add(Login l);
	public int update(Login l);
	public Login getByLogId(String loginId);
	public String getByAuthCode(String authCode);
	public int updateCID(@Param(value="uid") String uid,@Param(value="cid") String cid);
}
