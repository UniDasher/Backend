package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.Complain;

public interface ComplainMapper {

	public int add(Complain c);
	public int update(Complain c);
	public int getCount(int status);
	public List<Complain> list(@Param(value="searchStr") String searchStr, @Param(value="status") int status,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);
	public Complain getByComId(@Param(value="comId") String comId,@Param(value="type") int type);
	public int handle(Complain c);
	public List<Complain> userComList(@Param(value="uid") String uid, @Param(value="status") int status);
	
}
