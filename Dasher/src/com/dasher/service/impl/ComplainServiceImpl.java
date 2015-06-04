package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.ComplainMapper;
import com.dasher.model.Complain;
import com.dasher.service.ComplainService;

public class ComplainServiceImpl implements ComplainService {

	private ComplainMapper complainMapper;
	
	public ComplainMapper getComplainMapper() {
		return complainMapper;
	}

	public void setComplainMapper(ComplainMapper complainMapper) {
		this.complainMapper = complainMapper;
	}

	public boolean add(Complain c) {
		// TODO Auto-generated method stub
		return complainMapper.add(c)>0? true:false;
	}

	public Complain getByComId(String comId) {
		// TODO Auto-generated method stub
		return complainMapper.getByComId(comId);
	}

	public boolean update(Complain c) {
		// TODO Auto-generated method stub
		return complainMapper.update(c)>0? true:false;
	}

	public List<Complain> list(int status, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return complainMapper.list(status, startRow, pageSize);
	}

	public int getCount(int status) {
		// TODO Auto-generated method stub
		return complainMapper.getCount(status);
	}

	public boolean handle(Complain c) {
		// TODO Auto-generated method stub
		return complainMapper.handle(c)>0? true:false;
	}
}
