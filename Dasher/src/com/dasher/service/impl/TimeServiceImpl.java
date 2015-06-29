package com.dasher.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.TimeMapper;
import com.dasher.model.Time;
import com.dasher.service.TimeService;
import com.dasher.util.DateUtil;

public class TimeServiceImpl implements TimeService {

	private TimeMapper timeMapper;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;
	public TimeMapper getTimeMapper() {
		return timeMapper;
	}

	public void setTimeMapper(TimeMapper timeMapper) {
		this.timeMapper = timeMapper;
	}

	public boolean add(String sid,String myloginId,String week_1, String flag_1, String times_1,
			String week_2, String flag_2, String times_2, String week_3,
			String flag_3, String times_3, String week_4, String flag_4,
			String times_4, String week_5, String flag_5, String times_5,
			String week_6, String flag_6, String times_6, String week_7,
			String flag_7, String times_7) {
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        boolean flag=false;
		List<Time> list=new ArrayList<Time>();
		list.add(createTime(sid,myloginId,week_1,flag_1,times_1));
		list.add(createTime(sid,myloginId,week_2,flag_2,times_2));
		list.add(createTime(sid,myloginId,week_3,flag_3,times_3));
		list.add(createTime(sid,myloginId,week_4,flag_4,times_4));
		list.add(createTime(sid,myloginId,week_5,flag_5,times_5));
		list.add(createTime(sid,myloginId,week_6,flag_6,times_6));
		list.add(createTime(sid,myloginId,week_7,flag_7,times_7));
		
		//查询数据库，判断商家的营业时间是否已添加
		List<Time> list1=timeMapper.getBySid(sid);
		if(list1==null||list1.size()<=0){
			for (Time md : list) {
				flag=timeMapper.add(md)>0?true:false;
				if(!flag){
					break;
				}
			}
		}else{
			for (Time md : list) {
				flag=timeMapper.update(md)>0?true:false;
				if(!flag){
					break;
				}
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
		return flag;
	}

	private Time createTime(String sid,String myloginId,String weeks, String flag, String time) {
		Time t=new Time();
		if("0".equals(flag)&&"".equals(time)){
			t.setTime1("");
			t.setTime2("");
			t.setTime3("");
			t.setTime4("");
			t.setTime5("");
		}else{
			String strs[]=time.split(",");
			
			t.setTime1(strs.length>=1?strs[0]:"");
			t.setTime2(strs.length>=2?strs[1]:"");
			t.setTime3(strs.length>=3?strs[2]:"");
			t.setTime4(strs.length>=4?strs[3]:"");
			t.setTime5(strs.length>=5?strs[4]:"");
		}
		
		t.setWeeks(Integer.parseInt(weeks));
		t.setFlag(Integer.parseInt(flag));
		t.setSid(sid);
		t.setSubscribe("");
		t.setCreateBy(myloginId);
		t.setCreateDate(DateUtil.getCurrentDateStr());
		return t;
	}

	public List<Time> getBySId(String sid) {
		return timeMapper.getBySid(sid);
	}

}
