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

import com.dasher.model.Login;
import com.dasher.model.ShopDishType;
import com.dasher.service.LoginService;
import com.dasher.service.ShopDishService;
import com.dasher.service.ShopDishTypeService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class ShopDishController extends MyController {

	@Autowired
	private ShopDishService shopDishService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/dish/add")
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
		else if(l.getType()>0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", loginService.userHandleLogin(myloginId));
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	

	

}
