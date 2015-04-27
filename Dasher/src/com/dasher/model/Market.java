package com.dasher.model;

import java.io.Serializable;

public class Market implements Serializable {

	private int id;
	private String smid;
	private String name;
	private String address;
	private String subscribe;
	private String email;
	private String phone;
	private String serviceTime;
	private String longitude;
	private String latitude;
	private int goodEvaluate;
	private int badEvaluate;
	private int status;
	
	public Market() {
		
	}

	public Market(int id, String smid, String name, String address,
			String subscribe, String email, String phone, String serviceTime,
			String longitude, String latitude, int goodEvaluate,
			int badEvaluate, int status) {
		super();
		this.id = id;
		this.smid = smid;
		this.name = name;
		this.address = address;
		this.subscribe = subscribe;
		this.email = email;
		this.phone = phone;
		this.serviceTime = serviceTime;
		this.longitude = longitude;
		this.latitude = latitude;
		this.goodEvaluate = goodEvaluate;
		this.badEvaluate = badEvaluate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
