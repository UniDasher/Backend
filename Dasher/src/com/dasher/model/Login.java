package com.dasher.model;

import java.io.Serializable;

public class Login implements Serializable {

	private int id;
	private int type;
	private String cid;
	private String loginId;
	private String authCode;
	private String loginTime;
	
	public Login() {
		super();
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
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
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
	public Login(int id, int type, String cid, String loginId, String authCode,
			String loginTime) {
		super();
		this.id = id;
		this.type = type;
		this.cid = cid;
		this.loginId = loginId;
		this.authCode = authCode;
		this.loginTime = loginTime;
	}
}
