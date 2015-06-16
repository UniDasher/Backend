package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.ComplainDeal;
import com.dasher.model.Login;
import com.dasher.model.User;
import com.dasher.service.ComplainDealService;
import com.dasher.service.LoginService;
import com.dasher.service.UserService;
import com.dasher.util.DateUtil;
import com.dasher.util.MyMD5Util;
import com.dasher.util.ShowMsg;



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
	    String firstName="";
		try {
			jsonObject = new JSONObject(JSONStr);
			password = jsonObject.getString("password");
			mobilePhone=jsonObject.getString("mobilePhone");
			phoneCode=jsonObject.getString("phoneCode");
			firstName=jsonObject.getString("firstName");
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
			resultCode=2;
		}
		else if(password.length()<6||password.length()>30)
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=2;
		}
		else if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=2;
		}
		else
		{
			//手机格式验证
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			
			if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				//判断验证码是否正确
				ComplainDeal cd=complainDealService.getByTel(mobilePhone);
				if(!cd.getPhoneCode().equals(phoneCode))
				{
					resultCode=2;
					resultDesc=ShowMsg.phoneCodeErr;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			
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
					u.setFirstName(firstName);
					u.setLastName("");
					u.setEqumentNumber("");
					u.setMobilePhone(mobilePhone);
					u.setEmail("");
					u.setAddress("");
					u.setLongitude("");
					u.setLatitude("");
					u.setLogo("/upload/user/images/default.png");
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
			resultCode=2;
		}
		else if(password.length()>5&&password.length()<31)
		{
			int flag=userService.userLoin(mobilePhone, password);
			if(flag==1)
			{
				resultDesc=ShowMsg.userNameNull;
				resultCode=1;
			}
			else if(flag==2)
			{
				resultDesc=ShowMsg.pwdErr;
				resultCode=1;
			}
			else if(flag==0)
			{
				resultDesc=ShowMsg.loginSuc;
				resultCode=0;
				User us=userService.getUserByTel(mobilePhone);
				model.put("uid", us.getUid());
				model.put("authCode", loginService.userHandleLogin(us.getUid()+""));

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
			resultCode=2;
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
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
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
		}
		else
		{
			User u=userService.getByUId(uid);
			if(u!=null)
			{
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
	
	@RequestMapping("phone/user/update/name")
	@ResponseBody
	protected Object updateName(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String firstName="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
			firstName=jsonObject.getString("firstName");
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
		}else if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=2;
		}
		else
		{
			//用户名修改
			User u=new User();
			u.setFirstName(firstName);
			u.setUid(uid);
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
			authCode = jsonObject.getString("authCode");
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
			resultCode=2;
		}else{
			//手机格式验证
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			
			if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				//验证码判断
				ComplainDeal cd=complainDealService.getByTel(mobilePhone);
				if(!cd.getPhoneCode().equals(phoneCode))
				{
					resultCode=2;
					resultDesc=ShowMsg.phoneCodeErr;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
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
					resultCode=2;
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
			authCode = jsonObject.getString("authCode");
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
				resultCode=2;
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
	    String uid="";
	    String bankAccount="";
	    String bankType="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
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
		
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(bankAccount=="")
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
			u.setUid(uid);
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
		
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
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
		
		//String logo=FileUploadUtil.uploadFile(request, "/WEB-INF/upload/user/images");
		String logo="";
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			if("false".equals(logo))
			{
				resultCode=1;
				resultDesc=ShowMsg.imageUploadFail;
			}
			else
			{
				User u=new User();
				u.setUid(uid);
				u.setLogo("/upload/user/images/"+logo);
				result=userService.updateLogo(u);
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
			authCode = jsonObject.getString("authCode");
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
		else
		{
			User u=new User();
			u.setUid(uid);
			try {
				u.setPassword(MyMD5Util.getEncryptedPwd(newPassword));
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
				resultDesc=ShowMsg.pwdErr;
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
		
		
		if(mobilePhone==""||phoneCode==""||newPassword=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			//判断手机验证码是否正确
			ComplainDeal cd=complainDealService.getByTel(mobilePhone);
			if(!cd.getPhoneCode().equals(phoneCode))
			{
				resultCode=2;
				resultDesc=ShowMsg.phoneCodeErr;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);
				return model;
			}
			
			//修改密码
			User u=new User();
			u.setMobilePhone(mobilePhone);
			try {
				u.setPassword(MyMD5Util.getEncryptedPwd(newPassword));
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
				resultDesc=ShowMsg.updateFail;
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
	    String uid="";
	    String myStatus="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
			myStatus=jsonObject.getString("status");
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
		

		if(uid==""||myStatus=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			if(myStatus.matches("^[0-9]*$"))
			{
				int status=Integer.parseInt(myStatus);
				User u=new User();
				u.setUid(uid);
				u.setStatus(status);
				result=userService.userApply(u);
				if(u!=null)
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
				resultDesc=ShowMsg.statusErr;
				resultCode=2;
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
				int count=userService.getUserByStatus2(type,searchStr);
				if(count>0)
				{
					model.put("count", count);
					int startRow=(curPage-1)*pageSize;
					List<User> userList=userService.searchUser(type, searchStr, startRow, pageSize);
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
	
	@RequestMapping("phone/user/push")
	@ResponseBody
	protected Object push(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String cid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
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
		model.put("authCode", authCode);
		
		if("".equals(uid)||"".equals(cid))
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Login l=new Login();
			l.setCid(cid);
			l.setLoginId(uid);
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
	

}
