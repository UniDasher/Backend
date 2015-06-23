package com.dasher.model;

import java.io.Serializable;

public class MenuEvaluate implements Serializable {

	private int id;
	private String sid;
	private String mid;
	private String uid;
	private String wid;
	private int evalShop;
	private int evalServer;
	private String evalContent;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private String shopName;
	private String userName;
	private String waiterName;

	public MenuEvaluate() {

	}

	public MenuEvaluate(int id, String sid, String mid, String uid, String wid,
			int evalShop, int evalServer, String evalContent, String createBy,
			String createDate, String updateBy, String updateDate,
			String shopName, String userName, String waiterName) {
		super();
		this.id = id;
		this.sid = sid;
		this.mid = mid;
		this.uid = uid;
		this.wid = wid;
		this.evalShop = evalShop;
		this.evalServer = evalServer;
		this.evalContent = evalContent;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.shopName = shopName;
		this.userName = userName;
		this.waiterName = waiterName;
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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

	public int getEvalShop() {
		return evalShop;
	}

	public void setEvalShop(int evalShop) {
		this.evalShop = evalShop;
	}

	public int getEvalServer() {
		return evalServer;
	}

	public void setEvalServer(int evalServer) {
		this.evalServer = evalServer;
	}

	public String getEvalContent() {
		return evalContent;
	}

	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
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

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

}
