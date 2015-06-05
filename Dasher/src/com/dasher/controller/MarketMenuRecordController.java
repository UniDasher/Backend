package com.dasher.controller;

import java.io.IOException;
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

import com.dasher.model.MarketMenuRecord;
import com.dasher.service.LoginService;
import com.dasher.service.MarketMenuRecordService;
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

	@RequestMapping("phone/market/menu/commodity/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
	    String mcid="";
	    String name="";
	    String unit="";
	    String price="";
	    String count="";
	    String subscribe="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = jsonObject.getString("authCode");
			mid=jsonObject.getString("mid");
			mcid=jsonObject.getString("mcid");
			name=jsonObject.getString("name");
			price=jsonObject.getString("price");
			count=jsonObject.getString("count");
			unit=jsonObject.getString("unit");
			subscribe=jsonObject.getString("subscribe");
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
	@RequestMapping("phone/market/menu/commodity/list")
	@ResponseBody
	protected Object phoneList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
			List<MarketMenuRecord> list=marketMenuRecordService.list(mid);
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
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	


}
