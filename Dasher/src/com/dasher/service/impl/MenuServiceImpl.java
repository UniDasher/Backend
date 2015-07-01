package com.dasher.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.ComplainMapper;
import com.dasher.mapper.MenuMapper;
import com.dasher.model.Complain;
import com.dasher.model.Earning;
import com.dasher.model.Menu;
import com.dasher.model.MenuDish;
import com.dasher.model.Shop;
import com.dasher.model.User;
import com.dasher.service.EarningService;
import com.dasher.service.MenuDishService;
import com.dasher.service.MenuService;
import com.dasher.service.ShopDishService;
import com.dasher.service.ShopService;
import com.dasher.service.UserService;
import com.dasher.util.BaiDuMapUtil;
import com.dasher.util.DateUtil;

public class MenuServiceImpl implements MenuService {

	private MenuMapper menuMapper;
	@Autowired
	private MenuDishService menuDishService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ComplainMapper complainMapper;
	@Autowired
	private EarningService earningService;
	@Autowired
	private UserService userService;
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
        m.setDistance(String.valueOf((int)distance));
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

	public int receive(Menu m) {
		synchronized(this){
			//判断订单是否已被接单
			Menu m_1=menuMapper.getByMid(m.getMid());
			if(m_1.getStatus()!=1){
				return 2;
			}else{
				return menuMapper.receive(m)>0? 1:0;
			}
		} 
	}

	public boolean updateStatus(Menu m) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        
        int result=-1;
        boolean flag=false;
        
		if(m.getStatus()==3){
			//订单完成,添加完成的时间
			m.setEndDate(DateUtil.getCurrentDateStr());
			
			Menu m_1=menuMapper.getByMid(m.getMid());
			
			//修改接单人的余额
			User user=userService.getByUId(m_1.getWid());
			float curUserBalance=user.getBalance()+m_1.getDishsMoney()+m_1.getCarriageMoney();
			//更新用户的余额和收支记录
			flag=userService.updateBalance(m_1.getWid(),curUserBalance);
			if(!flag){
				transactionManager.rollback(ts); 
				return flag;
			}
			
			//新增送餐人结算记录
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String strs[]=sdf.format(date).split("-");
			String eid="";
			for(int i=0;i<strs.length;i++)
			{
				eid=eid+strs[i];
			}
			
			float totalMoney=m_1.getDishsMoney()+m_1.getCarriageMoney()+
				m_1.getTaxesMoney()+m_1.getServiceMoney()+m_1.getTipMoney();
			Earning e=new Earning();
			e.setEid(eid);
			e.setWid(m_1.getWid());
			e.setMid(m_1.getMid());
			e.setCarriageMoney(m_1.getCarriageMoney());
			e.setTotalMoney(totalMoney);
			e.setType(1);
			e.setCreateBy(m.getUpdateBy());
			e.setCreateDate(DateUtil.getCurrentDateStr());
			result=earningService.add(e)==true?1:-1;
			
		}else if(m.getStatus()==4){
			//订单取消
			m.setCancleDate(DateUtil.getCurrentDateStr());
			//新增订单取消退款记录
			Menu m_1=menuMapper.getByMid(m.getMid());
			if(m_1.getStatus()!=1){
				transactionManager.rollback(ts);  
				return false;
			}
			Complain c=new Complain();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String strs[]=sdf.format(date).split("-");
			String comId="";
			for(int i=0;i<strs.length;i++)
			{
				comId=comId+strs[i];
			}
			c.setComId(comId);
			c.setMid(m.getMid());
			c.setComType(2);
			c.setUid(m_1.getUid());
			c.setWid("");
			c.setType(1);
			c.setContent("用户取消订单");
			c.setCreateBy(m.getUpdateBy());
			c.setCreateDate(DateUtil.getCurrentDateStr());
			result=complainMapper.add(c);
		}else if(m.getStatus()==5){
			//配送投诉
			m.setComplainDate(DateUtil.getCurrentDateStr());
			result=1;
		}
		if(result>0)
		{
			flag=menuMapper.updateStatus(m)>0? true:false;
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
	public boolean updateStatus_2(Menu m) {
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

	public List<Menu> getListByStr(int type, String uid,int userType) {
		// TODO Auto-generated method stub
		return menuMapper.getListByStr(type, uid,userType);
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

	public void getListOverTime() {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        boolean result=false;
		//获取当前的时间
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		String mealEndDate =df.format(new Date());// new Date()为获取当前系统时间
		
		List<Menu> list=menuMapper.getListOverTime(mealEndDate);
		if(list.size()>0){
			//修改订单的状态
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String updateDate =df.format(new Date());// new Date()为获取当前系统时间
			result=menuMapper.updateOverTime(mealEndDate,updateDate)>0?true:false;
			if(!result)
			{
				transactionManager.rollback(ts);
				return;
			}
			//新增延时退款操作记录
			for (Menu menu : list) {
				Complain c=new Complain();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
				Date date=new Date();
				String strs[]=sdf.format(date).split("-");
				String comId="";
				for(int i=0;i<strs.length;i++)
				{
					comId=comId+strs[i];
				}
				c.setComId(comId);
				c.setMid(menu.getMid());
				c.setComType(3);
				c.setUid(menu.getUid());
				c.setWid("");
				c.setType(1);
				c.setContent("用户订单超时");
				c.setCreateBy(menu.getUid());
				c.setCreateDate(DateUtil.getCurrentDateStr());
				result=complainMapper.add(c)>0?true:false;
				if(!result){
					break;
				}
			}
			
		}
		if(result==true)
		{
			transactionManager.commit(ts);
		}
		else
		{
			transactionManager.rollback(ts);  
		}
		
	}

	public List<Menu> getNearListBySid(String sid) {
		return menuMapper.getNearListBySid(sid);
	}
}
