package com.dasher.model;

import java.io.Serializable;

public class Time implements Serializable{
	
	private int id;
	private String sid;
	private int weeks;
	private int flag;
	private String subscribe;
	private String time1;
	private String time2;
	private String time3;
	private String time4;
	private String time5;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	private int status;
	
	public Time() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Time(int id, String sid, int weeks, int flag, String subscribe,
			String time1, String time2, String time3, String time4,
			String time5, String createBy, String createDate, String updateBy,
			String updateDate, int status) {
		super();
		this.id = id;
		this.sid = sid;
		this.weeks = weeks;
		this.flag = flag;
		this.subscribe = subscribe;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
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

	public int getWeeks() {
		return weeks;
	}

	public void setWeeks(int weeks) {
		this.weeks = weeks;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public String getTime5() {
		return time5;
	}

	public void setTime5(String time5) {
		this.time5 = time5;
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
