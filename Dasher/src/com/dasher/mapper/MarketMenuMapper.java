package com.dasher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dasher.model.MarketMenu;
import com.dasher.model.Menu;

public interface MarketMenuMapper {

	public int add(MarketMenu mm);
	public int receive(MarketMenu mm);
	public int updateStatus(MarketMenu mm);
	public int getCount(@Param(value="status") String status,@Param(value="smid") String smid,@Param(value="searchStr") String searchStr);
	public List<MarketMenu> list(@Param(value="status") String status,@Param(value="smid") String smid,@Param(value="searchStr") String searchStr,@Param(value="startRow") int startRow,@Param(value="pageSize") int pageSize);

}
