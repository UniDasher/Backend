package com.dasher.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.dasher.chat.Constants;
import com.dasher.chat.httpclient.apidemo.EasemobIMUsers;
import com.dasher.mapper.UserMapper;
import com.dasher.model.Login;
import com.dasher.model.User;
import com.dasher.service.LoginService;
import com.dasher.service.UserService;
import com.dasher.util.DateUtil;
import com.dasher.util.MyMD5Util;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserServiceImpl implements UserService {
	@Autowired
	private LoginService loginService;
	
	@Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;
	private UserMapper userMapper;
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public boolean addUser(User u) {
		//添加事务处理
		DefaultTransactionDefinition dtd = new DefaultTransactionDefinition();
        dtd.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts = transactionManager.getTransaction(dtd);
        boolean result=false;
		//用户注册
	    result= userMapper.addUser(u)>0? true:false;
	    if(!result){
	    	//用户注册失败
	    	transactionManager.rollback(ts);  
	    	return result;
	    }
	    
	    User user=getUserByTel(u.getMobilePhone());
	    
		//注册用户的环信账号
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",user.getUid());
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
        if(createNewIMUserSingleNode==null){
        	transactionManager.rollback(ts);  
	    	return result;
        }
        
        JSONObject jsonObject=new JSONObject(createNewIMUserSingleNode);
        JSONArray dishArray;
		try {
			dishArray = jsonObject.getJSONArray("entities");
			if(dishArray.length()==1){
	        	//用户环信注册成功
	        	JSONObject dishObj=dishArray.getJSONObject(0);
	        	String uuid=dishObj.getString("uuid");
	        	//保存用户环信账号
	        	
	        	Login l=new Login();
	    		l.setLoginId(user.getUid());
	    		l.setPushClientId(uuid);
	    		l.setType(1);
	    		l.setLoginTime(DateUtil.getCurrentDateStr());
	        	
	    		result=loginService.updateUUID(l);
	    		
	        }else{
	        	//用户环信注册失败
	        	transactionManager.rollback(ts);  
	        	return result;
	        }
		} catch (JSONException e) {
			e.printStackTrace();
			transactionManager.rollback(ts);  
        	return false;
		}
		if(!result){
	    	transactionManager.rollback(ts);
	    }else{
	    	transactionManager.commit(ts);
	    }
    	return result;
	}

	public boolean delete(User u) {
		// TODO Auto-generated method stub
		return userMapper.delete(u)>0? true:false;
	}

	public User getByUId(String uid) {
		// TODO Auto-generated method stub
		return userMapper.getByUId(uid);
	}

	public boolean update(User u) {
		// TODO Auto-generated method stub
		return userMapper.update(u)>0? true:false;
	}

	public User getUserByTel(String mobilePhone) {
		// TODO Auto-generated method stub
		return userMapper.getUserByTel(mobilePhone);
	}

	public int userLoin(String mobilePhone, String pwd) {
		// TODO Auto-generated method stub
		int flag=-1;
		User u=userMapper.getUserByTel(mobilePhone);
		if(u==null)
		   flag=1;
		else
		{
			try {
				if(!MyMD5Util.validPassword(pwd, u.getPassword()))
					flag=2;
				else
					flag=0;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean updatePwd(User u) {
		// TODO Auto-generated method stub
		return userMapper.updatePwd(u)>0? true:false;
	}

	public int getPwd(String uid, String pwd) {
		// TODO Auto-generated method stub
		int flag=-1;
		User u=userMapper.getByUId(uid);
		if(u!=null)
		{
			try {
				if(!MyMD5Util.validPassword(pwd,u.getPassword()))
					flag=2;
				else
					flag=0;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			flag=1;
		
		return flag;
	}

	public boolean userApply(User u) {
		// TODO Auto-generated method stub
		return userMapper.userApply(u)>0? true:false;
	}

	public List<User> searchUser(int type, String searchStr, int startRow,
			int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.searchUser(type, searchStr, startRow, pageSize);
	}
	
	public int getUserByStatus2(int type,String searchStr) {
		// TODO Auto-generated method stub
		return userMapper.getUserByStatus2(type,searchStr);
	}

	public boolean updateLogo(User u) {
		// TODO Auto-generated method stub
		return userMapper.updateLogo(u)>0? true:false;
	}

	public boolean updateUserName(User u) {
		// TODO Auto-generated method stub
		return userMapper.updateUserName(u)>0? true:false;
	}

	public boolean updatePhone(User u) {
		// TODO Auto-generated method stub
		return userMapper.updatePhone(u)>0? true:false;
	}

	public boolean updateEmail(User u) {
		// TODO Auto-generated method stub
		return userMapper.updateEmail(u)>0? true:false;
	}

	public List<User> balanceList(String searchStr,int startRow, int pageSize) {
		// TODO Auto-generated method stub
		return userMapper.balanceList(searchStr,startRow, pageSize);
	}

	public boolean updateBalance(String uid, float curUserBalance) {
		// TODO Auto-generated method stub
		return userMapper.updateBalance(uid,curUserBalance)>0? true:false;
	}

	public boolean forgetPwd(User u) {
		// TODO Auto-generated method stub
		return userMapper.forgetPwd(u)>0? true:false;
	}

	public int balanceListCount(String searchStr) {
		// TODO Auto-generated method stub
		return userMapper.balanceListCount(searchStr);
	}

	public List<User> settleList(String searchStr) {
		return userMapper.settleList(searchStr);
	}

	public boolean serveApply(User u) {
		// TODO Auto-generated method stub
		return userMapper.serveApply(u)>0? true:false;
	}

	public boolean cheakUser(User u) {
		return userMapper.cheakUser(u)>0? true:false;
	}

	public List<User> applyList() {
		return userMapper.applyList();
	}

	public boolean updateStatus(User u) {
		return userMapper.updateStatus(u)>0? true:false;
	}

	public boolean updateEvaluate(User user) {
		return userMapper.updateEvaluate(user)>0? true:false;
	}
}
