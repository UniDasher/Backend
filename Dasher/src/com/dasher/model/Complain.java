package com.dasher.model;

import java.io.Serializable;

public class Complain implements Serializable {

	private int id;
	private String comId;
	private int comType;
	private String uid;
	private String mid;
	private String wid;
	private int type;
	private String content;
	private int comResult;
	private String comContent;
	private float returnMoney;
	private float deductMoney;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private String userName;//投诉用户的名字
	private String userPhone;//投诉用户的电话
	private String waiterName;//送餐者的名字
	private String waiterPhone;//送餐者的电话
	private int status;//投诉的状态
	private int menuStatus;//订单的状态
	private float dishsMoney;//订单金额
	private float carriageMoney;//运费
	
	public float getCarriageMoney() {
		return carriageMoney;
	}
	public void setCarriageMoney(float carriageMoney) {
		this.carriageMoney = carriageMoney;
	}
	
	public float getDishsMoney() {
		return dishsMoney;
	}
	public void setDishsMoney(float dishsMoney) {
		this.dishsMoney = dishsMoney;
	}
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
	public int getComType() {
		return comType;
	}
	public void setComType(int comType) {
		this.comType = comType;
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
	public int getComResult() {
		return comResult;
	}
	public void setComResult(int comResult) {
		this.comResult = comResult;
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

	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getWaiterName() {
		return waiterName;
	}
	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}
	public String getWaiterPhone() {
		return waiterPhone;
	}
	public void setWaiterPhone(String waiterPhone) {
		this.waiterPhone = waiterPhone;
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

	public Complain(int id, String comId,int comType, String uid, String mid, String wid,
			int type, String content, int comResult, String comContent,
			float returnMoney, float deductMoney, String createBy,
			String createDate, String updateBy, String updateDate,
			String userName, String userPhone, String waiterName,
			String waiterPhone, int status, int menuStatus) {
		super();
		this.id = id;
		this.comId = comId;
		this.comType=comType;
		this.uid = uid;
		this.mid = mid;
		this.wid = wid;
		this.type = type;
		this.content = content;
		this.comResult = comResult;
		this.comContent = comContent;
		this.returnMoney = returnMoney;
		this.deductMoney = deductMoney;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.userName = userName;
		this.userPhone = userPhone;
		this.waiterName = waiterName;
		this.waiterPhone = waiterPhone;
		this.status = status;
		this.menuStatus = menuStatus;
	}
	public Complain(int id, String comId, String uid, String mid, String wid,
			int type, String content, int comResult, String comContent,
			float returnMoney, float deductMoney, String createBy,
			String createDate, String updateBy, String updateDate,
			String userName, String userPhone, String waiterName,
			String waiterPhone, int status, int menuStatus,
			float carriageMoney, float dishsMoney) {
		super();
		this.id = id;
		this.comId = comId;
		this.uid = uid;
		this.mid = mid;
		this.wid = wid;
		this.type = type;
		this.content = content;
		this.comResult = comResult;
		this.comContent = comContent;
		this.returnMoney = returnMoney;
		this.deductMoney = deductMoney;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.userName = userName;
		this.userPhone = userPhone;
		this.waiterName = waiterName;
		this.waiterPhone = waiterPhone;
		this.status = status;
		this.menuStatus = menuStatus;
		this.carriageMoney = carriageMoney;
		this.dishsMoney = dishsMoney;
	}
}
