package com.dasher.model;

import java.io.Serializable;

public class MenuEvaluate implements Serializable {

	private int id;
	private String sid;
	private String mid;
	private String uid;
	private String wid;
	private int evalShop;
	private int evalServer;
	private String evalContent;
	
	public MenuEvaluate() {
		
	}

	public MenuEvaluate(int id, String sid, String mid, String uid, String wid,
			int evalShop, int evalServer, String evalContent) {
		super();
		this.id = id;
		this.sid = sid;
		this.mid = mid;
		this.uid = uid;
		this.wid = wid;
		this.evalShop = evalShop;
		this.evalServer = evalServer;
		this.evalContent = evalContent;
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

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public int getEvalShop() {
		return evalShop;
	}

	public void setEvalShop(int evalShop) {
		this.evalShop = evalShop;
	}

	public int getEvalServer() {
		return evalServer;
	}

	public void setEvalServer(int evalServer) {
		this.evalServer = evalServer;
	}

	public String getEvalContent() {
		return evalContent;
	}

	public void setEvalContent(String evalContent) {
		this.evalContent = evalContent;
	}
	
	
	
}
