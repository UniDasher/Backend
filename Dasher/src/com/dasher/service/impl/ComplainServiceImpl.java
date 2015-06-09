package com.dasher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.ComplainMapper;
import com.dasher.mapper.UserMapper;
import com.dasher.model.Complain;
import com.dasher.model.User;
import com.dasher.model.UserSettle;
import com.dasher.service.ComplainService;
import com.dasher.service.UserService;
import com.dasher.service.UserSettleService;
import com.dasher.util.DateUtil;

public class ComplainServiceImpl implements ComplainService {

	private ComplainMapper complainMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSettleService userSettleService;
	
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;

	public ComplainMapper getComplainMapper() {
		return complainMapper;
	}

	public void setComplainMapper(ComplainMapper complainMapper) {
		this.complainMapper = complainMapper;
	}

	public boolean add(Complain c) {
		// TODO Auto-generated method stub
		return complainMapper.add(c)>0? true:false;
	}

	public Complain getByComId(String comId,int type) {
		// TODO Auto-generated method stub
		return complainMapper.getByComId(comId,type);
	}

	public boolean update(Complain c) {
		// TODO Auto-generated method stub
		return complainMapper.update(c)>0? true:false;
	}

	public List<Complain> list(String searchStr,int status, int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return complainMapper.list(searchStr,status, startRow, pageSize);
	}

	public int getCount(int status) {
		// TODO Auto-generated method stub
		return complainMapper.getCount(status);
	}

	public boolean handle(Complain c) {
		//判断投诉处理是否为通过，并且返还金额大于0
		if(c.getComResult()==1&&c.getReturnMoney()>0){
			//添加事务处理
			DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
	        dtd.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
	        TransactionStatus ts = transactionManager.getTransaction(dtd);
			//根据投诉编号获取投诉的基本信息
			Complain com=complainMapper.getByComId(c.getComId(),c.getType());
			//获取投诉用户信息，和送餐者信息
			User user=userService.getByUId(com.getUid());
			float userBalance=user.getBalance();
			float curUserBalance=user.getBalance()+c.getReturnMoney();
			//更新用户的余额和收支记录
			userService.updateBalance(com.getUid(),curUserBalance);
			UserSettle us=new UserSettle();
            us.setWid(com.getUid());
            us.setOldBalance(userBalance);
            us.setType(1);
            us.setTypeDesc("投诉退款");
            us.setSettlePrice(c.getReturnMoney());
            us.setCurBalance(curUserBalance);
            us.setSettleNumberType("投诉单号");
            us.setSettleNumber(c.getComId());
            us.setSettleDesc("投诉处理，返回餐费");
            us.setCreateBy(c.getUpdateBy());
            us.setCreateDate(DateUtil.getCurrentDateStr());
			userSettleService.add(us);
			
			User waiter=null;
			float waiterBalance=0;
			float curWaiterBalance=0;
			if(com.getMenuStatus()==3&&c.getDeductMoney()>0){//判断订单是否为完成订单
				waiter=userService.getByUId(com.getWid());
				waiterBalance=waiter.getBalance();
				curWaiterBalance=waiter.getBalance()-c.getDeductMoney();
				
				userService.updateBalance(com.getWid(),curWaiterBalance);
				us=new UserSettle();
	            us.setWid(com.getWid());
	            us.setOldBalance(waiterBalance);
	            us.setType(2);
	            us.setTypeDesc("投诉扣款");
	            us.setSettlePrice(c.getDeductMoney());
	            us.setCurBalance(curWaiterBalance);
	            us.setSettleNumberType("投诉单号");
	            us.setSettleNumber(c.getComId());
	            us.setSettleDesc("投诉处理，扣除餐费");
	            us.setCreateBy(c.getUpdateBy());
	            us.setCreateDate(DateUtil.getCurrentDateStr());
				userSettleService.add(us);
				
			}
			boolean result=complainMapper.handle(c)>0? true:false;
			transactionManager.commit(ts);
			return result;
		}else{
			//投诉拒绝
			return complainMapper.handle(c)>0? true:false;
		}
	}

	public List<Complain> userComList(String loginId, int status) {
		return complainMapper.userComList(loginId,status);
	}
}
