package com.dasher.model;

import java.io.Serializable;

public class MarketMenuRecord implements Serializable {

	private int id;
	private String mid;
	private String name;
	private String unit;
	private int count;
	private String subscribe;
	private String image;
	
	public MarketMenuRecord() {

	}

	public MarketMenuRecord(int id, String mid, String name, String unit,
			int count, String subscribe, String image) {
		super();
		this.id = id;
		this.mid = mid;
		this.name = name;
		this.unit = unit;
		this.count = count;
		this.subscribe = subscribe;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
