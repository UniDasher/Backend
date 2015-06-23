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
import com.dasher.model.Time;
import com.dasher.model.User;
import com.dasher.service.ComplainDealService;
import com.dasher.service.LoginService;
import com.dasher.service.ShopService;
import com.dasher.service.TimeService;
import com.dasher.service.UserService;
import com.dasher.util.DateUtil;
import com.dasher.util.MyMD5Util;
import com.dasher.util.ShowMsg;



@Controller
public class TimeController extends MyController {

	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private TimeService timeService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("time/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String sid=getString(request, "sid");
		String weeks=getString(request, "weeks");
		String flag=getString(request, "flag");
		String time=getString(request, "time");
		String time1="";
		String time2="";
		String time3="";
		String time4="";
		String time5="";
		String subscribe=getString(request, "subscribe");
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(weeks=="")
		{
			resultDesc=ShowMsg.weekNull;
			resultCode=2;
		}
		else if(!weeks.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else if(flag=="")
		{
			resultDesc=ShowMsg.flagNull;
			resultCode=2;
		}
		else if(!flag.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.flagErr;
			resultCode=2;
		}
		else
		{
			String strs[]=time.split(",");
			for(int i=0;i<strs.length;i++)
			{
				time1=strs[0];
				time2=strs[1];
				time3=strs[2];
				time4=strs[3];
				time5=strs[4];
			}
			Time t=new Time();
			if(flag.equals("0"))
			{
				t.setWeeks(Integer.parseInt(weeks));
			}
			else
			{
				t.setTime1(time1);
				t.setTime2(time2);
				t.setTime3(time3);
				t.setTime4(time4);
				t.setTime5(time5);
			}
			t.setSid(sid);
			t.setFlag(Integer.parseInt(flag));
			t.setSubscribe(subscribe);
			t.setCreateBy(myloginId);
			t.setCreateDate(DateUtil.getCurrentDateStr());
			result=timeService.add(t);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.addSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.addFail;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}	
	
	@RequestMapping("time/update")
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String id=getString(request, "id");
		String weeks=getString(request, "weeks");
		String flag=getString(request, "flag");
		String time=getString(request, "time");
		String time1="";
		String time2="";
		String time3="";
		String time4="";
		String time5="";
		String subscribe=getString(request, "subscribe");
		if(id==""||!id.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(weeks=="")
		{
			resultDesc=ShowMsg.weekNull;
			resultCode=2;
		}
		else if(!weeks.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else if(flag=="")
		{
			resultDesc=ShowMsg.flagNull;
			resultCode=2;
		}
		else if(!flag.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.flagErr;
			resultCode=2;
		}
		else
		{
			String strs[]=time.split(",");
			for(int i=0;i<strs.length;i++)
			{
				time1=strs[0];
				time2=strs[1];
				time3=strs[2];
				time4=strs[3];
				time5=strs[4];
			}
			Time t=new Time();
			if(flag.equals("0"))
			{
				t.setWeeks(Integer.parseInt(weeks));
			}
			else
			{
				t.setTime1(time1);
				t.setTime2(time2);
				t.setTime3(time3);
				t.setTime4(time4);
				t.setTime5(time5);
			}
			t.setId(Integer.parseInt(id));
			t.setFlag(Integer.parseInt(flag));
			t.setSubscribe(subscribe);
			t.setCreateBy(myloginId);
			t.setCreateDate(DateUtil.getCurrentDateStr());
			result=timeService.update(t);
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

	
}
