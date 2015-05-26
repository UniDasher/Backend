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

		String name=getString(request, "name");
		String address=getString(request, "address");
		String subscribe=getString(request, "subscribe");
		String email=getString(request, "email");
		String phone=getString(request, "phone");
		String serviceTime=getString(request, "serviceTime");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		if(name=="")
		{
			resultDesc=ShowMsg.marketNull;
			resultCode=2;
		}
		else if(address=="")
		{
			resultDesc=ShowMsg.AddressNull;
			resultCode=2;
		}
		else if(email=="")
		{
			resultDesc=ShowMsg.EmailNull;
			resultCode=2;
		}
		else if(phone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
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
			Matcher matcher2=pattern2.matcher(phone);
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
//				Market market=marketService.getByMarketName(name);
//				if(market==null)
//				{
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
//					Date date=new Date();
//					String strs[]=sdf.format(date).split("-");
//					String smid="";
//					for(int i=0;i<strs.length;i++)
//					{
//						smid=smid+strs[i];
//					}
//					
//					Market m=new Market();
//					m.setSmid(smid);
//					m.setName(name);
//					m.setAddress(address);
//					m.setSubscribe(subscribe);
//					m.setEmail(email);
//					m.setPhone(phone);
//					m.setServiceTime(serviceTime);
//					m.setLatitude(latitude);
//					m.setLongitude(longitude);
//					m.setCreateBy(myloginId);
//					m.setCreateDate(DateUtil.getCurrentDateStr());
//					result=marketService.add(m);
//		            if(result==true)
//				    {
//						resultCode=0;
//						resultDesc=ShowMsg.addSuc;
//				    }
//				    else
//				    {
//						resultCode=1;
//						resultDesc=ShowMsg.addFail;
//				    }
//				}
//				else
//				{
//					resultCode=1;
//					resultDesc=ShowMsg.marketaddRepeat;
//				}
				
			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	

}
