package com.dasher.model;

import java.io.Serializable;

public class ShopDish implements Serializable {

	private int id;
	private String sid;
	private String did;
	private String name;
	private float price;
	private int typeId;
	private String typeName;
	private String chilies;
	private String description;
	private int createBy;
	private String createDate;
	private int updateBy;
	private String updateDate;
	private int status;
	
	public ShopDish() {
		
	}

	public ShopDish(int id, String sid, String did, String name, float price,
			int typeId, String typeName, String chilies, String description,
			int createBy, String createDate, int updateBy, String updateDate,
			int status) {
		super();
		this.id = id;
		this.sid = sid;
		this.did = did;
		this.name = name;
		this.price = price;
		this.typeId = typeId;
		this.typeName = typeName;
		this.chilies = chilies;
		this.description = description;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
	}

	public int getCreateBy() {
		return createBy;
	}
	public void setCreateBy(int createBy) {
		this.createBy = createBy;
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

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getChilies() {
		return chilies;
	}
	
	public void setChilies(String chilies) {
		this.chilies = chilies;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
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
}
