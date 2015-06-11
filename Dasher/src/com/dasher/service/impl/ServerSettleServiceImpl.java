package com.dasher.service.impl;

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

}
