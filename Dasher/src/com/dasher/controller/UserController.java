package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.chat.Constants;
import com.dasher.chat.httpclient.apidemo.EasemobChatMessage;
import com.dasher.chat.httpclient.apidemo.EasemobIMUsers;
import com.dasher.model.*;
import com.dasher.service.*;
import com.dasher.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

import org.json.*;
@Controller
public class UserController extends MyController {

	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ComplainDealService complainDealService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/user/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String password="";
	    String mobilePhone="";
	    String phoneCode="";
	    String nickName="";
		try {
			jsonObject = new JSONObject(JSONStr);
			password = jsonObject.getString("password");
			mobilePhone=jsonObject.getString("mobilePhone");
			phoneCode=jsonObject.getString("phoneCode");
			nickName=jsonObject.getString("nickName");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		
		//参数判断
		if(mobilePhone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=1;
		}
		else if(password.length()<6||password.length()>30)
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=1;
		}
		else if(nickName==""||nickName.length()>10)
		{
			resultDesc=ShowMsg.NickNameNull;
			resultCode=2;
		}
		else
		{
			//手机格式验证
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			
			if(matcher2.matches()==false)
			{
				resultCode=1;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				//判断验证码是否正确
//				ComplainDeal cd=complainDealService.getByTel(mobilePhone);
//				if(!cd.getPhoneCode().equals(phoneCode))
//				{
//					resultCode=2;
//					resultDesc=ShowMsg.phoneCodeErr;
//					model.put("resultCode", resultCode);	
//					model.put("resultDesc", resultDesc);
//					return model;
//				}
			
				User user=userService.getUserByTel(mobilePhone);
				if(user==null)
				{
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
					Date date=new Date();
					String strs[]=sdf.format(date).split("-");
					String uid="";
					for(int i=0;i<strs.length;i++)
					{
						uid=uid+strs[i];
					}
					UUID uuid=UUID.randomUUID();
					String str[]=uuid.toString().split("-");
					String salt="";
					for(int i=0;i<str.length;i++)
					{
						salt=salt+str[i];
					}

					User u=new User();
					u.setUid(uid);
					u.setAccount("");
					try {
						u.setPassword(MyMD5Util.getEncryptedPwd(password));
					} catch (Exception e) {
						e.printStackTrace();
					}
					u.setSalt(salt);
					u.setNickName(nickName);
					u.setFirstName("");
					u.setLastName("");
					u.setEqumentNumber("");
					u.setMobilePhone(mobilePhone);
					u.setEmail("");
					u.setAddress("");
					u.setLongitude("");
					u.setLatitude("");
					u.setLogo("/upload/user/images/default.jpg");
					u.setCreateDate(DateUtil.getCurrentDateStr());
					u.setBankAccount("");
					u.setBankType("");
					result=userService.addUser(u);
					if(result==true)
					{
						resultCode=0;
						resultDesc=ShowMsg.addSuc;
						User us=userService.getUserByTel(mobilePhone);
						model.put("uid", us.getUid());
					}
					else
					{
						resultCode=1;
						resultDesc=ShowMsg.addFail;
					}
				}
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.addRepeat;
				}
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	

	@RequestMapping("phone/user/login")
	@ResponseBody
	protected Object login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String password="";
	    String mobilePhone="";
		try {
			jsonObject = new JSONObject(JSONStr);
			password = jsonObject.getString("password");
			mobilePhone=jsonObject.getString("mobilePhone");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		
		if(mobilePhone=="")
		{
			resultDesc=ShowMsg.userNull;
			resultCode=1;
		}
		else if(password.length()>5&&password.length()<31)
		{
			int flag=userService.userLoin(mobilePhone, password);
			if(flag==1)
			{
				resultDesc=ShowMsg.mobilePhoneNull;
				resultCode=2;
			}
			else if(flag==2)
			{
				resultDesc=ShowMsg.pwdErr;
				resultCode=2;
			}
			else if(flag==0)
			{
				User us=userService.getUserByTel(mobilePhone);
				resultDesc=ShowMsg.loginSuc;
				resultCode=0;
				model.put("uid", us.getUid());
//				model.put("cid", l.getPushClientId());
				model.put("name", us.getNickName());
				model.put("logo", us.getLogo());
				//获取authCode
				model.put("authCode", loginService.userHandleLogin(us.getUid()));
//				Login l=loginService.getByLogId(us.getUid());
//				//环信账号登陆
//				Map<String,String> map = EasemobUtil.imUserLogin(us.getUid());
//				if(map!=null&&"200".equals(map.get("statusCode"))&&l.getPushClientId().equals(map.get("uuid"))){
//					resultDesc=ShowMsg.loginSuc;
//					resultCode=0;
//					model.put("uid", us.getUid());
//					model.put("cid", l.getPushClientId());
//					model.put("name", us.getNickName());
//					model.put("logo", us.getLogo());
//				}else{
//					resultDesc=ShowMsg.ImLoginFail;
//					resultCode=1;
//				}
			}
			else
			{
				resultDesc=ShowMsg.loginFail;
				resultCode=1;
			}
		}
		else
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=1;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;

	}
	
	@RequestMapping("phone/user/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
	    String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);

		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String uid=getString(request,"uid");
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			User u=userService.getByUId(uid);
			if(u!=null)
			{
				u.setSalt("");
				u.setPassword("");
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", u);
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("phone/user/info/balance")
	@ResponseBody
	protected Object userBalance(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
	    String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String uid=getString(request,"uid");
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			User u=userService.getByUId(uid);
			if(u!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("balance", u.getBadEvaluate());
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/update/nickname")
	@ResponseBody
	protected Object updateName(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    //String uid="";
	    String nickName="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			//uid=jsonObject.getString("uid");
			nickName=jsonObject.getString("nickName");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		
		if(nickName=="")
		{
			resultDesc=ShowMsg.NickNameNull;
			resultCode=2;
		}
		else
		{
			//用户名修改
			User u=new User();
			u.setNickName(nickName);
			u.setUid(myloginId);
			result=userService.updateUserName(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.updateSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.updateFail;
			}
			
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("phone/user/update/truename")
	@ResponseBody
	protected Object updateTrueName(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String firstName="";
	    String lastName="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			
			firstName=jsonObject.getString("firstName");
			lastName=jsonObject.getString("lastName");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		
		if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=2;
		}
		else
		{
			//用户名修改
			User u=new User();
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setUid(myloginId);
			result=userService.updateTrueName(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.updateSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.updateFail;
			}
			
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/update/phone")
	@ResponseBody
	protected Object updatePhone(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String mobilePhone="";
	    String phoneCode="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			uid=jsonObject.getString("uid");
			mobilePhone=jsonObject.getString("mobilePhone");
			phoneCode=jsonObject.getString("phoneCode");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}else if(mobilePhone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=1;
		}else{
			//手机格式验证
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			
			if(matcher2.matches()==false)
			{
				resultCode=1;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				//验证码判断
//				ComplainDeal cd=complainDealService.getByTel(mobilePhone);
//				if(!cd.getPhoneCode().equals(phoneCode))
//				{
//					resultCode=2;
//					resultDesc=ShowMsg.phoneCodeErr;
//					model.put("resultCode", resultCode);	
//					model.put("resultDesc", resultDesc);
//					return model;
//				}
				//判断手机号是否存在
				User u=userService.getUserByTel(mobilePhone);
				if(u==null)
				{
					User user=new User();
					user.setMobilePhone(mobilePhone);
					user.setUid(uid);
					result=userService.updatePhone(user);
					
					if(result==true)
					{
						resultCode=0;
						resultDesc=ShowMsg.updateSuc;
					}
					else
					{
						resultCode=1;
						resultDesc=ShowMsg.updateFail;
					}
				}
				else
				{
					resultDesc=ShowMsg.phoneRepeat;
					resultCode=1;
				}
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/update/email")
	@ResponseBody
	protected Object updateEmail(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String email="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			uid=jsonObject.getString("uid");
			email=jsonObject.getString("email");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}else if(email=="")
		{
			resultDesc=ShowMsg.EmailNull;
			resultCode=2;
		}
		else
		{
			//邮箱验证
			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
			Matcher matcher=pattern.matcher(email);
			if(matcher.matches()==false)
			{
				resultCode=1;
				resultDesc=ShowMsg.emailErr;
			}
			else
			{
				//邮箱修改
				User u=new User();
				u.setUid(uid);
				u.setEmail(email);
				result=userService.updateEmail(u);
				if(result==true)
				{
					resultCode=0;
					resultDesc=ShowMsg.updateSuc;
				}
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.updateFail;
				}
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/update/account")
	@ResponseBody
	protected Object updateBankAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    //String uid="";
	    String bankAccount="";
	    String bankType="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			//uid=jsonObject.getString("uid");
			bankAccount=jsonObject.getString("bankAccount");
			bankType=jsonObject.getString("bankType");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(bankAccount=="")
		{
			resultDesc=ShowMsg.bankAccountNull;
			resultCode=2;
		}
		else if(bankType=="")
		{
			resultDesc=ShowMsg.bankTypeNull;
			resultCode=2;
		}
		else
		{
			User u=new User();
			u.setUid(myloginId);
			u.setBankAccount(bankAccount);
			u.setBankType(bankType);
			result=userService.update(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.updateSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.updateFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/update/file")
	@ResponseBody
	protected Object updatefile(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		Map<String,String> dataMap=FileUploadUtil.uploadFile(request, "/upload/user/images");
		if(dataMap==null){
			resultCode=1;
			resultDesc=ShowMsg.imageUploadFail;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		
		String authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
		
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String logo=dataMap.get("fileName");
		
		if("false".equals(logo))
		{
			resultCode=2;
			resultDesc=ShowMsg.imageUploadFail;
		}
		else
		{
			User u=new User();
			u.setUid(myloginId);
			u.setLogo("/upload/user/images/"+logo);
			result=userService.updateLogo(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.updateSuc;
				model.put("fileName", "/upload/user/images/"+logo);
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.updateFail;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("phone/user/pwd/update")
	@ResponseBody
	protected Object pwdupdate(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String oldPassword="";
	    String newPassword="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			uid=jsonObject.getString("uid");
			oldPassword=jsonObject.getString("oldPassword");
			newPassword=jsonObject.getString("newPassword");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(uid==""||oldPassword==""||newPassword=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(newPassword.length()<6)
		{
			resultDesc=ShowMsg.newPwdLength;
			resultCode=1;
		}
		else
		{
			User u=new User();
			u.setUid(uid);
			try {
				u.setPassword(MyMD5Util.getEncryptedPwd(newPassword));
			} catch (Exception e) {
				e.printStackTrace();
			}
			int flag=userService.getPwd(uid, oldPassword);
			if(flag==1)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else if(flag==2)
			{
				resultDesc=ShowMsg.oldpwdErr;
				resultCode=1;
			}
			else if(flag==0)
			{
				result=userService.updatePwd(u);
				if(result==true)
				{
					resultCode=0;
					resultDesc=ShowMsg.updateSuc;
				}
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.updateFail;
				}
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/pwd/forget")
	@ResponseBody
	protected Object pwdForget(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String mobilePhone="";
	    String phoneCode="";
	    String newPassword="";
		try {
			jsonObject = new JSONObject(JSONStr);
			mobilePhone=jsonObject.getString("mobilePhone");
			phoneCode=jsonObject.getString("phoneCode");
			newPassword=jsonObject.getString("newPassword");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher2=pattern2.matcher(mobilePhone);
		
		
		if(mobilePhone==""||phoneCode==""||newPassword=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(newPassword.length()<6)
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=1;
		}
		else if(matcher2.matches()==false)
		{
			resultCode=1;
			resultDesc=ShowMsg.mobilePhoneErr;
		}
		else
		{
			//判断手机验证码是否正确
//			ComplainDeal cd=complainDealService.getByTel(mobilePhone);
//			if(!cd.getPhoneCode().equals(phoneCode))
//			{
//				resultCode=2;
//				resultDesc=ShowMsg.phoneCodeErr;
//				model.put("resultCode", resultCode);	
//				model.put("resultDesc", resultDesc);
//				return model;
//			}
			
			//修改密码
			User u=new User();
			u.setMobilePhone(mobilePhone);
			try {
				u.setPassword(MyMD5Util.getEncryptedPwd(newPassword));
			} catch (Exception e) {
				e.printStackTrace();
			}
			result=userService.forgetPwd(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.updateSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.pwdUpdateFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/status")
	@ResponseBody
	protected Object apply(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    //String uid="";
	    //String myStatus="";
	    String firstName="";
	    String lastName="";
	    String bankAccount="";
	    String bankType="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			//uid=jsonObject.getString("uid");
			//myStatus=jsonObject.getString("status");
			firstName=jsonObject.getString("firstName");
			lastName=jsonObject.getString("lastName");
			bankAccount=jsonObject.getString("bankAccount");
			bankType=jsonObject.getString("bankType");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		
		if(bankAccount=="")
		{
			resultDesc=ShowMsg.bankAccountNull;
			resultCode=2;
		}
		else if(bankType=="")
		{
			resultDesc=ShowMsg.bankTypeNull;
			resultCode=1;
		}else if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=1;
		}else{
			User u=new User();
			u.setUid(myloginId);
			u.setBankType(bankType);
			u.setBankAccount(bankAccount);
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setApplyTime(DateUtil.getCurrentDateStr());
			u.setStatus(1);
			result=userService.serveApply(u);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.checkIng;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.checkFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("phone/user/logout")
	@ResponseBody
	protected Object logout(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
	    String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		
		boolean result=loginService.userLogout(myloginId);
		if(result){
			resultCode=0;
			resultDesc=ShowMsg.LogoutSuccess;
		}else{
			resultCode=1;
			resultDesc=ShowMsg.LogoutFail;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;

	}
	
	@RequestMapping("phone/user/push")
	@ResponseBody
	protected Object push(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String cid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			cid=jsonObject.getString("cid");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		if("".equals(cid))
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Login l=new Login();
			l.setIgtClientId(cid);
			l.setLoginId(myloginId);
			result=loginService.updateCID(l);
			if(result)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("/user/info")
	@ResponseBody
	protected Object info(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String uid=getString(request, "uid");
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			User u=userService.getByUId(uid);
			if(u!=null)
			{
				u.setSalt("");
				u.setPassword("");
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", u);
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}

	
	@RequestMapping("/user/list")
	@ResponseBody
	protected Object list(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		String mytype=getString(request, "type");
		String searchStr=getString(request, "searchStr");
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		if(mycurPage.equals("")||mypageSize.equals("")||mytype.equals(""))
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$")&&mytype.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int type=Integer.parseInt(mytype);
				int count=userService.getUserByStatus2(type,searchStr,startDate,endDate);
				if(count>0)
				{
					model.put("count", count);
					int startRow=(curPage-1)*pageSize;
					List<User> userList=userService.searchUser(type, searchStr,startDate,endDate ,startRow, pageSize);
					model.put("list", userList);
					resultCode=0;
					resultDesc=ShowMsg.findSuc;
				}
				else
				{
					model.put("count", 0);
					model.put("list", null);
					resultCode=0;
					resultDesc=ShowMsg.findSuc;
				}
			}
			else
			{
				resultDesc=ShowMsg.inputErr;
				resultCode=2;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/user/list/balance")
	@ResponseBody
	protected Object listbalance(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String mycurPage=getString(request, "curPage");
		String mypageSize=getString(request, "countPage");
		String searchStr=getString(request, "searchStr");
		if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
		{
			int curPage=Integer.parseInt(mycurPage);
			int pageSize=Integer.parseInt(mypageSize);
			int startRow=(curPage-1)*pageSize;
			int count =userService.balanceListCount(searchStr);
			if(count>0)
			{
				model.put("count", count);
				List<User> list=userService.balanceList(searchStr,startRow, pageSize);
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("count", 0);
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	@RequestMapping("/user/list/apply")
	@ResponseBody
	protected Object listApply(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		List<User> userList=userService.applyList();
		model.put("list", userList);
		resultCode=0;
		resultDesc=ShowMsg.findSuc;
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("/user/status")
	@ResponseBody
	protected Object status(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		String uid=getString(request, "uid");
		String status=getString(request, "status");
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			User u=new User();
			u.setUid(uid);
			u.setStatus(Integer.parseInt(status));
			result=userService.updateStatus(u);
			if(result)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("/user/update/apply")
	@ResponseBody
	protected Object statusApply(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		String uid=getString(request, "uid");
		String status=getString(request, "status");
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			User u=new User();
			u.setUid(uid);
			u.setStatus(Integer.parseInt(status));
			u.setAuthTime(DateUtil.getCurrentDateStr());
			u.setHandlePerson(myloginId);
			result=userService.cheakUser(u);
			if(result)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("/user/asw/test")
	@ResponseBody
	protected Object testUser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		JsonNodeFactory factory = new JsonNodeFactory(false);
		// 聊天消息 获取7天以内的消息
        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000);
        ObjectNode queryStrNode1 = factory.objectNode();
        queryStrNode1.put("ql", "select * where timestamp>" + senvenDayAgo + " and timestamp<" + currentTimestamp);
        ObjectNode messages1 = EasemobChatMessage.getChatMessages(queryStrNode1);
		System.out.println(messages1.toString());
		/*
		 {"action":"get",
		 "params":{"ql":["select * where timestamp>1436679126844 and timestamp<1436765526844"]},
		 "path":"/chatmessages",
		 "uri":"http://a1.easemob.com/dashertest/dasher/chatmessages",
		 "entities":[
		 	{"uuid":"9060a01e-291d-11e5-8983-63e445f6cc0c",
			 	"type":"chatmessage",
			 	"created":1436764265771,
			 	"modified":1436764265771,
			 	"timestamp":1436764265528,
			 	"from":"admin",
			 	"msg_id":"82461076486095412",
			 	"to":"hjbtest1",
			 	"chat_type":"chat",
			 	"payload":{"bodies":[{"type":"txt","msg":"你好啊。test数据"}]}},
		 	{"uuid":"9060a046-291d-11e5-8e7d-89257392aa93",
			 	"type":"chatmessage",
			 	"created":1436764265771,
			 	"modified":1436764265771,
			 	"timestamp":1436764265531,
			 	"from":"admin",
			 	"msg_id":"82461076490289716",
			 	"to":"hjbtest3",
			 	"chat_type":"chat",
			 	"payload":{"bodies":[{"type":"txt","msg":"你好啊。test数据"}]}},
		 	{"uuid":"9060c6f2-291d-11e5-ab73-1bf9ca8d3b82",
		 		"type":"chatmessage","created":1436764265772,
		 		"modified":1436764265772,"timestamp":1436764265532,
		 		"from":"admin","msg_id":"82461076502872628","to":"hjbtest",
		 		"chat_type":"chat",
		 		"payload":{"bodies":[{"type":"txt","msg":"你好啊。test数据"}]}},
		 	{"uuid":"9060c6fc-291d-11e5-994b-e173aaa37ae6","type":"chatmessage",
		 		"created":1436764265772,"modified":1436764265772,"timestamp":1436764265537,
		 		"from":"admin","msg_id":"82461076515455540","to":"hjbtest1","chat_type":"chat",
		 		"payload":{
		 			"bodies":[
		 				{"type":"img","filename":"1.png",
		 				"secret":"jvRliikdEeWUy6V8_hZbwVWVqfl12tl_gyJI2F6ObfXl59Vu",
		 				"url":"https://a1.easemob.com/dashertest/dasher/chatfiles/8ef46580-291d-11e5-81fa-2d9064f6f060"}]}},
		 	{"uuid":"9060c72e-291d-11e5-b722-197c14423af6","type":"chatmessage","created":1436764265772,
		 		"modified":1436764265772,"timestamp":1436764265540,"from":"admin","msg_id":"82461076532232756",
		 		"to":"hjbtest3","chat_type":"chat",
		 		"payload":{
		 			"bodies":[
		 				{"type":"img","filename":"1.png","secret":"jvRliikdEeWUy6V8_hZbwVWVqfl12tl_gyJI2F6ObfXl59Vu",
		 				"url":"https://a1.easemob.com/dashertest/dasher/chatfiles/8ef46580-291d-11e5-81fa-2d9064f6f060"}]}},
		 	{"uuid":"9060c706-291d-11e5-a681-7d3c96068b2d","type":"chatmessage","created":1436764265772,
		 		"modified":1436764265772,"timestamp":1436764265546,"from":"admin","msg_id":"82461076540621364",
		 		"to":"hjbtest","chat_type":"chat",
		 			"payload":{
		 				"bodies":[
		 					{"type":"img","filename":"1.png",
		 					"secret":"jvRliikdEeWUy6V8_hZbwVWVqfl12tl_gyJI2F6ObfXl59Vu",
		 					"url":"https://a1.easemob.com/dashertest/dasher/chatfiles/8ef46580-291d-11e5-81fa-2d9064f6f060"}]}},
		 	{"uuid":"afa8be02-291d-11e5-adb5-f9bbba0914da","type":"chatmessage","created":1436764318253,
		 		"modified":1436764318253,"timestamp":1436764317682,"from":"admin","msg_id":"82461300466123168",
		 		"to":"hjbtest1","chat_type":"chat","payload":{"bodies":[{"type":"txt","msg":"测试数据二"}]}},
		 	{"uuid":"afa89742-291d-11e5-b8a8-3df24c0d7955","type":"chatmessage","created":1436764318252,
		 		"modified":1436764318252,"timestamp":1436764317685,"from":"admin","msg_id":"82461300474511776",
		 		"to":"hjbtest3","chat_type":"chat","payload":{"bodies":[{"type":"txt","msg":"测试数据二"}]}},
		 	{"uuid":"afa8be02-291d-11e5-abd2-e1c4740baeea","type":"chatmessage","created":1436764318253,
		 		"modified":1436764318253,"timestamp":1436764317688,"from":"admin","msg_id":"82461300487094688",
		 		"to":"hjbtest","chat_type":"chat","payload":{"bodies":[{"type":"txt","msg":"测试数据二"}]}}],
		 "timestamp":1436765522867,
		 "duration":5,
		 "count":9,
		 "statusCode":200}
		 */
		/*
		 {"action":"get",
		 "params":{"ql":["select * where timestamp>1436765884729 and timestamp<1436765885729"]},
		 "path":"/chatmessages","uri":"http://a1.easemob.com/dashertest/dasher/chatmessages",
		 "entities":[],"timestamp":1436765881731,"duration":4,"count":0,"statusCode":200}
		 */
		resultCode=0;
		resultDesc=ShowMsg.findSuc;
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	
	
}
