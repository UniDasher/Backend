package com.dasher.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Time;
import com.dasher.service.LoginService;
import com.dasher.service.TimeService;
import com.dasher.service.UserService;
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
		model.put("authCode", authCode);
		
		String sid=getString(request, "sid");
		String week_1=getString(request, "week_1");
		String flag_1=getString(request, "flag_1");
		String times_1=getString(request, "times_1");
		
		String week_2=getString(request, "week_2");
		String flag_2=getString(request, "flag_2");
		String times_2=getString(request, "times_2");
		
		String week_3=getString(request, "week_3");
		String flag_3=getString(request, "flag_3");
		String times_3=getString(request, "times_3");
		
		String week_4=getString(request, "week_4");
		String flag_4=getString(request, "flag_4");
		String times_4=getString(request, "times_4");
		
		String week_5=getString(request, "week_5");
		String flag_5=getString(request, "flag_5");
		String times_5=getString(request, "times_5");
		
		String week_6=getString(request, "week_6");
		String flag_6=getString(request, "flag_6");
		String times_6=getString(request, "times_6");
		
		String week_7=getString(request, "week_7");
		String flag_7=getString(request, "flag_7");
		String times_7=getString(request, "times_7");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(week_1=="")
		{
			resultDesc=ShowMsg.weekNull;
			resultCode=2;
		}
		else if(!week_1.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else if(flag_1=="")
		{
			resultDesc=ShowMsg.flagNull;
			resultCode=2;
		}
		else if(!flag_1.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.flagErr;
			resultCode=2;
		}
		else
		{
			result=timeService.add(sid,myloginId,week_1,flag_1,times_1,week_2,flag_2,times_2,week_3,flag_3,times_3,
					week_4,flag_4,times_4,week_5,flag_5,times_5,week_6,flag_6,times_6,week_7,flag_7,times_7);
			
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
	
	@RequestMapping("time/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String sid=getString(request, "sid");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<Time> t=timeService.getBySId(sid);
			if(t!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("list", t);
			}
			else
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("list", null);
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
}
