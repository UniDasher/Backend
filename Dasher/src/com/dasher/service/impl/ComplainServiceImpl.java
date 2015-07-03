package com.dasher.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.ComplainMapper;
import com.dasher.model.Complain;
import com.dasher.model.Login;
import com.dasher.model.MarketMenu;
import com.dasher.model.Menu;
import com.dasher.model.User;
import com.dasher.model.UserSettle;
import com.dasher.service.ComplainService;
import com.dasher.service.LoginService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.MenuService;
import com.dasher.service.UserService;
import com.dasher.service.UserSettleService;
import com.dasher.util.DateUtil;
import com.dasher.util.FileUploadUtil;
import com.dasher.util.IGtPushUtil;
import com.dasher.util.ShowMsg;
import com.gexin.rp.sdk.base.IPushResult;

public class ComplainServiceImpl implements ComplainService {

	private ComplainMapper complainMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSettleService userSettleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MarketMenuService marketMenuService;
	@Autowired
	private LoginService loginService;
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
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        boolean result=false;
        
		//新增投诉，修改订单的状态
        if(c.getType()==1){
        	//订单为餐厅订单
        	Menu menu=new Menu();
        	menu.setMid(c.getMid());
        	menu.setStatus(5);
        	menu.setComplainDate(DateUtil.getCurrentDateStr());
        	menu.setUpdateBy(c.getUpdateBy());
        	menu.setUpdateDate(DateUtil.getCurrentDateStr());
        	result=menuService.updateStatus_2(menu);
        }else{
        	//订单为超市订单
        	MarketMenu menu=new MarketMenu();
        	menu.setMid(c.getMid());
        	menu.setStatus(5);
        	menu.setComplainDate(DateUtil.getCurrentDateStr());
        	menu.setUpdateBy(c.getUpdateBy());
        	menu.setUpdateDate(DateUtil.getCurrentDateStr());
        	result=marketMenuService.updateStatus_2(menu);
        }
        if(result){
        	result=complainMapper.add(c)>0? true:false;
        	if(result){
        		transactionManager.commit(ts);
        	}else{
        		transactionManager.rollback(ts);
        	}
        }else{
        	transactionManager.rollback(ts);
        }
		return result;
	}

	public Complain getByComId(String comId,int type) {
		return complainMapper.getByComId(comId,type);
	}

	public boolean update(Complain c) {
		return complainMapper.update(c)>0? true:false;
	}

	public List<Complain> list(String searchStr,int status, String startDate, String endDate, int startRow, int pageSize) {
		return complainMapper.list(searchStr,status,startDate,endDate, startRow, pageSize);
	}

	public int getCount(String searchStr, int status, String startDate, String endDate) {
		return complainMapper.getCount(searchStr,status,startDate,endDate);
	}

	public boolean handle(HttpServletRequest request,String fileName,Complain c) {
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        
        boolean result=false;
      //根据投诉编号获取投诉的基本信息
		Complain com=complainMapper.getByComId(c.getComId(),c.getType());
		/*
		//判断投诉处理是否为通过，并且返还金额大于0
		if(com.getComResult()==1&&com.getReturnMoney()>0){
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
            result=userSettleService.add(us);
			if(!result){
				transactionManager.rollback(ts);
				return false;
			}
            
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
	            result=userSettleService.add(us);
				
				if(!result){
					transactionManager.rollback(ts);
					return false;
				}
			}
		}*/
		
		//订单状态修改
		//新增投诉，修改订单的状态
        if(c.getType()==1){
        	//订单为餐厅订单
        	Menu menu=new Menu();
        	menu.setMid(com.getMid());
        	menu.setStatus(com.getComType()==1?8:(com.getComType()==2?7:9));
        	menu.setEndDate(DateUtil.getCurrentDateStr());
        	menu.setUpdateBy(c.getUpdateBy());
        	menu.setUpdateDate(DateUtil.getCurrentDateStr());
        	result=menuService.updateStatus_2(menu);
        	
        	//通知用户，订单退款处理
			//向用户推送信息（个推）
			String uid=menu.getUid();//用户的编号
			//判断用户是否登录
			Login log=loginService.getByLogId(uid);
			if(log!=null&&log.getAuthCode()!=""&&log.getAuthCode()!=null&&log.getIgtClientId()!=""){
				IPushResult ipr=IGtPushUtil.PushtoSingle(log.getIgtClientId(), 
						com.getComType()==1?ShowMsg.menuComplainDealTitle:(com.getComType()==2?ShowMsg.menuCancleDealTitle:ShowMsg.menuOverTimeDealTitle), 
						com.getComType()==1?ShowMsg.menuComplainDealContent:(com.getComType()==2?ShowMsg.menuCancleDealContent:ShowMsg.menuOverTimeDealContent));
			}
        }else{
        	//订单为超市订单
        	MarketMenu menu=new MarketMenu();
        	menu.setMid(com.getMid());
        	menu.setStatus(com.getComType()==1?8:(com.getComType()==2?7:9));
        	menu.setEndDate(DateUtil.getCurrentDateStr());
        	menu.setUpdateBy(c.getUpdateBy());
        	menu.setUpdateDate(DateUtil.getCurrentDateStr());
        	result=marketMenuService.updateStatus_2(menu);
        	
        	//通知用户，订单退款处理
			//向用户推送信息（个推）
			String uid=menu.getUid();//用户的编号
			//判断用户是否登录
			Login log=loginService.getByLogId(uid);
			if(log!=null&&log.getAuthCode()!=""&&log.getAuthCode()!=null&&log.getIgtClientId()!=""){
				IPushResult ipr=IGtPushUtil.PushtoSingle(log.getIgtClientId(), 
						com.getComType()==1?ShowMsg.menuComplainDealTitle:(com.getComType()==2?ShowMsg.menuCancleDealTitle:ShowMsg.menuOverTimeDealTitle), 
						com.getComType()==1?ShowMsg.menuComplainDealContent:(com.getComType()==2?ShowMsg.menuCancleDealContent:ShowMsg.menuOverTimeDealContent));
			}
        }
        
        
        if(result){
        	c.setComType(com.getComType());
        	//退款处理，导出excel文件
            try {
				FileUploadUtil.createRefundExcel(request,fileName,c,com);
			} catch (IOException e) {
				transactionManager.rollback(ts);
				e.printStackTrace();
			}
        	result=complainMapper.handle(c)>0? true:false;
        	if(result){
        		transactionManager.commit(ts);
        	}else{
        		transactionManager.rollback(ts);
        	}
        }else{
        	transactionManager.rollback(ts);
        }
		return result;
	}

	public List<Complain> userComList(String loginId, int status) {
		return complainMapper.userComList(loginId,status);
	}
}
