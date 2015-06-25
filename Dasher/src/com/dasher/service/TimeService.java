package com.dasher.service;

import java.util.List;

import com.dasher.model.Time;

public interface TimeService {

	public boolean add(Time t);
	public boolean update(Time t);
	public boolean delete(Time t);
	public Time getById(String id);
	public Time getBySid(String sid);
	public List<Time> list(String sid);
}
