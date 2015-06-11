package com.dasher.service.impl;

import java.util.List;

import com.dasher.mapper.ServerSettleMapper;
import com.dasher.model.ServerSettle;
import com.dasher.service.ServerSettleService;

public class ServerSettleServiceImpl implements ServerSettleService {

	private ServerSettleMapper serverSettleMapper;
	
	public ServerSettleMapper getServerSettleMapper() {
		return serverSettleMapper;
	}

	public void setServerSettleMapper(ServerSettleMapper serverSettleMapper) {
		this.serverSettleMapper = serverSettleMapper;
	}

	public boolean add(ServerSettle ss) {
		return serverSettleMapper.add(ss)>0? true:false;
	}

	public int getCount(String searchStr, String startDate, String endDate) {
		return serverSettleMapper.getCount(searchStr,startDate,endDate);
	}

	public List<ServerSettle> list(String searchStr, String startDate,
			String endDate, int startRow, int pageSize) {
		return serverSettleMapper.list(searchStr,startDate,endDate,startRow,pageSize);
	}

}
