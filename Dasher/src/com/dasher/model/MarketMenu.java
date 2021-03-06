package com.dasher.model;

import java.io.Serializable;
import java.util.List;

public class MarketMenu implements Serializable {

	private int id;
	private String mid;
	private String smid;
	private String uid;
	private String wid;
	private float dishsMoney;
	private float carriageMoney;
	private float taxesMoney;
	private float serviceMoney;
	private float tipMoney;
	private int menuCount;
	private int payType;
	private String distance;
	private String direction;
	private String address;
	private String longitude;
	private String latitude;
	private String marketName;
	private String userName;
	private String serverName;
	private String mealStartDate;
	private String mealEndDate;
	private String startDate;
	private String endDate;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private int status;
	
	private String cancleDate;
	private String overTimeDate;
	private String complainDate;
	
	private String shopAddress;
	private String shopLongitude;
	private String shopLatitude;
	private String userPhone;
	private String userLogo;
	private String serverPhone;
	private String serverLogo;
	private List<MarketMenuRecord> dishs;
	
	public MarketMenu() {

	}
	
	public MarketMenu(int id, String mid, String smid, String uid, String wid,
			float dishsMoney, float carriageMoney, float taxesMoney,
			float serviceMoney, float tipMoney, int menuCount, int payType,
			String distance, String direction, String address,
			String longitude, String latitude, String marketName,
			String userName, String serverName, String mealStartDate,
			String mealEndDate, String startDate, String endDate,
			String createBy, String createDate, String updateBy,
			String updateDate, int status, String cancleDate,
			String overTimeDate, String complainDate, String shopAddress,
			String shopLongitude, String shopLatitude, String userPhone,
			String userLogo, String serverPhone, String serverLogo) {
		super();
		this.id = id;
		this.mid = mid;
		this.smid = smid;
		this.uid = uid;
		this.wid = wid;
		this.dishsMoney = dishsMoney;
		this.carriageMoney = carriageMoney;
		this.taxesMoney = taxesMoney;
		this.serviceMoney = serviceMoney;
		this.tipMoney = tipMoney;
		this.menuCount = menuCount;
		this.payType = payType;
		this.distance = distance;
		this.direction = direction;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.marketName = marketName;
		this.userName = userName;
		this.serverName = serverName;
		this.mealStartDate = mealStartDate;
		this.mealEndDate = mealEndDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
		this.cancleDate = cancleDate;
		this.overTimeDate = overTimeDate;
		this.complainDate = complainDate;
		this.shopAddress = shopAddress;
		this.shopLongitude = shopLongitude;
		this.shopLatitude = shopLatitude;
		this.userPhone = userPhone;
		this.userLogo = userLogo;
		this.serverPhone = serverPhone;
		this.serverLogo = serverLogo;
	}

	public List<MarketMenuRecord> getDishs() {
		return dishs;
	}

	public void setDishs(List<MarketMenuRecord> dishs) {
		this.dishs = dishs;
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


	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
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

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getCancleDate() {
		return cancleDate;
	}

	public void setCancleDate(String cancleDate) {
		this.cancleDate = cancleDate;
	}

	public String getOverTimeDate() {
		return overTimeDate;
	}

	public void setOverTimeDate(String overTimeDate) {
		this.overTimeDate = overTimeDate;
	}

	public String getComplainDate() {
		return complainDate;
	}

	public void setComplainDate(String complainDate) {
		this.complainDate = complainDate;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(String shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	public String getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(String shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserLogo() {
		return userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getServerPhone() {
		return serverPhone;
	}

	public void setServerPhone(String serverPhone) {
		this.serverPhone = serverPhone;
	}

	public String getServerLogo() {
		return serverLogo;
	}

	public void setServerLogo(String serverLogo) {
		this.serverLogo = serverLogo;
	}
}
