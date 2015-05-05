package com.dasher.service.impl;

import java.util.List;
import com.dasher.mapper.ManagerMapper;
import com.dasher.model.Manager;
import com.dasher.service.ManagerService;
import com.dasher.util.MyMD5Util;

public class ManagerServiceImpl implements ManagerService {

	private ManagerMapper managerMapper;
	
	
	public ManagerMapper getManagerMapper() {
		return managerMapper;
	}

	public void setManagerMapper(ManagerMapper managerMapper) {
		this.managerMapper = managerMapper;
	}

	public boolean add(Manager m) {
		// TODO Auto-generated method stub
		return managerMapper.add(m)>0? true:false;
	}

	public Manager getByAccount(String account) {
		// TODO Auto-generated method stub
		return managerMapper.getByAccount(account);
	}

	public int managerLoin(String account, String pwd) {
		// TODO Auto-generated method stub
		int flag=-1;
		Manager m=managerMapper.getByAccount(account);
		if(m==null)
			flag=1;
		else
		{
			try {
				if(!MyMD5Util.validPassword(pwd, m.getPassword()))
					flag=2;
				else
					flag=0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean update(Manager m) {
		// TODO Auto-generated method stub
		return managerMapper.update(m)>0? true:false;
	}

	public Manager getById(int id) {
		// TODO Auto-generated method stub
		return managerMapper.getById(id);
	}

	public boolean delete(Manager m) {
		// TODO Auto-generated method stub
		return managerMapper.delete(m)>0? true:false;
	}

	public List<Manager> list() {
		// TODO Auto-generated method stub
		return managerMapper.list();
	}

	public List<Manager> listAll() {
		// TODO Auto-generated method stub
		return managerMapper.listAll();
	}
	
	

}
