package com.dasher.model;

import java.io.Serializable;

public class UserAddress implements Serializable {

	private int id;
	private String uid;
	private String address;
	private String longitude;
	private String latitude;
	private int status;
	
	public UserAddress() {

	}

	public UserAddress(int id, String uid, String address, String longitude,
			String latitude, int status) {
		super();
		this.id = id;
		this.uid = uid;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
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
	
	
}
