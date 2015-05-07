package com.dasher.service.impl;

import com.dasher.mapper.MarketMenuRecordMapper;
import com.dasher.model.MarketMenuRecord;
import com.dasher.service.MarketMenuRecordService;

public class MarketMenuRecordServiceImpl implements MarketMenuRecordService {

	private MarketMenuRecordMapper marketMenuRecordMapper;
	
	public MarketMenuRecordMapper getMarketMenuRecordMapper() {
		return marketMenuRecordMapper;
	}

	public void setMarketMenuRecordMapper(
			MarketMenuRecordMapper marketMenuRecordMapper) {
		this.marketMenuRecordMapper = marketMenuRecordMapper;
	}

	public boolean add(MarketMenuRecord mmr) {
		// TODO Auto-generated method stub
		return marketMenuRecordMapper.add(mmr)>0? true:false;
	}

}
