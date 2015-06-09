package com.dasher.model;

import java.io.Serializable;

public class MarketMenuRecord implements Serializable {

	private int id;
	private String mcid;
	private String mid;
	private String name;
	private Float price;
	private String unit;
	private int count;
	private String subscribe;
	private String createBy;
	private String createDate;
	
	public MarketMenuRecord() {

	}
	
	public MarketMenuRecord(int id, String mcid, String mid, String name,
			Float price, String unit, int count, String subscribe,
			String createBy, String createDate) {
		super();
		this.id = id;
		this.mcid = mcid;
		this.mid = mid;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.count = count;
		this.subscribe = subscribe;
		this.createBy = createBy;
		this.createDate = createDate;
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
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
