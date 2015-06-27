package com.dasher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.MenuMapper;
import com.dasher.model.Menu;
import com.dasher.model.MenuDish;
import com.dasher.model.Shop;
import com.dasher.service.MenuDishService;
import com.dasher.service.MenuService;
import com.dasher.service.ShopDishService;
import com.dasher.service.ShopService;
import com.dasher.util.BaiDuMapUtil;
import com.dasher.util.DateUtil;

public class MenuServiceImpl implements MenuService {

	private MenuMapper menuMapper;
	@Autowired
	private MenuDishService menuDishService;
	@Autowired
	private ShopService shopService;
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
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        int result=-1;
        boolean flag=false;
        //获取商家的信息
        Shop shop=shopService.getBySid(m.getSid());
        //获取商家和订单地址的距离和方位
        String direction=BaiDuMapUtil.GetDirection(Double.parseDouble(shop.getLongitude()),
        		Double.parseDouble(shop.getLatitude()),Double.parseDouble(m.getLongitude()),
        		Double.parseDouble(m.getLatitude()));
        double distance =BaiDuMapUtil.GetShortDistance(Double.parseDouble(shop.getLongitude()),
        		Double.parseDouble(shop.getLatitude()),Double.parseDouble(m.getLongitude()),
        		Double.parseDouble(m.getLatitude()));
        m.setDistance(String.valueOf(distance));
        m.setDirection(direction);
        //保存订单信息
		result=menuMapper.add(m);
		if(result>0)
		{
			List<MenuDish> dishs=m.getDishs();
			for (MenuDish md : dishs) {
				flag=menuDishService.add(md);
				if(!flag){
					break;
				}
			}
			if(flag==true)
			{
				transactionManager.commit(ts);
			}
			else
			{
				transactionManager.rollback(ts);  
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
		if(m.getStatus()==3){
			m.setEndDate(DateUtil.getCurrentDateStr());
		}else if(m.getStatus()==4){
			m.setCancleDate(DateUtil.getCurrentDateStr());
		}else if(m.getStatus()==5){
			m.setComplainDate(DateUtil.getCurrentDateStr());
		}
		return menuMapper.updateStatus(m)>0? true:false;
	}

	public List<Menu> list(String status, String sid, String searchStr,
			String startDate, String endDate, int curPage, int countPage) {
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

	public List<Menu> getNearList(double longitude, double latitude,float distance) {
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
