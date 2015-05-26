package com.dasher.model;

import java.io.Serializable;

public class MarketCommodity implements Serializable {

	private int id;
	private String smid;
	private String mcid;
	private String name;
	private String unit;
	private String type;
	private String subscribe;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private int status;
	
	public MarketCommodity() {

	}

	public MarketCommodity(int id, String smid, String mcid, String name,
			String unit, String type, String subscribe, String createBy,
			String createDate, String updateBy, String updateDate, int status) {
		super();
		this.id = id;
		this.smid = smid;
		this.mcid = mcid;
		this.name = name;
		this.unit = unit;
		this.type = type;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
