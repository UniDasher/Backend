package com.dasher.service;

import com.dasher.model.Time;

public interface TimeService {

	public boolean add(Time t);
	public boolean update(Time t);
	public Time getById(String id);
}
