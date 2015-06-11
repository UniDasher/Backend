package com.dasher.controller;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import javax.servlet.http.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.*;
import com.dasher.service.*;
import com.dasher.util.*;


@Controller
public class MarketController extends MyController {

	@Autowired
	private MarketService marketService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/market/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String smid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			smid=jsonObject.getString("smid");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()>0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			Market m=marketService.getBySmid(smid);
			if(m==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", m);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("phone/market/list/near")
	@ResponseBody
	protected Object phoneList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String longitude="";
	    String latitude="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			longitude=jsonObject.getString("longitude");
			latitude=jsonObject.getString("latitude");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()>0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(!longitude.equals("")&&!latitude.equals(""))
		{
			//获取用户附近的超市列表
		}
		else
		{
			resultDesc=ShowMsg.searchFail;
			resultCode=2;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/market/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
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
				Market market=marketService.getByMarketName(name);
				if(market==null)
				{
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
					Date date=new Date();
					String strs[]=sdf.format(date).split("-");
					String smid="";
					for(int i=0;i<strs.length;i++)
					{
						smid=smid+strs[i];
					}
					
					Market m=new Market();
					m.setSmid(smid);
					m.setName(name);
					m.setAddress(address);
					m.setSubscribe(subscribe);
					m.setEmail(email);
					m.setPhone(phone);
					m.setServiceTime(serviceTime);
					m.setLatitude(latitude);
					m.setLongitude(longitude);
					m.setCreateBy(myloginId);
					m.setCreateDate(DateUtil.getCurrentDateStr());
					result=marketService.add(m);
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
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.marketaddRepeat;
				}
				
			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("/market/update")
	@ResponseBody
	protected Object update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);

		String smid=getString(request, "smid");
		String address=getString(request, "address");
		String subscribe=getString(request, "subscribe");
		String email=getString(request, "email");
		String phone=getString(request, "phone");
		String serviceTime=getString(request, "serviceTime");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
	
		if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
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
			    
				Market m=new Market();
				m.setSmid(smid);
				m.setAddress(address);
				m.setSubscribe(subscribe);
				m.setEmail(email);
				m.setPhone(phone);
				m.setServiceTime(serviceTime);
				m.setLatitude(latitude);
				m.setLongitude(longitude);
				m.setUpdateBy(myloginId);
				m.setUpdateDate(DateUtil.getCurrentDateStr());
				result=marketService.update(m);
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

	@RequestMapping("/market/delete")
	@ResponseBody
	protected Object delete(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);

		String smid=getString(request, "smid");
		if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			Market m=new Market();
			m.setSmid(smid);
			m.setUpdateBy(myloginId);
			m.setUpdateDate(DateUtil.getCurrentDateStr());
			result=marketService.delete(m);
            if(result==true)
		    {
				resultCode=0;
				resultDesc=ShowMsg.delSuc;
		    }
		    else
		    {
				resultCode=1;
				resultDesc=ShowMsg.delFail;
		    }

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	


	@RequestMapping("/market/info")
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

		String smid=getString(request, "smid");
		if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			Market m=marketService.getBySmid(smid);
			if(m==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", m);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/market/list")
	@ResponseBody
	protected Object list(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		String searchStr=getString(request, "searchStr");
		if(!mycurPage.equals("")&&!mypageSize.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=marketService.getListCount(searchStr);
				if(count>0)
				{
					model.put("count", count);
					List<Market> list=marketService.list(searchStr, startRow, pageSize);
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
		}
		else
		{
			resultDesc=ShowMsg.searchFail;
			resultCode=2;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/market/list/menu")
	@ResponseBody
	protected Object menuList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getString(request, "authCode");
		String myloginId=loginService.getByAuthCode(authCode);
		Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		List<Market> marketList=marketService.menuList();
		List<ModelMap> list=new ArrayList<ModelMap>();
		ModelMap shopModel=new ModelMap();
		for(Market s:marketList){
			shopModel.put("smid", s.getSmid());
			shopModel.put("name", s.getName());
			list.add(shopModel);
		}
		model.put("list", list);
		resultDesc=ShowMsg.findSuc;
		resultCode=0;
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
}
