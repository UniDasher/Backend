package com.dasher.service;

import com.dasher.model.ComplainDeal;

public interface ComplainDealService {

	public boolean add(ComplainDeal cd);
	public boolean update(ComplainDeal cd);
	public ComplainDeal getByTel(String mobilePhone);
	
}
