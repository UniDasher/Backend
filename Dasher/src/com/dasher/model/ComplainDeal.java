package com.dasher.model;

import java.io.Serializable;

public class ComplainDeal implements Serializable {

	private int id;
	private String mobilePhone;
	private String phoneCode;
	private String createDate;
	private String updateDate;
	
	public ComplainDeal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public ComplainDeal(int id, String mobilePhone, String phoneCode,
			String createDate, String updateDate) {
		super();
		this.id = id;
		this.mobilePhone = mobilePhone;
		this.phoneCode = phoneCode;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	
}
