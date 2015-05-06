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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.User;
import com.dasher.service.LoginService;
import com.dasher.service.UserService;
import com.dasher.util.MyMD5Util;
import com.dasher.util.ShowMsg;



@Controller
public class UserController extends MyController {

	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/user/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String account=getString(request, "account");
		String password=getString(request, "password");
		String firstName=getString(request, "firstName");
		String lastName=getString(request, "lastName");
		String equmentNumber=getString(request, "equmentNumber");
		String mobilePhone=getString(request, "mobilePhone");
		String email=getString(request, "email");
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		if(account=="")
		{
			resultDesc=ShowMsg.userNull;
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
		else if(lastName=="")
		{
			resultDesc=ShowMsg.LastNameNull;
			resultCode=2;
		}
		else if(mobilePhone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=2;
		}
		else if(email=="")
		{
			resultDesc=ShowMsg.EmailNull;
			resultCode=2;
		}
		else if(address=="")
		{
			resultDesc=ShowMsg.AddressNull;
			resultCode=2;
		}
		else if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.LonLatNull;
			resultCode=2;
		}
		else
		{

			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
			Matcher matcher=pattern.matcher(email);
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			if(matcher.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.emailErr;
			}
			else if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
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
				u.setAccount(account);
				try {
					u.setPassword(MyMD5Util.getEncryptedPwd(password));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				u.setSalt(salt);
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setEqumentNumber(equmentNumber);
				u.setMobilePhone(mobilePhone);
				u.setEmail(email);
				u.setAddress(address);
				u.setLongitude(longitude);
				u.setLatitude(latitude);
				u.setLogo("logo");
				User user=userService.getUserByAccount(account);
				if(user==null)
				{
					result=userService.addUser(u);
					if(result==true)
					{
						resultCode=0;
						resultDesc=ShowMsg.addSuc;
						User us=userService.getUserByAccount(account);
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

	@RequestMapping("/user/login")
	@ResponseBody
	protected Object login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String account=getString(request, "account");
		String password=getString(request, "password");
		if(account=="")
		{
			resultDesc=ShowMsg.userNull;
			resultCode=2;
		}
		else if(password.length()>5&&password.length()<31)
		{
			int flag=userService.userLoin(account, password);
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
				User us=userService.getUserByAccount(account);
				model.put("uid", us.getUid());
				model.put("authCode", loginService.handleLogin(us.getUid()+""));

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
		model.put("authCode", loginService.userHandleLogin(myloginId));
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
				model.put("user", u);
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

	@RequestMapping("/user/update")
	@ResponseBody
	protected Object update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		model.put("authCode", loginService.userHandleLogin(myloginId));
		String uid=getString(request, "uid");
		String firstName=getString(request, "firstName");
		String lastName=getString(request, "lastName");
		String mobilePhone=getString(request, "mobilePhone");
		String logo=getString(request, "logo");
		String email=getString(request, "email");
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");

		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}

		else if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=2;
		}
		else if(lastName=="")
		{
			resultDesc=ShowMsg.LastNameNull;
			resultCode=2;
		}
		else if(mobilePhone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=2;
		}
		else if(email=="")
		{
			resultDesc=ShowMsg.EmailNull;
			resultCode=2;
		}
		else if(address=="")
		{
			resultDesc=ShowMsg.AddressNull;
			resultCode=2;
		}
		else if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.LonLatNull;
			resultCode=2;
		}
		else
		{
			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
			Matcher matcher=pattern.matcher(email);
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(mobilePhone);
			if(matcher.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.emailErr;
			}
			else if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				User u=new User();
				u.setUid(uid);
				u.setFirstName(firstName);
				u.setLastName(lastName);
				u.setMobilePhone(mobilePhone);
				u.setLogo(logo);
				u.setEmail(email);
				u.setAddress(address);
				u.setLongitude(longitude);
				u.setLatitude(latitude);

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
		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}

	@RequestMapping("/user/pwd/update")
	@ResponseBody
	protected Object pwdupdate(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		model.put("authCode", loginService.userHandleLogin(myloginId));
		String uid=getString(request, "uid");
		String oldPassword=getString(request, "oldPassword");
		String newPassword=getString(request, "newPassword");

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
	
	@RequestMapping("/user/status")
	@ResponseBody
	protected Object apply(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		model.put("authCode", loginService.userHandleLogin(myloginId));
		String uid=getString(request, "uid");
		String myStatus=getString(request, "status");

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
		model.put("authCode", loginService.userHandleLogin(myloginId));
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
					model.put("userList", userList);
					resultCode=0;
					resultDesc=ShowMsg.findSuc;
				}
				else
				{
					resultDesc=ShowMsg.findFail;
					resultCode=1;
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
	
	
}
