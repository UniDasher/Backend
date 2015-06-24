package com.dasher.model;

import java.io.Serializable;

public class Shop implements Serializable {

	private int id;
	private String sid;
	private String name;
	private String typeTab;
	private String address;
	private String subscribe;
	private String email;
	private String phone;
	private String logo;
	private String longitude;
	private String latitude;
	private int goodEvaluate;
	private int badEvaluate;
	private int createBy;
	private String createDate;
	private int updateBy;
	private String updateDate;
	private int status;
	private String serviceTimes;
	
	public Shop() {
		
	}

	public Shop(int id, String sid, String name, String typeTab,
			String address, String subscribe, String email, String phone,
			String logo, String longitude, String latitude, int goodEvaluate,
			int badEvaluate, int createBy, String createDate, int updateBy,
			String updateDate, int status, String serviceTimes) {
		super();
		this.id = id;
		this.sid = sid;
		this.name = name;
		this.typeTab = typeTab;
		this.address = address;
		this.subscribe = subscribe;
		this.email = email;
		this.phone = phone;
		this.logo = logo;
		this.longitude = longitude;
		this.latitude = latitude;
		this.goodEvaluate = goodEvaluate;
		this.badEvaluate = badEvaluate;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
		this.serviceTimes = serviceTimes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeTab() {
		return typeTab;
	}

	public void setTypeTab(String typeTab) {
		this.typeTab = typeTab;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getGoodEvaluate() {
		return goodEvaluate;
	}

	public void setGoodEvaluate(int goodEvaluate) {
		this.goodEvaluate = goodEvaluate;
	}

	public int getBadEvaluate() {
		return badEvaluate;
	}

	public void setBadEvaluate(int badEvaluate) {
		this.badEvaluate = badEvaluate;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(String serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	
}
