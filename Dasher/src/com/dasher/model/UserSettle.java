package com.dasher.model;

import java.io.Serializable;

public class UserSettle implements Serializable {

	private int id;
	private String uid;
	private float oldBalance;
	private int settleType;
	private float settlePrice;
	private float curBalance;
	private String settleNumber;
	private String settleDesc;
	private String createBy;
	private String createDate;
	
	public UserSettle() {
		
	}

	public UserSettle(int id, String uid, float oldBalance, int settleType,
			float settlePrice, float curBalance, String settleNumber,
			String settleDesc, String createBy, String createDate) {
		super();
		this.id = id;
		this.uid = uid;
		this.oldBalance = oldBalance;
		this.settleType = settleType;
		this.settlePrice = settlePrice;
		this.curBalance = curBalance;
		this.settleNumber = settleNumber;
		this.settleDesc = settleDesc;
		this.createBy = createBy;
		this.createDate = createDate;
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

	public int getSettleType() {
		return settleType;
	}

	public void setSettleType(int settleType) {
		this.settleType = settleType;
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
