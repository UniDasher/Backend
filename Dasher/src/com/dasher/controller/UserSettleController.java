package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.UserSettle;
import com.dasher.service.LoginService;
import com.dasher.service.UserSettleService;
import com.dasher.util.ShowMsg;

@Controller
public class UserSettleController extends MyController {

	@Autowired
	private UserSettleService userSettleService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/settle/user")
	@ResponseBody
	protected Object settleUser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String fileName=sdf.format(date);
		fileName=fileName+".xlsx";
		boolean result=userSettleService.settleUser(request,uid,myloginId,fileName);
        
		if(result==true)
		{
			model.put("fileName","/upload/settle/user/"+fileName);	
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
	@RequestMapping("/settle/user/all")
	@ResponseBody
	protected Object settleUserAll(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String searchStr=getString(request, "searchStr");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String fileName=sdf.format(date);
		fileName=fileName+".xlsx";
		boolean result=userSettleService.settleUserAll(request,searchStr,myloginId,fileName);
        
		if(result==true)
		{
			model.put("fileName","/upload/settle/user/"+fileName);	
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
	
	@RequestMapping("/settle/user/list")
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
		
		String mycurPage=getString(request, "curPage");
		String mypageSize=getString(request, "countPage");
		String searchStr=getString(request, "searchStr");
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
		{
			int curPage=Integer.parseInt(mycurPage);
			int pageSize=Integer.parseInt(mypageSize);
			int startRow=(curPage-1)*pageSize;
			int count=userSettleService.getCount(searchStr,startDate,endDate);
			if(count>0)
			{
				model.put("count", count);
				List<UserSettle> list=userSettleService.list(searchStr,startDate,endDate,startRow, pageSize);
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
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	
	@RequestMapping("/settle/user/info/list")
	@ResponseBody
	protected Object infolist(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String wid=getString(request, "wid");
		if(wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<UserSettle> list=userSettleService.getListByWid(wid);
			if(list.size()>0)
			{
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findFail;
				resultCode=0;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
}
