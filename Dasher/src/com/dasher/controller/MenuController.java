package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.*;
import com.dasher.service.*;
import com.dasher.util.*;

@Controller
public class MenuController extends MyController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopDishService shopDishService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/menu/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		try {
			//获取参数
			String JSONStr=getJsonString(request);
		    JSONObject jsonObject=null;
		    jsonObject = new JSONObject(JSONStr);
		    String authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		    //获取订单的信息
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
			

			if(sid==""||uid==""||mealEndDate=="")
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
				//判断送餐时间是否在当前时间之后
				int cal1=CalendarUtil.timeCurCompare(mealEndDate);
				if(cal1==0){
					resultDesc=ShowMsg.mealEndDateOver;
					resultCode=1;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);	
					return model;
				}else if(cal1==-1){
					resultDesc=ShowMsg.mealEndDateFail;
					resultCode=1;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);	
					return model;
				}
				
				//判断商家是否在营业时间
				Time time=timeService.getCurTimeBySId(sid);
				if(time!=null&&!CalendarUtil.IsBusiness(time)){
					resultDesc=ShowMsg.shopNotBusiness;
					resultCode=1;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);	
					return model;
				}
				
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
				
				//获取订单的菜品信息
				List<MenuDish> dishs=new ArrayList<MenuDish>();
				JSONArray dishArray=jsonObject.getJSONArray("dishs");
				for(int i=0;i<dishArray.length();i++)
				{
					JSONObject dishObj=dishArray.getJSONObject(i);
				    String did="";
				    //String name="";
				    String price="";
				    String count="";
				    did=dishObj.getString("did");
					//name=dishObj.getString("name");
					price=dishObj.getString("price");
					count=dishObj.getString("count");
					if(did=="")
					{
						resultDesc=ShowMsg.ParFail;
						resultCode=2;
					}
//					else if(name=="")
//					{
//						resultDesc=ShowMsg.nameNull;
//						resultCode=2;
//					}
					else if(price=="")
					{
						resultDesc=ShowMsg.priceNull;
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
						//验证金额
						pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
						matcher=pattern.matcher(price);
						if(matcher.matches()==false)
						{
							resultDesc=ShowMsg.priceErr;
							resultCode=2;
							model.put("resultCode", resultCode);	
							model.put("resultDesc", resultDesc);
							return model;
						}
						
						//判断菜品是否存在
						ShopDish sd=shopDishService.getByDid(did);
						if(sd==null||sd.getPrice()!=Float.parseFloat(price)){
							resultDesc=ShowMsg.shopNotDish;
							resultCode=2;
							model.put("resultCode", resultCode);	
							model.put("resultDesc", resultDesc);
							return model;
						}
						
						MenuDish md=new MenuDish();
						md.setMid(mid);
						md.setDid(did);
						md.setName(sd.getName());
						md.setPrice(Float.parseFloat(price));
						md.setCount(Integer.parseInt(count));
						md.setCreateBy(myloginId);
						md.setCreateDate(DateUtil.getCurrentDateStr());
						
						dishs.add(md);
					}
				}
				//执行订单及订单餐品的新增操作
				m.setDishs(dishs);
				
				//判断菜品的金额是否等于订单的金额
				float dishsPriceTal=0;
				for (MenuDish md : dishs) {
					dishsPriceTal+=md.getPrice()*md.getCount();
				}
				if(dishsPriceTal==m.getDishsMoney()){
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
				}else{
					resultCode=2;
					resultDesc=ShowMsg.DishsTotalPriceNotMenuDishPrice;
				}
			}
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
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
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
//		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
//		{
//			resultDesc=ShowMsg.NoLogin;
//			resultCode=3;
//			model.put("resultCode", resultCode);	
//			model.put("resultDesc", resultDesc);	
//			return model;
//		}
//		model.put("authCode", authCode);

		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			//获取接单人的信息，获取编号
			User u=userService.getByUId(myloginId);
			//if(u.getStatus()==2)
			if(true)
			{
				Menu m=new Menu();
				m.setWid(myloginId);
				m.setMid(mid);
				m.setStartDate(DateUtil.getCurrentDateStr());
				m.setUpdateBy(myloginId);
				m.setUpdateDate(DateUtil.getCurrentDateStr());
				int res=menuService.receive(m);
				if(res==1)
				{
					resultCode=0;
					resultDesc=ShowMsg.receiveSuc;
				}
				else if(res==0)
				{
					resultCode=1;
					resultDesc=ShowMsg.receiveFail;
				}else
				{
					resultCode=1;
					resultDesc=ShowMsg.receiveFail_2;
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

	@RequestMapping("phone/menu/complete")
	@ResponseBody
	protected Object menuComplete(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
	    String evalShop="";
	    String evalServer="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
			mid=jsonObject.getString("mid");
			evalShop=jsonObject.getString("evalShop");
			evalServer=jsonObject.getString("evalServer");
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
			
			result=menuService.menuComplete(mid,Integer.parseInt(evalShop),Integer.parseInt(evalServer),myloginId);
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
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
				resultDesc=ShowMsg.menuCanFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("phone/menu/list/near/shop")
	@ResponseBody
	protected Object listNearShop(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			//送餐人获取附近订单
			List<Shop> list=shopService.getListByMenu(Double.parseDouble(longitude), 
					Double.parseDouble(latitude),ShowMsg.distance,myloginId);
			if(list.size()>0)
			{
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	@RequestMapping("phone/menu/list/near/sid")
	@ResponseBody
	protected Object listNearSid(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			//送餐人获取附近订单
			List<Menu> list=menuService.getNearListBySid(sid,myloginId);
			if(list.size()>0)
			{
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
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
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			//送餐人获取附近订单
			List<Menu> list=menuService.getNearList(Double.parseDouble(longitude), Double.parseDouble(latitude),ShowMsg.distance);
			if(list.size()>0)
			{
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
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
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		String userType=getString(request, "userType");
		if(!uid.equals("")&&!type.equals(""))
		{
			if(type.matches("^[0-9]*$"))
			{
				//获取用户的下单列表
				List<Menu> list=menuService.getListByStr(Integer.parseInt(type), uid,Integer.parseInt(userType));
				if(list.size()>0)
				{
					model.put("count", list.size());
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
			authCode = getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		if(!mycurPage.equals("")&&!mypageSize.equals("")&&!type.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&type.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{

				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=menuService.getListByUidCount(Integer.parseInt(type), uid,startDate,endDate);
				if(count>0)
				{
					model.put("count", count);
					List<Menu> list=menuService.getListByUid(Integer.parseInt(type), uid,startDate,endDate, startRow, pageSize);
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
