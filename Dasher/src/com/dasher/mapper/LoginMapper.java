package com.dasher.mapper;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Login;

public interface LoginMapper {
	public int add(Login l);
	public int update(Login l);
	public int updateByLoid(Login l);
	public int updateCID(Login l);
	public Login getByLogId(@Param(value="loginId") String loginId);
	public String getByAuthCode(@Param(value="authCode") String authCode);
	public int userLogout(@Param(value="loginId") String loginId);
	public int updateUUID(Login l);
}
