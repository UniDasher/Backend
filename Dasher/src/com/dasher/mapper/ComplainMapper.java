package com.dasher.mapper;

import com.dasher.model.Complain;

public interface ComplainMapper {

	public int add(Complain c);
	public int update(Complain c);
	public Complain getByComId(String comId);
}
