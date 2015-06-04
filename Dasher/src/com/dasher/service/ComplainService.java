package com.dasher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Complain;

public interface ComplainService {

	public boolean add(Complain c);
	public boolean update(Complain c);
	public int getCount(int status);
	public List<Complain> list(int status,int startRow,int pageSize);
	public Complain getByComId(String comId);
	public boolean handle(Complain c);
}
