package com.dasher.controller;

import java.io.IOException;
import java.util.List;

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

import com.dasher.model.MenuEvaluate;
import com.dasher.service.LoginService;
import com.dasher.service.MenuEvaluateService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MenuEvaluateController extends MyController {

	@Autowired
	private MenuEvaluateService menuEvaluateService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/menu/eval/add")
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
	    String mid="";
	    String wid="";
	    String evalShop="";
	    String evalServer="";
	    String evalContent="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			sid=jsonObject.getString("sid");
			uid=jsonObject.getString("uid");
			mid=jsonObject.getString("mid");
			wid=jsonObject.getString("wid");
			evalShop=jsonObject.getString("evalShop");
			evalServer=jsonObject.getString("evalServer");
			evalContent=jsonObject.getString("evalContent");
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

		if(mid==""||sid==""||wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(evalShop==""||evalServer=="")
		{
			resultDesc=ShowMsg.evalNull;
			resultCode=2;
		}
		else if(!evalShop.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.evalErr;
			resultCode=2;
		}
		else if(!evalServer.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.evalErr;
			resultCode=2;
		}
		else
		{
           MenuEvaluate me=new MenuEvaluate();
           me.setMid(mid);
           me.setSid(sid);
           me.setUid(myloginId);
           me.setWid(wid);
           me.setEvalShop(Integer.parseInt(evalShop));
           me.setEvalServer(Integer.parseInt(evalServer));
           me.setEvalContent(evalContent);
           me.setCreateBy(myloginId);
           me.setCreateDate(DateUtil.getCurrentDateStr());
           result=menuEvaluateService.add(me);
           if(result==true)
		   {
				resultCode=0;
				resultDesc=ShowMsg.evalSuc;
		   }
		   else
		   {
				resultCode=1;
				resultDesc=ShowMsg.evalFail;
		   }
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("phone/menu/eval/info")
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
			authCode = getHeadersInfo(request,"X-Auth-Token");
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
           MenuEvaluate me=menuEvaluateService.getEvalByMid(mid);
           if(me!=null)
           {
        	   model.put("data", me);
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

	@RequestMapping("phone/menu/eval/user")
	@ResponseBody
	protected Object evaluser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String wid="";
	    String mycurPage="";
	    String mypageSize="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			wid=jsonObject.getString("wid");
			mycurPage=jsonObject.getString("curPage");
			mypageSize=jsonObject.getString("countPage");
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

		if(!mycurPage.equals("")&&!mypageSize.equals("")&&!wid.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=menuEvaluateService.WaiterCount(wid);
				if(count>0)
				{

					model.put("count", count);
					List<MenuEvaluate> list=menuEvaluateService.ListWaiter(wid, startRow, pageSize);
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
	
	@RequestMapping("phone/menu/eval/shop")
	@ResponseBody
	protected Object evalshop(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String sid="";
	    String mycurPage="";
	    String mypageSize="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			sid=jsonObject.getString("sid");
			mycurPage=jsonObject.getString("curPage");
			mypageSize=jsonObject.getString("countPage");
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

		if(!mycurPage.equals("")&&!mypageSize.equals("")&&!sid.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=menuEvaluateService.ShopCount(sid);
				if(count>0)
				{

					model.put("count", count);
					List<MenuEvaluate> list=menuEvaluateService.ListShop(sid, startRow, pageSize);
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
	@RequestMapping("/menu/eval/info")
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
           MenuEvaluate me=menuEvaluateService.getEvalByMid(mid);
           if(me!=null)
           {
        	   model.put("data", me);
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
