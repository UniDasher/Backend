package com.dasher.service;

import com.dasher.model.Login;

public interface LoginService {
	
	public boolean add(Login l);
	public boolean update(Login l);
	public Login getByLogId(String loginId);
	public String getByAuthCode(String authCode);
	public String handleLogin(String id);
	public String userHandleLogin(String id);
	public boolean updateCID(String uid, String cid);
}
