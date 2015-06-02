package com.dasher.model;

import java.io.Serializable;

public class User implements Serializable {

	private int id;
	private String uid;
	private String firstName;
	private String lastName;
	private String account;
	private String password;
	private String salt;
	private String equmentNumber;
	private String logo;
	private float balance;
	private String mobilePhone;
	private String email;
	private String address;
	private String longitude;
	private String latitude;
	private int goodEvaluate;
	private int badEvaluate;
	private String createDate;
	private String bankAccount;
	private String bankType;
	private int status;
	
	public User()
	{
		
	}

	public User(int id, String uid, String firstName, String lastName,
			String account, String password, String salt, String equmentNumber,
			String logo, float balance, String mobilePhone, String email,
			String address, String longitude, String latitude,
			int goodEvaluate, int badEvaluate, String createDate,
			String bankAccount, String bankType, int status) {
		super();
		this.id = id;
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
		this.password = password;
		this.salt = salt;
		this.equmentNumber = equmentNumber;
		this.logo = logo;
		this.balance = balance;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.goodEvaluate = goodEvaluate;
		this.badEvaluate = badEvaluate;
		this.createDate = createDate;
		this.bankAccount = bankAccount;
		this.bankType = bankType;
		this.status = status;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEqumentNumber() {
		return equmentNumber;
	}

	public void setEqumentNumber(String equmentNumber) {
		this.equmentNumber = equmentNumber;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getGoodEvaluate() {
		return goodEvaluate;
	}

	public void setGoodEvaluate(int goodEvaluate) {
		this.goodEvaluate = goodEvaluate;
	}

	public int getBadEvaluate() {
		return badEvaluate;
	}

	public void setBadEvaluate(int badEvaluate) {
		this.badEvaluate = badEvaluate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
