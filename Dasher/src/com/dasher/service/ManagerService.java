package com.dasher.service;

import java.util.List;

import com.dasher.model.Manager;

public interface ManagerService {

	public boolean add(Manager m);
	public Manager getByAccount(String account);
	public Manager getById(int id);
	public boolean update(Manager m);
	public boolean delete(Manager m);
	public List<Manager> list();
	public int managerLoin(String account,String pwd);
}
