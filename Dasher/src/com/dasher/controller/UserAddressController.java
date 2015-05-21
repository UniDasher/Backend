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
import com.dasher.service.LoginService;
import com.dasher.service.UserAddressService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class UserAddressController extends MyController {

	@Autowired
	private UserAddressService userAddressService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/address/add")
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
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");

		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
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
			UserAddress ua=new UserAddress();
			ua.setUid(uid);
			ua.setAddress(address);
			ua.setLatitude(latitude);
			ua.setLongitude(longitude);
			ua.setCreateBy(myloginId);
			ua.setCreateDate(DateUtil.getCurrentDateStr());
			result=userAddressService.add(ua);
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

	@RequestMapping("/address/delete")
	@ResponseBody
	protected Object delete(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String id=getString(request, "id");

		if(id==""||id.matches("^[0-9]*$")==false)
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			UserAddress ua=new UserAddress();
			ua.setId(Integer.parseInt(id));
			ua.setStatus(0);
			ua.setUpdateBy(myloginId);
			ua.setUpdateDate(DateUtil.getCurrentDateStr());
			result=userAddressService.delete(ua);
			if(result==true)
			{
				resultCode=0;
				resultDesc=ShowMsg.delSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.delFail;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	
	@RequestMapping("/address/update")
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
		String id=getString(request, "id");

		if(id==""||id.matches("^[0-9]*$")==false)
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			UserAddress u=userAddressService.getByStatus();
			if(u!=null)
			{
				UserAddress uad=new UserAddress();
				uad.setId(u.getId());
				uad.setStatus(1);
				uad.setUpdateBy(myloginId);
				uad.setUpdateDate(DateUtil.getCurrentDateStr());
				result=userAddressService.updateStatus(uad);
			}
			
			UserAddress ua=new UserAddress();
			ua.setId(Integer.parseInt(id));
			ua.setStatus(2);
			ua.setUpdateBy(myloginId);
			ua.setUpdateDate(DateUtil.getCurrentDateStr());
			result=userAddressService.updateStatus(ua);
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

	@RequestMapping("/address/list")
	@ResponseBody
	protected Object list(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String uid=getString(request, "uid");
		List<UserAddress> list=userAddressService.list(uid);
		if(list.size()>0)
		{
			model.put("list", list);
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}
		else
		{
			resultDesc=ShowMsg.findFail;
			resultCode=1;
		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

}
