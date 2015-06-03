package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.dasher.model.MarketMenu;
import com.dasher.model.MarketMenuRecord;
import com.dasher.model.MenuDish;
import com.dasher.model.User;
import com.dasher.service.LoginService;
import com.dasher.service.MarketMenuRecordService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.UserService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MarketMenuRecordController extends MyController {

	@Autowired
	private MarketMenuRecordService marketMenuRecordService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/market/menu/commodity/add")
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

		String mid=getString(request, "mid");
		String name=getString(request, "name");
		String price=getString(request, "price");
		String unit=getString(request, "unit");
		String count=getString(request, "count");
		String subscribe=getString(request, "subscribe");
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.proNameNull;
			resultCode=2;
		}
		else if(price=="")
		{
			resultDesc=ShowMsg.priceNull;
			resultCode=2;
		}
		else if(unit=="")
		{
			resultDesc=ShowMsg.unitNull;
			resultCode=2;
		}
		else if(count=="")
		{
			resultDesc=ShowMsg.menuCountNull;
			resultCode=2;
		}
		else if(!count.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.menuCountErr;
			resultCode=2;
		}
		else
		{
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
			Matcher matcher=pattern.matcher(price);
			if(matcher.matches()==false)
			{
				resultDesc=ShowMsg.priceErr;
				resultCode=2;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);
				return model;
			}
			
			MarketMenuRecord mmr=new MarketMenuRecord();
			mmr.setMid(mid);
			mmr.setName(name);
			mmr.setUnit(unit);
			mmr.setCount(Integer.parseInt(count));
			mmr.setSubscribe(subscribe);
            if(!price.equals(""))
			{
            	mmr.setPrice(Float.parseFloat(price));
			}
			mmr.setCreateBy(myloginId);
			mmr.setCreateDate(DateUtil.getCurrentDateStr());
			result=marketMenuRecordService.add(mmr);
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

	@RequestMapping("/market/menu/commodity/list")
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
		String mid=getString(request, "mid");
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		
		else
		{
			List<MarketMenuRecord> list=marketMenuRecordService.list(mid);
			if(list.size()>0)
			{
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findFail;
				resultCode=1;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	


}
