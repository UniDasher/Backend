package com.dasher.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dasher.model.User;
import com.dasher.service.UserService;



@Controller
public class UserController2 extends MyController {

	@Autowired
	private UserService userService;

	private boolean result=false;
	private String showInfo="";
	private String nextPage="";
	ModelMap model=new ModelMap();

	@RequestMapping("/addUser.do")
	protected ModelAndView addUser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String account=getString(request, "account");
		String password=getString(request, "password");
		String equmentNumber=getString(request, "equmentNumber");
		User u=new User();
		u.setAccount(account);
		u.setPassword(password);
		u.setEqumentNumber(equmentNumber);
		result=userService.addUser(u);
		if(result==true)
		{
			showInfo="添加用户成功！";
		}
		else
			showInfo="添加用户失败！";
		nextPage="index";

		return new ModelAndView(nextPage,"showInfo",showInfo);

	}	
}
