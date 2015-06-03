package com.dasher.service;

import java.util.List;

import com.dasher.model.MarketMenuRecord;

public interface MarketMenuRecordService {

	public boolean add(MarketMenuRecord mmr);
	public List<MarketMenuRecord> list(String mid);
}
