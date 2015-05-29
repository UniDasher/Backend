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

import com.dasher.model.Login;
import com.dasher.model.Market;
import com.dasher.model.Menu;
import com.dasher.model.MenuEvaluate;
import com.dasher.model.Shop;
import com.dasher.service.LoginService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.MarketService;
import com.dasher.service.MenuEvaluateService;
import com.dasher.service.MenuService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MarketMenuController extends MyController {

	@Autowired
	private MarketMenuService marketMenuService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/market/menu/add")
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

		String mcid=getString(request, "mcid");
		String uid=getString(request, "uid");
		String dishsMoney=getString(request, "dishsMoney");
		String carriageMoney=getString(request, "carriageMoney");
		String taxesMoney=getString(request, "taxesMoney");
		String serviceMoney=getString(request, "serviceMoney");
		String tipMoney=getString(request, "tipMoney");
		String menuCount=getString(request, "menuCount");
		String payType=getString(request, "payType");
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		String mealStartDate=getString(request, "mealStartDate");
		String mealEndDate=getString(request, "mealEndDate");
		if(mcid==""||uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(address=="")
		{
			resultDesc=ShowMsg.AddressNull;
			resultCode=2;
		}
		else if(dishsMoney=="")
		{
			resultDesc=ShowMsg.dishsMoneyNull;
			resultCode=2;
		}
		else if(menuCount=="")
		{
			resultDesc=ShowMsg.menuCountNull;
			resultCode=2;
		}
		else if(!menuCount.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.menuCountErr;
			resultCode=2;
		}
		else if(payType=="")
		{
			resultDesc=ShowMsg.payTypeNull;
			resultCode=2;
		}
		else if(!payType.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.payTypeErr;
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
//			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
//			Matcher matcher=pattern.matcher(email);
//			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//			Matcher matcher2=pattern2.matcher(phone);
//			if(matcher.matches()==false)
//			{
//				resultCode=2;
//				resultDesc=ShowMsg.emailErr;
//			}
//			else if(matcher2.matches()==false)
//			{
//				resultCode=2;
//				resultDesc=ShowMsg.mobilePhoneErr;
//			}
//			else
//			{
//
//				
//			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	

}
