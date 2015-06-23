package com.dasher.service;

import java.util.List;

import com.dasher.model.Complain;

public interface ComplainService {


	public int getCount(int status);
	public boolean add(Complain c);
	public boolean update(Complain c);
	public boolean handle(Complain c);
	public Complain getByComId(String comId, int type);
	public List<Complain> list(String searchStr, int status,int startRow,int pageSize);
	public List<Complain> userComList(String loginId, int status);
}
