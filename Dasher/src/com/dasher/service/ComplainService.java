package com.dasher.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dasher.model.Complain;

public interface ComplainService {

	public int getCount(String searchStr, int status, String startDate, String endDate);
	public boolean add(Complain c);
	public boolean update(Complain c);
	public boolean handle(HttpServletRequest request,String fileName,Complain c);
	public Complain getByComId(String comId, int type);
	public List<Complain> list(String searchStr, int status, String startDate, String endDate,int startRow,int pageSize);
	public List<Complain> userComList(String loginId, int status);
}
