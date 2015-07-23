package com.dasher.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.elasticsearch.common.mvel2.conversion.ArrayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.ModelMap;

import com.dasher.mapper.ShopMapper;
import com.dasher.model.Market;
import com.dasher.model.Shop;
import com.dasher.service.ShopService;
import com.dasher.service.TimeService;
import com.dasher.util.BaiDuMapUtil;

public class ShopServiceImpl implements ShopService {

	private ShopMapper shopMapper;
	@Autowired
	private TimeService timeService;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;

	public ShopMapper getShopMapper() {
		return shopMapper;
	}

	public void setShopMapper(ShopMapper shopMapper) {
		this.shopMapper = shopMapper;
	}

	public boolean add(Shop s) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        
		boolean flag= shopMapper.add(s)>0? true:false;
		
		if(flag){
			String sid=s.getSid();
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
			
			flag=timeService.add(sid,s.getCreateBy()+"",week_1,flag_1,times_1,week_2,flag_2,times_2,week_3,flag_3,times_3,
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

	public boolean delete(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.delete(s)>0? true:false;
	}

	public Shop getBySid(String sid) {
		Calendar c = Calendar.getInstance();  
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}
		return shopMapper.getBySid(sid,dw);
	}

	public boolean update(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.update(s)>0? true:false;
	}

	public Shop getByName(String name) {
		// TODO Auto-generated method stub
		return shopMapper.getByName(name);
	}

	public List<Shop> getListByLati(double longitude, double latitude,float distance) {
		double r = BaiDuMapUtil.DEF_R;
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
		
		return shopMapper.getListByLati(minlon, maxlon, minlat, maxlat,dw);
	}
	public List<Shop> getListByMenu(double longitude, double latitude,float distance,String uid) {
		double r = BaiDuMapUtil.DEF_R;
		double dlng =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;
		double dlat = distance/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlon = longitude -dlng;
		double maxlon = longitude + dlng;
		return shopMapper.getListByMenu(minlon, maxlon, minlat, maxlat,uid);
	}

	public List<Shop> list(String searchStr, int startRow, int pageSize) {
		Calendar c = Calendar.getInstance();  
		int dw = c.get(Calendar.DAY_OF_WEEK);
		if(dw==1){
			dw=7;
		}else{
			dw=dw-1;
		}
		
		return shopMapper.list(searchStr, startRow, pageSize,dw);
	}

	public boolean updateLogo(Shop s) {
		// TODO Auto-generated method stub
		return shopMapper.updateLogo(s)>0? true:false;
	}

	public int getShopCount(String searchStr) {
		// TODO Auto-generated method stub
		return shopMapper.getShopCount(searchStr);
	}

	public List<Shop> menuList() {
		// TODO Auto-generated method stub
		return shopMapper.menuList();
	}

	public boolean updateEvaluate(Shop shop) {
		// TODO Auto-generated method stub
		return shopMapper.updateEvaluate(shop)>0? true:false;
	}

	public List<ModelMap> getShopType() {
		ModelMap model=null;
		List<ModelMap> map=new ArrayList<ModelMap>();
		
		List<String> list=shopMapper.getShopType();
		for (String val : list) {
			model=new ModelMap();
			model.put("typeTab", val);
			map.add(model);
		}
		return map;
	}
}
