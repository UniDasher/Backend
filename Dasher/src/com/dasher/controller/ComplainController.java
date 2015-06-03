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

import com.dasher.model.UserAddress;
import com.dasher.service.ComplainService;
import com.dasher.service.LoginService;
import com.dasher.service.UserAddressService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class ComplainController extends MyController {

	@Autowired
	private ComplainService complainService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/complain/add")
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
		String uid=getString(request, "uid");
		String mid=getString(request, "mid");
		String content=getString(request, "content");

		if(uid==""||mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(content=="")
		{
			resultDesc=ShowMsg.contentNull;
			resultCode=2;
		}
		else
		{
//			UserAddress ua=new UserAddress();
//			ua.setUid(uid);
//			ua.setAddress(address);
//			ua.setLatitude(latitude);
//			ua.setLongitude(longitude);
//			ua.setCreateBy(myloginId);
//			ua.setCreateDate(DateUtil.getCurrentDateStr());
//			result=userAddressService.add(ua);
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
		}


		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	
}
