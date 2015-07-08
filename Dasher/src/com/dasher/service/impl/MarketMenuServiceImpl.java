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
import com.dasher.mapper.MarketMenuMapper;
import com.dasher.model.Complain;
import com.dasher.model.Earning;
import com.dasher.model.Market;
import com.dasher.model.MarketMenu;
import com.dasher.model.MarketMenuRecord;
import com.dasher.model.Menu;
import com.dasher.model.Shop;
import com.dasher.model.User;
import com.dasher.service.EarningService;
import com.dasher.service.MarketCommodityService;
import com.dasher.service.MarketMenuRecordService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.MarketService;
import com.dasher.service.UserService;
import com.dasher.util.BaiDuMapUtil;
import com.dasher.util.DateUtil;

public class MarketMenuServiceImpl implements MarketMenuService {

	private MarketMenuMapper marketMenuMapper;
	@Autowired
	private MarketMenuRecordService marketMenuRecordService;
	@Autowired
	private ComplainMapper complainMapper;
	@Autowired
	private MarketService marketService;
	@Autowired
	private EarningService earningService;
	@Autowired
	private UserService userService;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;
	
	public MarketMenuMapper getMarketMenuMapper() {
		return marketMenuMapper;
	}

	public void setMarketMenuMapper(MarketMenuMapper marketMenuMapper) {
		this.marketMenuMapper = marketMenuMapper;
	}

	public boolean add(MarketMenu mm) {
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        int result=-1;
        boolean flag=false;
        //获取商家的信息
        Market market=marketService.getBySmid(mm.getSmid());
        //获取商家和订单地址的距离和方位
        String direction=BaiDuMapUtil.GetDirection(Double.parseDouble(market.getLongitude()),
        		Double.parseDouble(market.getLatitude()),Double.parseDouble(mm.getLongitude()),
        		Double.parseDouble(mm.getLatitude()));
        double distance =BaiDuMapUtil.GetShortDistance(Double.parseDouble(market.getLongitude()),
        		Double.parseDouble(market.getLatitude()),Double.parseDouble(mm.getLongitude()),
        		Double.parseDouble(mm.getLatitude()));
        
        mm.setDistance(String.valueOf((int)distance));
        mm.setDirection(direction);
        //保存订单信息
		result=marketMenuMapper.add(mm);
		if(result>0)
		{
			List<MarketMenuRecord> dishs=mm.getDishs();
			for (MarketMenuRecord mmr : dishs) {
				flag=marketMenuRecordService.add(mmr);
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

	public int receive(MarketMenu mm) {
		synchronized(this){
			//判断订单是否已被接单
			MarketMenu m_1=marketMenuMapper.getByMid(mm.getMid());
			if(m_1.getStatus()!=1){
				return 2;
			}else{
				return marketMenuMapper.receive(mm)>0? 1:0;
			}
		} 
	}

	public boolean menuComplete(String mid, int evalShop, int evalServer,
			String myloginId) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        
		boolean flag=false;
		MarketMenu mm=new MarketMenu();
		mm.setMid(mid);
		mm.setUpdateBy(myloginId);
		mm.setUpdateDate(DateUtil.getCurrentDateStr());
		mm.setStatus(3);
		//修改订单的状态
		flag=updateStatus(mm);
		
		if(!flag){
			transactionManager.rollback(ts); 
			return flag;
		}
		//获取订单的信息
		MarketMenu m_1=marketMenuMapper.getByMid(mm.getMid());
		//获取送餐人的信息
		User user=userService.getByUId(m_1.getWid());
		if(user!=null){
			//修改用户的评价
			if(evalServer==0){
				user.setBadEvaluate(user.getBadEvaluate()+1);
			}else{
				user.setGoodEvaluate(user.getGoodEvaluate()+1);
			}
			flag=userService.updateEvaluate(user);
		}else{
			transactionManager.rollback(ts); 
			return false;
		}
		if(!flag){
			transactionManager.rollback(ts); 
			return flag;
		}
		//获取商家的信息
		Market market=marketService.getBySmid(m_1.getSmid());
		if(market!=null){
			//修改商家的评价
			if(evalShop==0){
				market.setBadEvaluate(market.getBadEvaluate()+1);
			}else{
				market.setGoodEvaluate(market.getGoodEvaluate()+1);
			}
			flag=marketService.updateEvaluate(market);
		}else{
			transactionManager.rollback(ts); 
			return false;
		}
		
		if(!flag){
			transactionManager.rollback(ts); 
		}else{
			transactionManager.commit(ts);
		}
		return flag;
	}

	public boolean updateStatus(MarketMenu m) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        int result=-1;
        boolean flag=false;
        
		if(m.getStatus()==3){
			m.setEndDate(DateUtil.getCurrentDateStr());
			
			MarketMenu m_1=marketMenuMapper.getByMid(m.getMid());
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
			
			float totalMoney=m_1.getDishsMoney()+m_1.getCarriageMoney()+m_1.getTaxesMoney()+
				m_1.getServiceMoney()+m_1.getTipMoney();
			Earning e=new Earning();
			e.setEid(eid);
			e.setWid(m_1.getWid());
			e.setMid(m_1.getMid());
			e.setCarriageMoney(m_1.getCarriageMoney());
			e.setTotalMoney(totalMoney);
			e.setType(2);
			e.setCreateBy(m.getUpdateBy());
			e.setCreateDate(DateUtil.getCurrentDateStr());
			result=earningService.add(e)==true?1:-1;
		}else if(m.getStatus()==4){
			m.setCancleDate(DateUtil.getCurrentDateStr());
			//新增订单取消退款记录
			MarketMenu m_1=marketMenuMapper.getByMid(m.getMid());
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
			c.setType(2);
			c.setContent("用户取消订单");
			c.setCreateBy(m.getUpdateBy());
			c.setCreateDate(DateUtil.getCurrentDateStr());
			result=complainMapper.add(c);
		}else if(m.getStatus()==5){
			m.setComplainDate(DateUtil.getCurrentDateStr());
			result=1;
		}
		else if(m.getStatus()==1){
			m.setCreateBy(DateUtil.getCurrentDateStr());
			result=1;
		}
		if(result>0)
		{
			flag=marketMenuMapper.updateStatus(m)>0? true:false;
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

	public boolean updateStatus_2(MarketMenu menu) {
		return marketMenuMapper.updateStatus(menu)>0? true:false;
	}
	public int getCount(String status, String smid, String searchStr,String startDate,String endDate) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getCount(status, smid, searchStr,startDate,endDate);
	}

	public List<MarketMenu> list(String status, String smid, String searchStr,String startDate,String endDate,
			int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return marketMenuMapper.list(status, smid, searchStr,startDate,endDate, startRow, pageSize);
	}

	public MarketMenu getByMid(String mid) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getByMid(mid);
	}

	public List<MarketMenu> getListByUid(int type, String searchStr,
			int curPage, int countPage) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getListByUid(type, searchStr, curPage, countPage);
	}

