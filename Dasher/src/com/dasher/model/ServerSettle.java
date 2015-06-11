package com.dasher.model;

import java.io.Serializable;

public class ServerSettle implements Serializable {

	private int id;
	private String uid;
	private float oldBalance;
	private int type;
	private String typeDesc;
	private float settlePrice;
	private float curBalance;
	private String settleNumberType;
	private String settleNumber;
	private String settleDesc;
	private String createBy;
	private String createDate;
	
	private String userName;
	private String mobilePhone;
	
	public ServerSettle() {
	
	}
	public ServerSettle(int id, String uid, float oldBalance, int type,
			String typeDesc, float settlePrice, float curBalance,
			String settleNumberType, String settleNumber, String settleDesc,
			String createBy, String createDate, String userName,
			String mobilePhone) {
		super();
		this.id = id;
		this.uid = uid;
		this.oldBalance = oldBalance;
		this.type = type;
		this.typeDesc = typeDesc;
		this.settlePrice = settlePrice;
		this.curBalance = curBalance;
		this.settleNumberType = settleNumberType;
		this.settleNumber = settleNumber;
		this.settleDesc = settleDesc;
		this.createBy = createBy;
		this.createDate = createDate;
		this.userName = userName;
		this.mobilePhone = mobilePhone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
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

	public float getOldBalance() {
		return oldBalance;
	}

	public void setOldBalance(float oldBalance) {
		this.oldBalance = oldBalance;
	}

	

	public float getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(float settlePrice) {
		this.settlePrice = settlePrice;
	}

	public float getCurBalance() {
		return curBalance;
	}

	public void setCurBalance(float curBalance) {
		this.curBalance = curBalance;
	}

	public String getSettleNumberType() {
		return settleNumberType;
	}

	public void setSettleNumberType(String settleNumberType) {
		this.settleNumberType = settleNumberType;
	}

	public String getSettleNumber() {
		return settleNumber;
	}

	public void setSettleNumber(String settleNumber) {
		this.settleNumber = settleNumber;
	}

	public String getSettleDesc() {
		return settleDesc;
	}

	public void setSettleDesc(String settleDesc) {
		this.settleDesc = settleDesc;
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

	
}
