package com.dasher.model;

import java.io.Serializable;

public class UserSettle implements Serializable {

	private int id;
	private String wid;
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
	private String updateBy;
	private String updateDate;
	private int status;
	private String userName;
	private String mobilePhone;
	
	public UserSettle() {
		
	}

	public UserSettle(int id, String wid, float oldBalance, int type,
			String typeDesc, float settlePrice, float curBalance,
			String settleNumberType, String settleNumber, String settleDesc,
			String userName, String createBy, String createDate,
			String updateBy, String updateDate, int status,String mobilePhone) {
		super();
		this.id = id;
		this.wid = wid;
		this.oldBalance = oldBalance;
		this.type = type;
		this.typeDesc = typeDesc;
		this.settlePrice = settlePrice;
		this.curBalance = curBalance;
		this.settleNumberType = settleNumberType;
		this.settleNumber = settleNumber;
		this.settleDesc = settleDesc;
		this.userName = userName;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
		this.mobilePhone=mobilePhone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public float getOldBalance() {
		return oldBalance;
	}

	public void setOldBalance(float oldBalance) {
		this.oldBalance = oldBalance;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}
