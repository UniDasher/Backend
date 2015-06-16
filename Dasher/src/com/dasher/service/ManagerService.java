package com.dasher.service;

import java.util.List;

import com.dasher.model.Manager;

public interface ManagerService {

	public int managerLoin(String account,String pwd);
	public boolean add(Manager m);
	public boolean update(Manager m);
	public boolean delete(Manager m);
	public Manager getByAccount(String account);
	public Manager getById(int id);
	public List<Manager> listAll();
	public List<Manager> list();
}
