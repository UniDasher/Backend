package com.dasher.model;

import java.io.Serializable;

public class MarketCommodity implements Serializable {

	private int id;
	private String mcid;
	private String name;
	private String unit;
	private String type;
	private String subscribe;
	
	public MarketCommodity() {

	}

	public MarketCommodity(int id, String mcid, String name, String unit,
			String type, String subscribe) {
		super();
		this.id = id;
		this.mcid = mcid;
		this.name = name;
		this.unit = unit;
		this.type = type;
		this.subscribe = subscribe;
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
	
	
}
