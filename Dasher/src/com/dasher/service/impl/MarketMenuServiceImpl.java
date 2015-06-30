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

import com.dasher.mapper.MarketMenuMapper;
import com.dasher.model.Earning;
import com.dasher.model.Market;
import com.dasher.model.MarketMenu;
import com.dasher.model.MarketMenuRecord;
import com.dasher.service.EarningService;
import com.dasher.service.MarketCommodityService;
import com.dasher.service.MarketMenuRecordService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.MarketService;
import com.dasher.util.BaiDuMapUtil;
import com.dasher.util.DateUtil;

public class MarketMenuServiceImpl implements MarketMenuService {

	private MarketMenuMapper marketMenuMapper;
	@Autowired
	private MarketMenuRecordService marketMenuRecordService;
	@Autowired
	private MarketCommodityService marketCommodityService;
	@Autowired
	private MarketService marketService;
	@Autowired
	private EarningService earningService;
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
        mm.setDistance(String.valueOf(distance));
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

	public boolean receive(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.receive(mm)>0? true:false;
	}

	public boolean updateStatus(MarketMenu mm) {
		// TODO Auto-generated method stub
		 boolean flag=false;
			if(mm.getStatus()==3)
			{
				//添加事务处理
				DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
		        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		        TransactionStatus ts = transactionManager.getTransaction(dtd);
		        
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
				Date date=new Date();
				String strs[]=sdf.format(date).split("-");
				String eid="";
				for(int i=0;i<strs.length;i++)
				{
					eid=eid+strs[i];
				}
				
				float totalMoney=mm.getMenuCount()*mm.getDishsMoney()+mm.getCarriageMoney()+mm.getTaxesMoney()+mm.getServiceMoney()+mm.getTipMoney();
				Earning e=new Earning();
				e.setEid(eid);
				e.setWid(mm.getWid());
				e.setMid(mm.getMid());
				e.setCarriageMoney(mm.getCarriageMoney());
				e.setTotalMoney(totalMoney);
				e.setType(0);
				e.setCreateBy(mm.getUpdateBy());
				e.setCreateDate(DateUtil.getCurrentDateStr());
				flag=earningService.add(e);
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
				flag=marketMenuMapper.updateStatus(mm)>0? true:false;
			}
			
			return flag;

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

	public List<MarketMenu> ListByUid(String type, String searchStr) {
		// TODO Auto-generated method stub
		return marketMenuMapper.ListByUid(type, searchStr);
	}

	public boolean updateDate(MarketMenu mm) {
		// TODO Auto-generated method stub
		return marketMenuMapper.updateDate(mm)>0? true:false;
	}

	public List<MarketMenu> getNearList(float longitude, float latitude,
			float distance) {
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
		List<MarketMenu> list=marketMenuMapper.getNearlist(minlon, maxlon, minlat, maxlat);
		return list;

	}

}
