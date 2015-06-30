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

	@RequestMapping("phone/earning/listTotal")
	@ResponseBody
	protected Object phoneUserList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String wid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			wid=jsonObject.getString("wid");
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
		
		if(wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<Earning> list=earningService.getByWid(wid);
			float total=0;
			
			if(list.size()>0)
			{
				for(Earning earn:list)
				{
					total=total+earn.getTotalMoney();
				}
				
				model.put("data", total);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/earning/listService")
	@ResponseBody
	protected Object listService(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String wid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			wid=jsonObject.getString("wid");
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
		
		if(wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<Earning> list=earningService.getByWid(wid);
			float total=0;
			if(list.size()>0)
			{
				for(Earning earn:list)
				{
					total=total+earn.getCarriageMoney();
				}
				model.put("data", total);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/earning/listMonth")
	@ResponseBody
	protected Object listMonth(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String wid="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			wid=jsonObject.getString("wid");
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
		
		if(wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<Earning> list=earningService.listMonth(wid);
			float total=0;
			if(list.size()>0)
			{
				for(Earning earn:list)
				{
					total=total+earn.getCarriageMoney();
				}
				model.put("data", total);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/earning/carriageDay")
	@ResponseBody
	protected Object listDay(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String wid="";
	    String str="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			wid=jsonObject.getString("wid");
			str=jsonObject.getString("str");
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
		
		if(wid==""||str=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(!str.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.inputErr;
			resultCode=2;
		}
		else
		{
			List<Earning> list=earningService.listDay(wid, Integer.parseInt(str));
			float total=0;
			if(list.size()>0)
			{
				for(Earning earn:list)
				{
					total=total+earn.getCarriageMoney();
				}
				model.put("data", total);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	/*---------------------------------------------------------------------------------------------*/
	
	/*
	 * 
	 * 添加测试
	 */
	@RequestMapping("/test/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String wid=getString(request, "wid");
	    String mid=getString(request, "mid");
	    String carriageMoney=getString(request, "carriageMoney");
	    String totalMoney=getString(request, "totalMoney");
	    String type=getString(request, "type");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
		Date date=new Date();
		String strs[]=sdf.format(date).split("-");
		String eid="";
		for(int i=0;i<strs.length;i++)
		{
			eid=eid+strs[i];
		}
	    Earning e=new Earning();
	    e.setEid(eid);
	    e.setWid(wid);
	    e.setMid(mid);
	    e.setCarriageMoney(Float.parseFloat(carriageMoney));
	    e.setTotalMoney(Float.parseFloat(totalMoney));
	    e.setType(Integer.parseInt(type));
	    e.setCreateDate(DateUtil.getCurrentDateStr());
	    result=earningService.add(e);
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
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	/*
	 * 
	 * 测试累计总收入
	 * 
	 */
	@RequestMapping("/earning/listTotal")
	@ResponseBody
	protected Object UserList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String wid=getString(request, "wid");
		List<Earning> list=earningService.getByWid(wid);
		float total=0;
		
		if(list.size()>0)
		{
			for(Earning earn:list)
			{
				total=total+earn.getTotalMoney();
			}
			
			model.put("data", total);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		else
		{
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
    /*
     * 累计运费收入
     * 
     */
	
	@RequestMapping("/earning/listService")
	@ResponseBody
	protected Object listservice(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String wid=getString(request, "wid");
		List<Earning> list=earningService.getByWid(wid);
		float total=0;
		if(list.size()>0)
		{
			for(Earning earn:list)
			{
				total=total+earn.getCarriageMoney();
			}
			model.put("data", total);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		else
		{
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	/*
	 * 
	 * 测试本月累计收入
	 * 
	 */
	
	@RequestMapping("/earning/listMonth")
	@ResponseBody
	protected Object listmonth(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String wid=getString(request, "wid");
		List<Earning> list=earningService.listMonth(wid);
		float total=0;
		if(list.size()>0)
		{
			for(Earning earn:list)
			{
				total=total+earn.getCarriageMoney();
			}
			model.put("data", total);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		else
		{
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
			
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	
	/*
	 * 
	 * 测试每日收益
	 * 
	 */
	@RequestMapping("/earning/carriageDay")
	@ResponseBody
	protected Object listday(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
	    String wid=getString(request, "wid");
	    String str=getString(request, "str");
		List<Earning> list=earningService.listDay(wid, Integer.parseInt(str));
		float total=0;
		if(list.size()>0)
		{
			for(Earning earn:list)
			{
				total=total+earn.getCarriageMoney();
			}
			model.put("data", total);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		else
		{
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
}
