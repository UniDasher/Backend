package com.dasher.model;

import java.io.Serializable;

public class Login implements Serializable {

	private int id;
	private int type;
	private String loginId;
	private String authCode;
	private String igtClientId;
	private String pushClientId;
	private String loginTime;
	
	public Login() {
		super();
	}
	
	public Login(int id, int type, String loginId, String authCode,
			String igtClientId, String pushClientId, String loginTime) {
		super();
		this.id = id;
		this.type = type;
		this.loginId = loginId;
		this.authCode = authCode;
		this.igtClientId = igtClientId;
		this.pushClientId = pushClientId;
		this.loginTime = loginTime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getIgtClientId() {
		return igtClientId;
	}
	public void setIgtClientId(String igtClientId) {
		this.igtClientId = igtClientId;
	}
	public String getPushClientId() {
		return pushClientId;
	}
	public void setPushClientId(String pushClientId) {
		this.pushClientId = pushClientId;
	}
	
}
