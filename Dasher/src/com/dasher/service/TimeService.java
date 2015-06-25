package com.dasher.service;

import java.util.List;

import com.dasher.model.Time;

public interface TimeService {

	public boolean add(String sid,String myloginId,String week_1, String flag_1, String times_1,
			String week_2, String flag_2, String times_2, String week_3,
			String flag_3, String times_3, String week_4, String flag_4,
			String times_4, String week_5, String flag_5, String times_5,
			String week_6, String flag_6, String times_6, String week_7,
			String flag_7, String times_7);
	public List<Time> getBySId(String sid);
}
