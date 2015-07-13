package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.dasher.model.Earning;
import com.dasher.model.Login;
import com.dasher.model.MenuDish;
import com.dasher.service.EarningService;
import com.dasher.service.LoginService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class EarningController extends MyController {

	@Autowired
	private EarningService earningService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/earn/total")
	@ResponseBody
	protected Object earnWeek(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String authCode= getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		//获取送餐人总收益
		Map<String,Object> map=earningService.getEarnTotal(myloginId);
		//{balance=610.0, weekEarn=6.0, totalEarn=6.0, settleDate=2015-07-13 15:39:08.0, totalMoney=534.0}
		Set<String> keys=map.keySet();
		for (String key : keys) {
			if("settleDate".equals(key)){
				model.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.get(key)));
			}else{
				model.put(key, map.get(key));
			}
		}
		//获取送餐人最近七天的收益
		List<Map<String,Object>> list=earningService.getEarnWeek(myloginId);
		model.put("list", list);
		resultDesc=ShowMsg.findSuc;
		resultCode=0;
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/earn/list")
	@ResponseBody
	protected Object earnList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String authCode= getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		if(!"".equals(startDate)&&!"".equals(endDate)){
			List<Earning> list=earningService.getEarnList(myloginId,startDate,endDate);
			
			model.put("list", list);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}else{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
}
