package com.dasher.service.impl;

import com.dasher.mapper.ComplainDealMapper;
import com.dasher.model.ComplainDeal;
import com.dasher.service.ComplainDealService;

public class ComplainDealServiceImpl implements ComplainDealService {

	private ComplainDealMapper complainDealMapper;
	
	
	public ComplainDealMapper getComplainDealMapper() {
		return complainDealMapper;
	}

	public void setComplainDealMapper(ComplainDealMapper complainDealMapper) {
		this.complainDealMapper = complainDealMapper;
	}

	public boolean add(ComplainDeal cd) {
		// TODO Auto-generated method stub
		return complainDealMapper.add(cd)>0? true:false;
	}

	public ComplainDeal getByTel(String mobilePhone) {
		// TODO Auto-generated method stub
		return complainDealMapper.getByTel(mobilePhone);
	}

	public boolean update(ComplainDeal cd) {
		// TODO Auto-generated method stub
		return complainDealMapper.update(cd)>0? true:false;
	}

}
