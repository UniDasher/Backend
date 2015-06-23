package com.dasher.mapper;


import java.util.List;

import com.dasher.model.Manager;

public interface ManagerMapper {

	public int add(Manager m);
	public int update(Manager m);
	public int delete(Manager m);
	public Manager getByAccount(String account);
	public Manager getById(int id);
	public List<Manager> list();
	public List<Manager> listAll();
	
}
