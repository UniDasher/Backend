package com.dasher.mapper;

import java.util.List;

import com.dasher.model.MarketMenuRecord;

public interface MarketMenuRecordMapper {

	public int add(MarketMenuRecord mmr);
	public List<MarketMenuRecord> list(String mid);
}
