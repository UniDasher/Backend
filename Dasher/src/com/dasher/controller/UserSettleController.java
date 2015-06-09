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

import com.dasher.model.UserSettle;
import com.dasher.service.LoginService;
import com.dasher.service.UserSettleService;
import com.dasher.util.DateUtil;
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
			model.put("fileName","/WEB-INF/upload/settle/user/"+fileName);	
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
			model.put("fileName","/WEB-INF/upload/settle/user/"+fileName);	
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
	
	
	@RequestMapping("/settle/user/add")
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
		//model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String wid=getString(request, "wid");
		String settleType=getString(request, "settleType");
		String settlePrice=getString(request, "settlePrice");
		String settleNumberType=getString(request, "settleNumberType");
		String settleNumber=getString(request, "settleNumber");
		String settleDesc=getString(request, "settleDesc");

		if(wid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(settleType=="")
		{
			resultDesc=ShowMsg.settleTypeNull;
			resultCode=2;
		}
		else if(!settleType.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.priceErr;
			resultCode=2;
		}
		else if(settlePrice=="")
		{
			resultDesc=ShowMsg.settlePriceNull;
			resultCode=2;
		}
		else if(settleNumberType=="")
		{
			resultDesc=ShowMsg.settleNumberTypeNull;
			resultCode=2;
		}
		else if(settleNumber=="")
		{
			resultDesc=ShowMsg.settleNumberNull;
			resultCode=2;
		}
		
		else
		{
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
		    if(!settlePrice.equals(""))
			{
				Matcher matcher=pattern.matcher(settlePrice);
				if(matcher.matches()==false)
				{
					resultDesc=ShowMsg.settlePriceErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
            UserSettle us=new UserSettle();
            us.setWid(wid);
            us.setSettleDesc(settleType);
            us.setSettlePrice(Float.parseFloat(settlePrice));
            us.setSettleNumberType(settleNumberType);
            us.setSettleNumber(settleNumber);
            us.setSettleDesc(settleDesc);
            us.setCreateBy(myloginId);
            us.setCreateDate(DateUtil.getCurrentDateStr());
            result=userSettleService.add(us);
            
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
	
	
	@RequestMapping("/settle/user/update")
	@ResponseBody
	protected Object update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		//model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
//		String wid=getString(request, "wid");
//		String settleType=getString(request, "settleType");
//		String settlePrice=getString(request, "settlePrice");
//		String settleNumberType=getString(request, "settleNumberType");
//		String settleNumber=getString(request, "settleNumber");
//		String settleDesc=getString(request, "settleDesc");
//
//		if(wid=="")
//		{
//			resultDesc=ShowMsg.ParFail;
//			resultCode=2;
//		}
//		else if(settleType=="")
//		{
//			resultDesc=ShowMsg.settleTypeNull;
//			resultCode=2;
//		}
//		else if(settlePrice=="")
//		{
//			resultDesc=ShowMsg.settlePriceNull;
//			resultCode=2;
//		}
//		else if(settleNumberType=="")
//		{
//			resultDesc=ShowMsg.settleNumberTypeNull;
//			resultCode=2;
//		}
//		else if(settleNumber=="")
//		{
//			resultDesc=ShowMsg.settleNumberNull;
//			resultCode=2;
//		}
//		else
//		{
//			
//            UserSettle us=new UserSettle();
//            us.setWid(wid);
//            us.setSettleType(Integer.parseInt(settleType));
//            us.setSettlePrice(Float.parseFloat(settlePrice));
//            us.setSettleNumberType(settleNumberType);
//            us.setSettleNumber(settleNumber);
//            us.setSettleDesc(settleDesc);
//            us.setCreateBy(myloginId);
//            us.setCreateDate(DateUtil.getCurrentDateStr());
//            result=userSettleService.add(us);
//            
//			if(result==true)
//			{
//				resultCode=0;
//				resultDesc=ShowMsg.addSuc;
//			}
//			else
//			{
//				resultCode=1;
//				resultDesc=ShowMsg.addFail;
//			}
//		}

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
		if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
		{
			int curPage=Integer.parseInt(mycurPage);
			int pageSize=Integer.parseInt(mypageSize);
			int startRow=(curPage-1)*pageSize;
			int count=userSettleService.getCount(searchStr);
			if(count>0)
			{
				model.put("count", count);
				List<UserSettle> list=userSettleService.list(searchStr, startRow, pageSize);
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
