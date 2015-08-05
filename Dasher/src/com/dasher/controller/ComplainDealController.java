package com.dasher.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.ComplainDeal;
import com.dasher.service.ComplainDealService;
import com.dasher.util.DateUtil;
import com.dasher.util.StringHelper;

@Controller
public class ComplainDealController extends MyController {

	@Autowired
	private ComplainDealService complainDealService;
	
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;
	
	@RequestMapping("/deal/add")
	@ResponseBody
	protected void add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String mobilePhone=getString(request, "mobilePhone");
		ComplainDeal cd=new ComplainDeal();
		cd.setMobilePhone(mobilePhone);
		cd.setPhoneCode(StringHelper.getCode());
		cd.setCreateDate(DateUtil.getCurrentDateStr());
		result=complainDealService.add(cd);
		
	}	
	
	@RequestMapping("/deal/update")
	@ResponseBody
	protected void update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		
		String mobilePhone=getString(request, "mobilePhone");
		ComplainDeal cd=new ComplainDeal();
		cd.setMobilePhone(mobilePhone);
		cd.setPhoneCode(StringHelper.getCode());
		cd.setUpdateDate(DateUtil.getCurrentDateStr());
		result=complainDealService.update(cd);
		
	}	
	
	@RequestMapping("/deal/info")
	@ResponseBody
	protected Object info(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String mobilePhone=getString(request, "mobilePhone");
		ComplainDeal cd=complainDealService.getByTel(mobilePhone);
		model.put("data", cd);
		return model;
	}	
	
	/*
	 * 
	 * 
	 * 处理验证码
	 * 
	 * 
	 */
	@RequestMapping("/deal/handle")
	@ResponseBody
	protected Object handle(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String mobilePhone=getString(request, "mobilePhone");
		ComplainDeal cd=complainDealService.getByTel(mobilePhone);
		if(cd==null)
		{
			ComplainDeal cde=new ComplainDeal();
			cde.setMobilePhone(mobilePhone);
			cde.setPhoneCode(StringHelper.getCode());
			cde.setCreateDate(DateUtil.getCurrentDateStr());
			result=complainDealService.add(cde);
			
			model.put("data", cde);
		}
		else
		{
			ComplainDeal cde=new ComplainDeal();
			cde.setMobilePhone(mobilePhone);
			cde.setPhoneCode(StringHelper.getCode());
			cde.setUpdateDate(DateUtil.getCurrentDateStr());
			result=complainDealService.update(cde);
			model.put("data", cde);
		}
		
		return model;
		
	}	
	
}
