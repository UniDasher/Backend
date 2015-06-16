package com.dasher.mapper;

import com.dasher.model.Login;

public interface LoginMapper {
	public int add(Login l);
	public int update(Login l);
	public int updateByLoid(Login l);
	public int updateCID(Login l);
	public Login getByLogId(String loginId);
	public String getByAuthCode(String authCode);
}
