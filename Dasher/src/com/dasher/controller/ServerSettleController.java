package com.dasher.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.*;
import com.dasher.service.*;
import com.dasher.util.ShowMsg;

@Controller
public class ServerSettleController extends MyController{
	@Autowired
	private ServerSettleService serverSettleService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/settle/server/list")
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
			int count=serverSettleService.getCount(searchStr,startDate,endDate);
			if(count>0)
			{
				model.put("count", count);
				List<ServerSettle> list=serverSettleService.list(searchStr,startDate,endDate,startRow, pageSize);
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
}
