package com.dasher.model;

import java.io.Serializable;

public class UserAddress implements Serializable {

	private int id;
	private String uid;
	private String address;
	private String longitude;
	private String latitude;
	private int status;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	
	public UserAddress() {

	}

	public UserAddress(int id, String uid, String address, String longitude,
			String latitude, int status, String createBy, String createDate,
			String updateBy, String updateDate) {
		super();
		this.id = id;
		this.uid = uid;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
