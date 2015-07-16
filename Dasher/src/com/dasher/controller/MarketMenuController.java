package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Market;
import com.dasher.model.MarketCommodity;
import com.dasher.model.MarketMenu;
import com.dasher.model.MarketMenuRecord;
import com.dasher.model.MenuDish;
import com.dasher.model.ShopDish;
import com.dasher.model.Time;
import com.dasher.model.User;
import com.dasher.service.LoginService;
import com.dasher.service.MarketCommodityService;
import com.dasher.service.MarketMenuService;
import com.dasher.service.MarketService;
import com.dasher.service.ShopService;
import com.dasher.service.TimeService;
import com.dasher.service.UserService;
import com.dasher.util.CalendarUtil;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MarketMenuController extends MyController {

	@Autowired
	private MarketMenuService marketMenuService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private MarketService marketService;
	@Autowired
	private TimeService timeService;
	@Autowired
	private MarketCommodityService marketCommodityService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/market/menu/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, JSONException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String smid="";
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
			smid=jsonObject.getString("smid");
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

		if(smid==""||uid=="")
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
			Time time=timeService.getCurTimeBySId(smid);
			if(time!=null&&!CalendarUtil.IsBusiness(time)){
				resultDesc=ShowMsg.shopNotBusiness;
				resultCode=1;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);	
				return model;
			}
			
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
            MarketMenu mm=new MarketMenu();
            mm.setMid(mid);
            mm.setSmid(smid);
            mm.setUid(uid);
            if(!dishsMoney.equals(""))
			{
            	mm.setDishsMoney(Float.parseFloat(dishsMoney));
			}
			if(!carriageMoney.equals(""))
			{
				mm.setCarriageMoney(Float.parseFloat(carriageMoney));
			}
			mm.setTaxesMoney(0);
			mm.setServiceMoney(0);
			mm.setTipMoney(0);
			mm.setMenuCount(Integer.parseInt(menuCount));
			mm.setPayType(Integer.parseInt(payType));
			mm.setAddress(address);
			mm.setLatitude(latitude);
			mm.setLongitude(longitude);
			mm.setMealStartDate(mealStartDate);
            mm.setMealEndDate(mealEndDate);
            mm.setCreateBy(myloginId);
            mm.setCreateDate(DateUtil.getCurrentDateStr());
            
            List<MarketMenuRecord> dishs=new ArrayList<MarketMenuRecord>();
            JSONArray dishArray=jsonObject.getJSONArray("dishs");
            for(int i=0;i<dishArray.length();i++){
				JSONObject dishObj=dishArray.getJSONObject(i);
				String mcid="";
			    //String name="";
			    String price="";
			    String unit="";
			    String count="";
			    String subscribe="";
			    mcid=dishObj.getString("mcid");
			    //name=dishObj.getString("name");
			    price=dishObj.getString("price");
			    unit=dishObj.getString("unit");
				count=dishObj.getString("count");
				subscribe=dishObj.getString("subscribe");
				if(mcid=="")
				{
					resultDesc=ShowMsg.nameNull;
					resultCode=2;
				}
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
					MarketCommodity mc=marketCommodityService.getByMcid(mcid);
					if(mc==null||mc.getPrice()!=Float.parseFloat(price)){
						resultDesc=ShowMsg.shopNotDish;
						resultCode=2;
						model.put("resultCode", resultCode);	
						model.put("resultDesc", resultDesc);
						return model;
					}
					
					MarketMenuRecord md=new MarketMenuRecord();
					md.setMid(mid);
					md.setMcid(mcid);
					md.setName(mc.getName());
					md.setUnit(unit);
					md.setPrice(Float.parseFloat(price));
					md.setCount(Integer.parseInt(count));
					md.setSubscribe(subscribe);
					md.setCreateBy(myloginId);
					md.setCreateDate(DateUtil.getCurrentDateStr());
					
					dishs.add(md);
				}
            }

            mm.setDishs(dishs);
          //判断菜品的金额是否等于订单的金额
			float dishsPriceTal=0;
			for (MarketMenuRecord md : dishs) {
				dishsPriceTal+=md.getPrice()*md.getCount();
			}
			if(dishsPriceTal==mm.getDishsMoney()){
				result=marketMenuService.add(mm);
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
				resultCode=1;
				resultDesc=ShowMsg.DishsTotalPriceNotMenuDishPrice;
			}
            
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("phone/market/menu/receive")
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
			User u=userService.getByUId(myloginId);
			if(u.getStatus()==2)
			{
				MarketMenu mm=new MarketMenu();
				mm.setWid(myloginId);
				mm.setMid(mid);
				mm.setStartDate(DateUtil.getCurrentDateStr());
				mm.setUpdateBy(myloginId);
				mm.setUpdateDate(DateUtil.getCurrentDateStr());
				int res=marketMenuService.receive(mm);
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

	@RequestMapping("phone/market/menu/complete")
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
			result=marketMenuService.menuComplete(mid,Integer.parseInt(evalShop),Integer.parseInt(evalServer),myloginId);
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
	
	@RequestMapping("phone/market/menu/status")
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
			MarketMenu mm=new MarketMenu();
			mm.setMid(mid);
			mm.setUpdateBy(myloginId);
			mm.setUpdateDate(DateUtil.getCurrentDateStr());
			mm.setStatus(Integer.parseInt(status));
			result=marketMenuService.updateStatus(mm);
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
	
	@RequestMapping("phone/menu/list/near/market")
	@ResponseBody
	protected Object listNearMarket(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			List<Market> list=marketService.getNearListMenu(Double.parseDouble(longitude), Double.parseDouble(latitude), ShowMsg.distance);
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
	
	@RequestMapping("phone/menu/list/near/smid")
	@ResponseBody
	protected Object listNearSmid(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String smid=getString(request, "smid");
		
		if(smid=="")
		{
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			List<MarketMenu> list=marketMenuService.getNearListSmid(smid);
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
	
	@RequestMapping("phone/market/menu/list/near")
	@ResponseBody
	protected Object listNear(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.NoLocatInfo;
			resultCode=2;
		}
		else
		{
			List<MarketMenu> list=marketMenuService.getNearList(Double.parseDouble(longitude), Double.parseDouble(latitude), ShowMsg.distance);
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
	
	@RequestMapping("phone/market/menu/user/list")
	@ResponseBody
	protected Object userListStatus(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String uid=getString(request, "uid");
		String type=getString(request, "type");
		String userType=getString(request, "userType");
		if(uid.equals("")||type.equals(""))
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(!type.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.statusErr;
			resultCode=2;
		}
		else
		{
			List<MarketMenu> list=marketMenuService.ListByUid(Integer.parseInt(type), uid,Integer.parseInt(userType));
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
	@RequestMapping("phone/market/menu/update/mealdate")
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
			//超市订单延时代码
			MarketMenu mm=new MarketMenu();
			mm.setMid(mid);
			mm.setMealStartDate(mealStartDate);
			mm.setMealEndDate(mealEndDate);
			mm.setUpdateBy(myloginId);
			mm.setUpdateDate(DateUtil.getCurrentDateStr());
			result=marketMenuService.updateDate(mm);
			
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
	
	@RequestMapping("phone/market/menu/info")
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
			MarketMenu mm=marketMenuService.getByMid(mid);
			if(mm!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", mm);
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
	
	@RequestMapping("/market/menu/list")
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
		String smid=getString(request, "smid");
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
				int count=marketMenuService.getCount(status, smid, searchStr,startDate,endDate);
				if(count>0)
				{
					model.put("count", count);
					List<MarketMenu> list=marketMenuService.list(status, smid, searchStr,startDate,endDate, 
							startRow, pageSize);
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

	
	@RequestMapping("/market/menu/info")
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
		String mid=getString(request, "mid");
		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			MarketMenu mm=marketMenuService.getByMid(mid);
			if(mm!=null)
			{
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
				model.put("data", mm);
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


	@RequestMapping("/market/menu/user/list")
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
				int count=marketMenuService.getListByUidCount(Integer.parseInt(type), uid, startDate, endDate);
				if(count>0)
				{
					model.put("count", count);
					List<MarketMenu> list=marketMenuService.getListByUid(Integer.parseInt(type), uid, startDate, endDate, startRow, pageSize);
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

}
