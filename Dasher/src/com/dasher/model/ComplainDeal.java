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
	@Override
	public String getComContent() {
		return comContent;
	}
	@Override
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	@Override
	public float getReturnMoney() {
		return returnMoney;
	}
	@Override
	public void setReturnMoney(float returnMoney) {
		this.returnMoney = returnMoney;
	}
	@Override
	public float getDeductMoney() {
		return deductMoney;
	}
	@Override
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
