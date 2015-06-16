package com.dasher.service;

import com.dasher.model.Login;

public interface LoginService {
	
	public boolean add(Login l);
	public boolean update(Login l);
	public boolean updateByLoid(Login l);
	public boolean updateCID(Login l);
	public String getByAuthCode(String authCode);
	public String handleLogin(String id);
	public String userHandleLogin(String id);
	public Login getByLogId(String loginId);
}
