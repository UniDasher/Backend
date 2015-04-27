package com.dasher.model;

import java.io.Serializable;

public class Complain implements Serializable {

	protected int id;
	protected String comId;
	protected String uid;
	protected String mid;
	protected String content;
	protected int status;
	protected String imageName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Complain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Complain(int id, String comId, String uid, String mid,
			String content, int status, String imageName) {
		super();
		this.id = id;
		this.comId = comId;
		this.uid = uid;
		this.mid = mid;
		this.content = content;
		this.status = status;
		this.imageName = imageName;
	}
	
	
}
