package com.dasher.model;

import java.io.Serializable;

public class ComplainDeal extends Complain implements Serializable {

	private String comId;
	private int comResult;
	private String comContent;
	private float  returnMoney;
	private float deductMoney;
	
	@Override
	public String getComId() {
		return comId;
	}
	@Override
	public void setComId(String comId) {
		this.comId = comId;
	}
	public int getComResult() {
		return comResult;
	}
	public void setComResult(int comResult) {
		this.comResult = comResult;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public float getReturnMoney() {
		return returnMoney;
	}
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	public float getDeductMoney() {
		return deductMoney;
	}
	public void setDeductMoney(float deductMoney) {
		this.deductMoney = deductMoney;
	}
	
	public ComplainDeal(String comId, int comResult, String comContent,
			float returnMoney, float deductMoney) {
		super();
		this.comId = comId;
		this.comResult = comResult;
		this.comContent = comContent;
		this.returnMoney = returnMoney;
		this.deductMoney = deductMoney;
	}
	
	public ComplainDeal() {

	}
	
	
	
	
	
	
}
