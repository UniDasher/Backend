package com.dasher.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.MenuMapper;
import com.dasher.model.Menu;
import com.dasher.model.MenuDish;
import com.dasher.model.ShopDish;
import com.dasher.service.MenuDishService;
import com.dasher.service.MenuService;
import com.dasher.service.ShopDishService;
import com.dasher.util.DateUtil;

public class MenuServiceImpl implements MenuService {

	private MenuMapper menuMapper;
	@Autowired
	private MenuDishService menuDishService;
	@Autowired
	private ShopDishService shopDishService;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;

	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	public boolean add(Menu m) {
		// TODO Auto-generated method stub
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        int result=-1;
        boolean flag=false;
		result=menuMapper.add(m);
		if(result>0)
		{
			ShopDish sd=shopDishService.getByDid(m.getDid());
			if(sd!=null)
			{
				MenuDish md=new MenuDish();
				md.setDid(sd.getDid());
				md.setName(sd.getName());
				md.setPrice(sd.getPrice());
				md.setCount(m.getMenuCount());
				md.setCreateBy(m.getCreateBy());
				md.setCreateDate(DateUtil.getCurrentDateStr());
				flag=menuDishService.add(md);
				if(flag==true)
				{
					transactionManager.commit(ts);
				}
				else
				{
					transactionManager.rollback(ts);  
				}
				
			}
			
		}
		else
		{
			transactionManager.rollback(ts);  
		}
			
		return flag;
	}

	public boolean receive(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.receive(m)>0? true:false;
	}

	public boolean updateStatus(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.updateStatus(m)>0? true:false;
	}

	public List<Menu> list(String status, String sid, String searchStr,
			String startDate, String endDate, int curPage, int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.list(status, sid, searchStr, startDate, endDate, curPage, countPage);
	}

	public int getCount(String status, String sid, String searchStr,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return menuMapper.getCount(status, sid, searchStr, startDate, endDate);
	}

	public List<Menu> getListByUid(int type, String searchStr, int curPage,
			int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.getListByUid(type, searchStr, curPage, countPage);
	}

	public int getListByUidCount(int type, String searchStr) {
		// TODO Auto-generated method stub
		return menuMapper.getListByUidCount(type, searchStr);
	}

	public Menu getByMid(String mid) {
		// TODO Auto-generated method stub
		return menuMapper.getByMid(mid);
	}

	public int CountByStatus(String uid,String status) {
		// TODO Auto-generated method stub
		return menuMapper.CountByStatus(uid,status);
	}

	public List<Menu> listByStatus(String uid,String status, int curPage, int countPage) {
		// TODO Auto-generated method stub
		return menuMapper.listByStatus(uid,status, curPage, countPage);
	}

	public boolean updateMealDate(Menu m) {
		// TODO Auto-generated method stub
		return menuMapper.updateMealDate(m)>0? true:false;
	}

	public List<Menu> getListByStr(int type, String uid) {
		// TODO Auto-generated method stub
		return menuMapper.getListByStr(type, uid);
	}

	public List<Menu> getNearList(float longitude, float latitude,float distance) {
		// TODO Auto-generated method stub
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		List<Menu> list=menuMapper.getNearlist(minlon, maxlon, minlat, maxlat);
		return list;

	}



}
