package com.dasher.service;

import java.util.List;

import com.dasher.model.Complain;

public interface ComplainService {

	public boolean add(Complain c);
	public boolean update(Complain c);
	public int getCount(int status);
	public List<Complain> list(String searchStr, int status,int startRow,int pageSize);
	public Complain getByComId(String comId, int type);
	public boolean handle(Complain c);
	public List<Complain> userComList(String loginId, int status);
}
