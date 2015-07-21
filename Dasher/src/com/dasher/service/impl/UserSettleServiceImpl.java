package com.dasher.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.mapper.UserSettleMapper;
import com.dasher.model.Login;
import com.dasher.model.ServerSettle;
import com.dasher.model.User;
import com.dasher.model.UserSettle;
import com.dasher.service.LoginService;
import com.dasher.service.ServerSettleService;
import com.dasher.service.UserService;
import com.dasher.service.UserSettleService;
import com.dasher.util.DateUtil;
import com.dasher.util.FileUploadUtil;
import com.dasher.util.IGtPushUtil;
import com.dasher.util.ShowMsg;
import com.gexin.rp.sdk.base.IPushResult;

public class UserSettleServiceImpl implements UserSettleService {

	private UserSettleMapper userSettleMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private ServerSettleService serverSettleService;
	@Autowired
	private LoginService loginService;
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;
	
	public UserSettleMapper getUserSettleMapper() {
		return userSettleMapper;
	}
	public void setUserSettleMapper(UserSettleMapper userSettleMapper) {
		this.userSettleMapper = userSettleMapper;
	}
	public boolean settleUser(HttpServletRequest request,String uid,String myloginId,String fileName) {
		try{
			//添加事务处理
			DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
	        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	        TransactionStatus ts = transactionManager.getTransaction(dtd);
	        //获取结算的用户
	        User user=userService.getByUId(uid);
	        
	        if(user!=null){
	        	//保存excel文件
		        List<User> userList=new ArrayList<User>();
		        userList.add(user);
		        FileUploadUtil.createExcel(request,fileName,userList);
		        //用户结算记录新增
		        UserSettle us=new UserSettle();
	        	us.setWid(user.getUid());
	            us.setOldBalance(user.getBalance());
	            us.setType(2);
	            us.setTypeDesc("用户结算");
	            us.setSettlePrice(user.getBalance());
	            us.setCurBalance(0);
	            us.setSettleNumberType("结算账号："+user.getBankType());
	            us.setSettleNumber(user.getBankAccount());
	            us.setSettleDesc("用户账户余额结算");
	            us.setCreateBy(myloginId);
	            us.setCreateDate(DateUtil.getCurrentDateStr());
	            userService.updateBalance(user.getUid(),0);
	            userSettleMapper.add(us);
	            
	            //系统结算
	            
	            ServerSettle ss=new ServerSettle();
	            ss.setUid(user.getUid());
	            ss.setType(2);
	            ss.setTypeDesc("用户结算");
	            ss.setOldBalance(0);
	            ss.setSettlePrice(user.getBalance());
	            ss.setCurBalance(0);
	            ss.setSettleNumberType("结算账号："+user.getBankType());
	            ss.setSettleNumber(user.getBankAccount());
	            ss.setSettleDesc("用户账户余额结算");
	            ss.setCreateBy(myloginId);
	            ss.setCreateDate(DateUtil.getCurrentDateStr());
	            serverSettleService.add(ss); 
			}else{
				return false;
			}
	        Login log=loginService.getByLogId(uid);
			if(log!=null&&log.getAuthCode()!=""&&log.getAuthCode()!=null&&log.getIgtClientId()!=""&&log.getIgtClientId()!=null){
				IPushResult ipr=IGtPushUtil.PushtoSingleDeal(log.getIgtClientId(),ShowMsg.serverSettleIndex);
			}
	        transactionManager.commit(ts);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	
	public boolean settleUserAll(HttpServletRequest request,String searchStr,String myloginId,String fileName) {
		try{
			//添加事务处理
			DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
	        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	        TransactionStatus ts = transactionManager.getTransaction(dtd);
	        //获取结算的用户列表
	        List<User> userList=userService.settleList(searchStr);
	        if(userList==null||userList.size()<=0){
	        	transactionManager.commit(ts);
	        	return false;
	        }
	        //保存excel文件
	        FileUploadUtil.createExcel(request,fileName,userList);
	        //用户结算记录新增
	        for (User user : userList) {
	        	UserSettle us=new UserSettle();
	        	us.setWid(user.getUid());
	            us.setOldBalance(user.getBalance());
	            us.setType(2);
	            us.setTypeDesc("用户结算");
	            us.setSettlePrice(user.getBalance());
	            us.setCurBalance(0);
	            us.setSettleNumberType("结算账号："+user.getBankType());
	            us.setSettleNumber(user.getBankAccount());
	            us.setSettleDesc("用户账户余额结算");
	            us.setCreateBy(myloginId);
	            us.setCreateDate(DateUtil.getCurrentDateStr());
	            userService.updateBalance(user.getUid(),0);
	            userSettleMapper.add(us);
	          //系统结算
	            ServerSettle ss=new ServerSettle();
	            ss.setUid(user.getUid());
	            ss.setType(2);
	            ss.setTypeDesc("用户结算");
	            ss.setOldBalance(0);
	            ss.setSettlePrice(user.getBalance());
	            ss.setCurBalance(0);
	            ss.setSettleNumberType("结算账号："+user.getBankType());
	            ss.setSettleNumber(user.getBankAccount());
	            ss.setSettleDesc("用户账户余额结算");
	            ss.setCreateBy(myloginId);
	            ss.setCreateDate(DateUtil.getCurrentDateStr());
	            serverSettleService.add(ss);
	            
	            Login log=loginService.getByLogId(user.getUid());
				if(log!=null&&log.getAuthCode()!=""&&log.getAuthCode()!=null&&log.getIgtClientId()!=""&&log.getIgtClientId()!=null){
					IPushResult ipr=IGtPushUtil.PushtoSingleDeal(log.getIgtClientId(),ShowMsg.serverSettleIndex);
				}
			
			}
	        transactionManager.commit(ts);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public int getCount(String searchStr, String startDate, String endDate) {
		return userSettleMapper.getCount(searchStr,startDate,endDate);
	}

	public List<UserSettle> list(String searchStr, String startDate, String endDate, int startRow, int pageSize) {
		return userSettleMapper.list(searchStr,startDate,endDate, startRow, pageSize);
	}
	
	public boolean add(UserSettle us) {
		// TODO Auto-generated method stub
		return userSettleMapper.add(us)>0? true:false;
	}

	public boolean update(UserSettle us) {
		// TODO Auto-generated method stub
		return userSettleMapper.update(us)>0? true:false;
	}
	public List<UserSettle> getListByWid(String wid) {
		// TODO Auto-generated method stub
		return userSettleMapper.getListByWid(wid);
	}
	public List<UserSettle> getSettleByWid(String wid, String startDate,
			String endDate) {
		SimpleDateFormat sdfLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startDate=startDate+" 00:00:00";
	    endDate=endDate+" 24:00:00";
		try {
			startDate=sdfLong.format(sdfLong.parse(startDate));
			endDate=sdfLong.format(sdfLong.parse(endDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userSettleMapper.getSettleByWid(wid,startDate,endDate);
	}
}
