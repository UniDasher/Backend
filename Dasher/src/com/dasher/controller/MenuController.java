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

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Menu;
import com.dasher.model.User;
import com.dasher.service.LoginService;
import com.dasher.service.MenuService;
import com.dasher.service.UserService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MenuController extends MyController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/menu/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String sid="";
	    String uid="";
	    String dishsMoney="";
	    String carriageMoney="";
	    String taxesMoney="";
	    String serviceMoney="";
	    String tipMoney="";
	    String menuCount="";
	    String payType="";
	    String mealStartDate="";
	    String mealEndDate="";
	    String address="";
	    String longitude="";
	    String latitude="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			sid=jsonObject.getString("sid");
			uid=jsonObject.getString("uid");
			dishsMoney=jsonObject.getString("dishsMoney");
			carriageMoney=jsonObject.getString("carriageMoney");
			taxesMoney=jsonObject.getString("taxesMoney");
			serviceMoney=jsonObject.getString("serviceMoney");
			tipMoney=jsonObject.getString("tipMoney");
			menuCount=jsonObject.getString("menuCount");
			payType=jsonObject.getString("payType");
			mealStartDate=jsonObject.getString("mealStartDate");
			mealEndDate=jsonObject.getString("mealEndDate");
			address=jsonObject.getString("address");
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
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(sid==""||uid=="")
		{
			resultDesc=ShowMsg.ParFail;
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
			//验证金额
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
			Matcher matcher=pattern.matcher(dishsMoney);
			if(matcher.matches()==false)
			{
				resultDesc=ShowMsg.dishsMoneyErr;
				resultCode=2;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);
				return model;
			}
			if(!carriageMoney.equals(""))
			{
				Matcher matcher2=pattern.matcher(carriageMoney);
				if(matcher2.matches()==false)
				{
					resultDesc=ShowMsg.carriageMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
			/*
			if(!taxesMoney.equals(""))
			{
				Matcher matcher2=pattern.matcher(taxesMoney);
				if(matcher2.matches()==false)
				{
					resultDesc=ShowMsg.taxesMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
			if(!serviceMoney.equals(""))
			{
				Matcher matcher2=pattern.matcher(serviceMoney);
				if(matcher2.matches()==false)
				{
					resultDesc=ShowMsg.serviceMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
			if(!tipMoney.equals(""))
			{
				Matcher matcher2=pattern.matcher(tipMoney);
				if(matcher2.matches()==false)
				{
					resultDesc=ShowMsg.tipMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}*/
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String strs[]=sdf.format(date).split("-");
			String mid="";
			for(int i=0;i<strs.length;i++)
			{
				mid=mid+strs[i];
			}
			Menu m=new Menu();
			m.setMid(mid);
			m.setSid(sid);
			m.setUid(uid);
			if(!dishsMoney.equals(""))
			{
				m.setDishsMoney(Float.parseFloat(dishsMoney));
			}
			if(!carriageMoney.equals(""))
			{
				m.setCarriageMoney(Float.parseFloat(carriageMoney));
			}
			/*
			if(!taxesMoney.equals(""))
			{
				m.setTaxesMoney(Float.parseFloat(taxesMoney));
			}
			if(!serviceMoney.equals(""))
			{
				m.setServiceMoney(Float.parseFloat(serviceMoney));
			}
			if(!tipMoney.equals(""))
			{
				m.setTipMoney(Float.parseFloat(tipMoney));
			}*/
			m.setTaxesMoney(0);
			m.setServiceMoney(0);
			m.setTipMoney(0);
			
			m.setMenuCount(Integer.parseInt(menuCount));
			m.setPayType(Integer.parseInt(payType));
			m.setMealStartDate(mealStartDate);
			m.setMealEndDate(mealEndDate);
			m.setAddress(address);
			m.setLatitude(latitude);
			m.setLongitude(longitude);
			m.setCreateBy(myloginId);
			m.setCreateDate(DateUtil.getCurrentDateStr());

			result=menuService.add(m);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.menuSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.menuFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	


	@RequestMapping("phone/menu/receive")
	@ResponseBody
	protected Object receive(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			mid=jsonObject.getString("mid");
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

		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			//获取接单人的信息，获取编号
			User u=userService.getByUId(myloginId);
			if(u.getStatus()==2)
			{
				Menu m=new Menu();
				m.setWid(myloginId);
				m.setMid(mid);
				m.setStartDate(DateUtil.getCurrentDateStr());
				m.setUpdateBy(myloginId);
				m.setUpdateDate(DateUtil.getCurrentDateStr());
				result=menuService.receive(m);
				if(result==true)
				{
					resultCode=0;
					resultDesc=ShowMsg.receiveSuc;
				}
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.receiveFail;
				}
			}
			else
			{
				resultDesc=ShowMsg.NoPermiss2;
				resultCode=4;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("phone/menu/update/status")
	@ResponseBody
	protected Object updateStatus(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
	    String status="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			mid=jsonObject.getString("mid");
			status=jsonObject.getString("status");
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
		
		if(mid==""||status=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(!status.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.statusErr;
			resultCode=2;
		}
		else
		{
			Menu m=new Menu();
			m.setMid(mid);
			m.setUpdateBy(myloginId);
			m.setUpdateDate(DateUtil.getCurrentDateStr());
			m.setStatus(Integer.parseInt(status));
			result=menuService.updateStatus(m);
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
	@RequestMapping("phone/menu/list/near")
	@ResponseBody
	protected Object listNear(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		if(!longitude.equals("")&&!latitude.equals(""))
		{
			//送餐人获取附近订单
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
	@RequestMapping("phone/menu/user/list")
	@ResponseBody
	protected Object liststatus(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String uid="";
	    String type="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			uid=jsonObject.getString("uid");
			type=jsonObject.getString("type");
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
		
		if(!uid.equals("")&&!type.equals(""))
		{
			//获取用户的下单列表
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
	@RequestMapping("phone/menu/update/mealdate")
	@ResponseBody
	protected Object mealdate(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
	    String mealStartDate="";
	    String mealEndDate="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			mid=jsonObject.getString("uid");
			mealStartDate=jsonObject.getString("mealStartDate");
			mealEndDate=jsonObject.getString("mealEndDate");
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
		
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(mealStartDate=="")
		{
			resultDesc=ShowMsg.mealStartDateNull;
			resultCode=2;
		}
		else if(mealEndDate=="")
		{
			resultDesc=ShowMsg.mealEndDateNull;
			resultCode=2;
		}
		else
		{
			Menu m=new Menu();
			m.setMid(mid);
			m.setMealStartDate(mealStartDate);
			m.setMealEndDate(mealEndDate);
			m.setUpdateBy(myloginId);
			m.setUpdateDate(DateUtil.getCurrentDateStr());
			result=menuService.updateMealDate(m);
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
	
	@RequestMapping("phone/menu/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			mid=jsonObject.getString("uid");
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
		
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Menu m=menuService.getByMid(mid);
			if(m!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", m);
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
	

	@RequestMapping("/menu/list")
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
		model.put("authCode", authCode);
		String status=getString(request, "status");
		String sid=getString(request, "sid");
		String searchStr=getString(request, "searchStr");
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		if(!mycurPage.equals("")&&!mypageSize.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				if(!status.equals("")&&!status.matches("^[0-9]*$"))
				{
					resultDesc=ShowMsg.statusErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=menuService.getCount(status, sid, searchStr, startDate, endDate);
				if(count>0)
				{

					model.put("count", count);
					List<Menu> list=menuService.list(status, sid, searchStr, startDate, endDate, startRow, pageSize);
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
			else
			{
				resultDesc=ShowMsg.inputErr;
				resultCode=2;
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

	@RequestMapping("/menu/user/list")
	@ResponseBody
	protected Object userlist(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String type=getString(request, "type");
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		if(!mycurPage.equals("")&&!mypageSize.equals("")&&!type.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&type.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{

				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=menuService.getListByUidCount(Integer.parseInt(type), uid);
				if(count>0)
				{

					model.put("count", count);
					List<Menu> list=menuService.getListByUid(Integer.parseInt(type), uid, startRow, pageSize);
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
			else
			{
				resultDesc=ShowMsg.inputErr;
				resultCode=2;
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

	@RequestMapping("/menu/info")
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
		model.put("authCode", authCode);
		String mid=getString(request, "mid");
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Menu m=menuService.getByMid(mid);
			if(m!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", m);
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
