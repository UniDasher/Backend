package com.dasher.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.MarketMapper;
import com.dasher.model.Market;
import com.dasher.service.MarketService;
import com.dasher.service.TimeService;

public class MarketServiceImpl implements MarketService {

	private MarketMapper marketMapper;
	@Autowired
	private TimeService timeService;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;
	
	public MarketMapper getMarketMapper() {
		return marketMapper;
	}

	public void setMarketMapper(MarketMapper marketMapper) {
		this.marketMapper = marketMapper;
	}
	
	public boolean add(Market m) {
		// TODO Auto-generated method stub
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        
		boolean flag= marketMapper.add(m)>0? true:false;
		
		if(flag){
			String smid=m.getSmid();
			String week_1="1";
			String flag_1="1";
			String times_1="";
			
			String week_2="2";
			String flag_2="1";
			String times_2="";
			
			String week_3="3";
			String flag_3="1";
			String times_3="";
			
			String week_4="4";
			String flag_4="1";
			String times_4="";
			
			String week_5="5";
			String flag_5="1";
			String times_5="";
			
			String week_6="6";
			String flag_6="1";
			String times_6="";
			
			String week_7="7";
			String flag_7="1";
			String times_7="";
			
			flag=timeService.add(smid,m.getCreateBy()+"",week_1,flag_1,times_1,week_2,flag_2,times_2,week_3,flag_3,times_3,
					week_4,flag_4,times_4,week_5,flag_5,times_5,week_6,flag_6,times_6,week_7,flag_7,times_7);
			
		}
		if(flag==true)
		{
			transactionManager.commit(ts);
		}
		else
		{
			transactionManager.rollback(ts);  
		}
		return flag;

	}

	public boolean delete(Market m) {
		// TODO Auto-generated method stub
		return marketMapper.delete(m)>0? true:false;
	}

	public Market getBySmid(String smid) {
		Calendar c = Calendar.getInstance();  
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}
		return marketMapper.getBySmid(smid,dw);
	}

	public boolean update(Market m) {
		// TODO Auto-generated method stub
		return marketMapper.update(m)>0? true:false;
	}

	public Market getByMarketName(String name) {
		// TODO Auto-generated method stub
		return marketMapper.getByMarketName(name);
	}

	public int getListCount(String searchStr) {
		// TODO Auto-generated method stub
		return marketMapper.getListCount(searchStr);
	}

	public List<Market> list(String searchStr, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();  
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}

		return marketMapper.list(searchStr, startRow, pageSize,dw);
	}

	public List<Market> menuList() {
		// TODO Auto-generated method stub
		return marketMapper.menuList();
	}

	public List<Market> getNearList(double longitude, double latitude,
			float distance) {
		
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		
		Calendar c = Calendar.getInstance();  
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}
		
		List<Market> list=marketMapper.getNearlist(minlon, maxlon, minlat, maxlat,dw);
		return list;
	}

	public List<Market> getNearListMenu(double longitude, double latitude,
			float distance) {
		double r = 6371;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		List<Market> list=marketMapper.getNearListMenu(minlon, maxlon, minlat, maxlat);
		return list;
	}

	public boolean updateEvaluate(Market market) {
		return marketMapper.updateEvaluate(market)>0? true:false;
	}
}
