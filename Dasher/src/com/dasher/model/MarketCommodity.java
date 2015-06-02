package com.dasher.model;

import java.io.Serializable;

public class MarketCommodity implements Serializable {

	private int id;
	private String smid;
	private String mcid;
	private String name;
	private float price;
	private String unit;
	private int typeId;
	private String subscribe;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private int status;
	
	public MarketCommodity() {

	}

	public MarketCommodity(int id, String smid, String mcid, String name,
			float price, String unit, int typeId, String subscribe,
			String createBy, String createDate, String updateBy,
			String updateDate, int status) {
		super();
		this.id = id;
		this.smid = smid;
		this.mcid = mcid;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.typeId = typeId;
		this.subscribe = subscribe;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateDate = updateDate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMcid() {
		return mcid;
	}

	public void setMcid(String mcid) {
		this.mcid = mcid;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSmid() {
		return smid;
	}

	public void setSmid(String smid) {
		this.smid = smid;
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
}
