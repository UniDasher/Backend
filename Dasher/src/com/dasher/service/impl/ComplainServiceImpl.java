package com.dasher.service.impl;

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

	

}
