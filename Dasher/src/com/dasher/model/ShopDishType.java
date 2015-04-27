package com.dasher.model;

import java.io.Serializable;

public class ShopDishType implements Serializable {

	private int id;
	private String name;
	private int sortNum;
	private int status;
	
	public ShopDishType() {
		
	}

	public ShopDishType(int id, String name, int sortNum, int status) {
		super();
		this.id = id;
		this.name = name;
		this.sortNum = sortNum;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
