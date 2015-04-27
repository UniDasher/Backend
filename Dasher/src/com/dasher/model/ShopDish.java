package com.dasher.model;

import java.io.Serializable;

public class ShopDish implements Serializable {

	private int id;
	private String sid;
	private String did;
	private String name;
	private float price;
	private int typeId;
	private int chilies;
	private String description;
	
	public ShopDish() {
		
	}

	public ShopDish(int id, String sid, String did, String name, float price,
			int typeId, int chilies, String description) {
		super();
		this.id = id;
		this.sid = sid;
		this.did = did;
		this.name = name;
		this.price = price;
		this.typeId = typeId;
		this.chilies = chilies;
		this.description = description;
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

	public int getChilies() {
		return chilies;
	}

	public void setChilies(int chilies) {
		this.chilies = chilies;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