	public int getListByUidCount(int type, String searchStr) {
		// TODO Auto-generated method stub
		return marketMenuMapper.getListByUidCount(type, searchStr);
	}

	public List<MarketMenu> ListByUid(int type, String uid, int userType) {
		return marketMenuMapper.ListByUid(type, uid,userType);
	}
	public boolean updateDate(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.updateDate(mm)>0? true:false;
	}

	public List<MarketMenu> getNearList(double longitude, double latitude,
			float distance) {
		// TODO Auto-generated method stub
		double r = BaiDuMapUtil.DEF_R;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		List<MarketMenu> list=marketMenuMapper.getNearlist(minlon, maxlon, minlat, maxlat);
		return list;

	}

	public List<MarketMenu> getNearListSmid(String smid) {
		return marketMenuMapper.getNearListSmid(smid);
	}

	public void getListOverTime() {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        boolean result=false;
		//获取当前的时间
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		String mealEndDate =df.format(new Date());// new Date()为获取当前系统时间
		
		List<MarketMenu> list=marketMenuMapper.getListOverTime(mealEndDate);
		if(list.size()>0){
			//修改订单的状态
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String updateDate =df.format(new Date());// new Date()为获取当前系统时间
			result=marketMenuMapper.updateOverTime(mealEndDate,updateDate)>0?true:false;
			if(!result)
			{
				transactionManager.rollback(ts);
				return;
			}
			//新增延时退款操作记录
			for (MarketMenu menu : list) {
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
				c.setType(2);
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

}
