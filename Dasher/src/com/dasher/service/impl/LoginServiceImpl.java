package com.dasher.service.impl;

import java.util.UUID;

import com.dasher.mapper.LoginMapper;
import com.dasher.model.Login;
import com.dasher.service.LoginService;
import com.dasher.util.DateUtil;

public class LoginServiceImpl implements LoginService {

	private LoginMapper loginMapper;

	public LoginMapper getLoginMapper() {
		return loginMapper;
	}

	public void setLoginMapper(LoginMapper loginMapper) {
		this.loginMapper = loginMapper;
	}

	public boolean add(Login l) {
		// TODO Auto-generated method stub
		return loginMapper.add(l)>0? true:false;
	}

	public boolean update(Login l) {
		// TODO Auto-generated method stub
		return loginMapper.update(l)>0? true:false;
	}


	public Login getByLogId(String loginId) {
		// TODO Auto-generated method stub
		return loginMapper.getByLogId(loginId);
	}


	public String getByAuthCode(String authCode) {
		// TODO Auto-generated method stub
		return loginMapper.getByAuthCode(authCode);
	}

	public String handleLogin(String id){
		UUID uuid=UUID.randomUUID();
		String str[]=uuid.toString().split("-");
		String authCode="";
		for(int i=0;i<str.length;i++)
		{
			authCode=authCode+str[i];
		}
		Login l=new Login();
		l.setLoginId(id);
		l.setAuthCode(authCode);
		l.setType(0);
		l.setLoginTime(DateUtil.getCurrentDateStr());
		Login login=loginMapper.getByLogId(l.getLoginId());
		
		if(login==null)
		{
			loginMapper.add(l);
		}
		else
		{
			loginMapper.update(l);
		}
		
		return authCode;
	}

	public String userHandleLogin(String id) {
		// TODO Auto-generated method stub
		UUID uuid=UUID.randomUUID();
		String str[]=uuid.toString().split("-");
		String authCode="";
		for(int i=0;i<str.length;i++)
		{
			authCode=authCode+str[i];
		}
		Login l=new Login();
		l.setLoginId(id);
		l.setAuthCode(authCode);
		l.setType(1);
		l.setLoginTime(DateUtil.getCurrentDateStr());
		Login login=loginMapper.getByLogId(l.getLoginId());
		
		if(login==null)
		{
			loginMapper.add(l);
		}
		else
		{
			loginMapper.update(l);
		}
		return authCode;
	}
	public boolean updateCID(String uid, String cid) {
		return loginMapper.updateCID(uid,cid)>0?true:false;
	}
}
