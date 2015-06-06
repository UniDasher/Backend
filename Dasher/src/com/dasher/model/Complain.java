package com.dasher.model;

import java.io.Serializable;

public class Complain implements Serializable {

	private int id;
	private String comId;
	private String uid;
	private String mid;
	private String wid;
	private int type;
	private String content;
	private String comContent;
	private float returnMoney;
	private float deductMoney;
	private String userName;
	private String mobilePhone;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private int status;
	private int menuStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public float getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	public float getDeductMoney() {
		return deductMoney;
	}
	public void setDeductMoney(float deductMoney) {
		this.deductMoney = deductMoney;
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
	public int getMenuStatus() {
		return menuStatus;
	}
	public void setMenuStatus(int menuStatus) {
		this.menuStatus = menuStatus;
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
	
	public Complain() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Complain(int id, String comId, String uid, String mid, String wid,
			String content, String comContent, float returnMoney,
			float deductMoney, String userName, String mobilePhone,
			String createBy, String createDate, String updateBy,
			String updateDate, int status, int menuStatus) {
		super();
		this.id = id;
		this.comId = comId;
		this.uid = uid;
		this.mid = mid;
		this.wid = wid;
		this.content = content;
		this.comContent = comContent;
		this.returnMoney = returnMoney;
		this.deductMoney = deductMoney;
		this.userName = userName;
		this.mobilePhone = mobilePhone;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
		this.menuStatus = menuStatus;
	}
	
}
