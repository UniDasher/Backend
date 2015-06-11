package com.dasher.service;

import java.util.List;

import com.dasher.model.ServerSettle;

public interface ServerSettleService {

	public boolean add(ServerSettle ss);

	public int getCount(String searchStr, String startDate, String endDate);

	public List<ServerSettle> list(String searchStr, String startDate,
			String endDate, int startRow, int pageSize);
}
