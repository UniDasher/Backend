package com.dasher.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private int id;
	private String mid;
	private String sid;
	private String uid;
	private String wid;
	private String did;
	private float dishsMoney;
	private float carriageMoney;
	private float taxesMoney;
	private float serviceMoney;
	private float tipMoney;
	private int menuCount;
	private int payType;
	private String mealStartDate;
	private String mealEndDate;
	private String address;
	private String longitude;
	private String latitude;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private String startDate;
	private String endDate;
	private String shopName;
	private String userName;
	private String serverName;
	private int status;
	
	public Menu() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public float getDishsMoney() {
		return dishsMoney;
	}

	public void setDishsMoney(float dishsMoney) {
		this.dishsMoney = dishsMoney;
	}

	public float getCarriageMoney() {
		return carriageMoney;
	}

	public void setCarriageMoney(float carriageMoney) {
		this.carriageMoney = carriageMoney;
	}

	public float getTaxesMoney() {
		return taxesMoney;
	}

	public void setTaxesMoney(float taxesMoney) {
		this.taxesMoney = taxesMoney;
	}

	public float getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(float serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public float getTipMoney() {
		return tipMoney;
	}

	public void setTipMoney(float tipMoney) {
		this.tipMoney = tipMoney;
	}

	public int getMenuCount() {
		return menuCount;
	}

	public void setMenuCount(int menuCount) {
		this.menuCount = menuCount;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getMealStartDate() {
		return mealStartDate;
	}

	public void setMealStartDate(String mealStartDate) {
		this.mealStartDate = mealStartDate;
	}

	public String getMealEndDate() {
		return mealEndDate;
	}

	public void setMealEndDate(String mealEndDate) {
		this.mealEndDate = mealEndDate;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Menu(int id, String mid, String sid, String uid, String wid,
			String did, float dishsMoney, float carriageMoney,
			float taxesMoney, float serviceMoney, float tipMoney,
			int menuCount, int payType, String mealStartDate,
			String mealEndDate, String address, String longitude,
			String latitude, String createBy, String createDate,
			String updateBy, String updateDate, String startDate,
			String endDate, String shopName, String userName,
			String serverName, int status) {
		super();
		this.id = id;
		this.mid = mid;
		this.sid = sid;
		this.uid = uid;
		this.wid = wid;
		this.did = did;
		this.dishsMoney = dishsMoney;
		this.carriageMoney = carriageMoney;
		this.taxesMoney = taxesMoney;
		this.serviceMoney = serviceMoney;
		this.tipMoney = tipMoney;
		this.menuCount = menuCount;
		this.payType = payType;
		this.mealStartDate = mealStartDate;
		this.mealEndDate = mealEndDate;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.shopName = shopName;
		this.userName = userName;
		this.serverName = serverName;
		this.status = status;
	}
	
	
	
}
