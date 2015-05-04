package com.dasher.service;

import com.dasher.model.Complain;
import com.dasher.model.Login;

public interface LoginService {
	public boolean add(Login l);
	public boolean update(Login l);
	public Login getByLogId(String loginId);
	public void handleLogin(Login l);
	public String getByAuthCode(String authCode);
	public Login NewAuthCode(String id);
	public Login userNewAuthCode(String id);
}
