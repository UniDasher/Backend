package com.dasher.model;

import java.io.Serializable;

public class Login implements Serializable {

	private int id;
	private String loginId;
	private String authCode;
	private int type;
	private String loginTime;
	
	public Login() {
		
	}

	public Login(int id, String loginId, String authCode, int type,
			String loginTime) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.authCode = authCode;
		this.type = type;
		this.loginTime = loginTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
