package com.dasher.mapper;

import com.dasher.model.ComplainDeal;

public interface ComplainDealMapper {

	public int add(ComplainDeal cd);
	public int update(ComplainDeal cd);
	public ComplainDeal getByTel(String mobilePhone);
}
