package com.dasher.model;

import java.io.Serializable;

public class MenuDish implements Serializable {

	private int id;
	private String mid;
	private String did;
	private String name;
	private float price;
	private int count;
	
	public MenuDish() {
		
	}

	public MenuDish(int id, String mid, String did, String name, float price,
			int count) {
		super();
		this.id = id;
		this.mid = mid;
		this.did = did;
		this.name = name;
		this.price = price;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
