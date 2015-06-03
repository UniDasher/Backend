package com.dasher.service.impl;

import java.util.List;

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

	public List<MarketMenuRecord> list(String mid) {
		// TODO Auto-generated method stub
		return marketMenuRecordMapper.list(mid);
	}

}
