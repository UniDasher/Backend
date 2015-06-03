package com.dasher.service;

import com.dasher.model.Complain;

public interface ComplainService {

	public boolean add(Complain c);
	public boolean update(Complain c);
	public Complain getByComId(String comId);
}
