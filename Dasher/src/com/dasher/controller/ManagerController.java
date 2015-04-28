package com.dasher.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Manager;
import com.dasher.service.ManagerService;
import com.dasher.util.DateUtil;
import com.dasher.util.MyMD5Util;

@Controller
public class ManagerController extends MyController {

	@Autowired
	private ManagerService managerService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;
	
	@RequestMapping("/admin/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		
		model=new ModelMap();
		String account=getString(request, "account");
		String password=getString(request, "password");
		String firstName=getString(request, "firstName");
		String lastName=getString(request, "lastName");
		String email=getString(request, "email");
		int type=getInt(request, "type");
		UUID uuid=UUID.randomUUID();
		String str[]=uuid.toString().split("-");
		String salt="";
		for(int i=0;i<str.length;i++)
		{
			salt=salt+str[i];
		}
		Manager man=managerService.getByAccount(account);
		if(man==null)
		{
			Manager m=new Manager();
			m.setAccount(account);
			try {
				m.setPassword(MyMD5Util.getEncryptedPwd(password));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m.setSalt(salt);
			m.setFirstName(firstName);
			m.setLastName(lastName);
			m.setEmail(email);
			m.setType(type);
			m.setCreateDate(DateUtil.getCurrentDateStr());
			result=managerService.add(m);
			if(result==true)
			{
				resultCode=0;
				resultDesc="添加管理员成功！";
			}
			else
			{
				resultCode=1;
				resultDesc="添加管理员失败！";
			}

		}
		else
		{
			resultCode=2;
			resultDesc="用户名已存在，请重新输入！";
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/admin/get")
	@ResponseBody
	protected Object get(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		int id=getInt(request, "id");
		Manager m=managerService.getById(id);
		if(m!=null)
		{
			resultCode=0;
			model.put("Manager", m);
			resultDesc="查询管理员成功！";
		}
		else
		{
			resultCode=1;
			resultDesc="查询管理员失败！";
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/admin/update")
	@ResponseBody
	protected Object update(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		int id=getInt(request, "id");
		String firstName=getString(request, "firstName");
		String lastName=getString(request, "lastName");
		String email=getString(request, "email");
		int type=getInt(request, "type");
		Manager m=new Manager();
		m.setId(id);
		m.setFirstName(firstName);
		m.setLastName(lastName);
		m.setEmail(email);
		m.setType(type);
		m.setUpdateBy(id);
		m.setUpdateDate(DateUtil.getCurrentDateStr());
		result=managerService.update(m);
		if(result==true)
		{
			resultCode=0;
			resultDesc="修改管理员成功！";
		}
		else
		{
			resultCode=1;
			resultDesc="修改管理员失败！";
		}
	
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/admin/delete")
	@ResponseBody
	protected Object delete(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		int id=getInt(request, "id");
		Manager m=new Manager();
		m.setId(id);
		m.setUpdateBy(id);
		m.setUpdateDate(DateUtil.getCurrentDateStr());
		result=managerService.delete(m);
		if(result==true)
		{
			resultCode=0;
			resultDesc="删除管理员成功！";
		}
		else
		{
			resultCode=1;
			resultDesc="删除管理员失败！";
		}
	
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/admin/list")
	@ResponseBody
	protected Object list(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		List<Manager> list=managerService.list();
		if(list.size()>0)
		{
			resultCode=0;
			model.put("list", list);
			resultDesc="查询管理员成功！";
		}
		else
		{
			resultCode=1;
			resultDesc="查询管理员失败！";
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	//@RequestMapping("/admin/login")
	@ResponseBody
	protected Object login(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String account=getString(request, "account");
		String password=getString(request, "password");
		int flag=managerService.managerLoin(account, password);
		if(flag==1)
		{
			resultDesc="用户名不存在";
		}
		else if(flag==2)
		{
			resultDesc="密码错误";
		}
		else if(flag==0)
		{
			resultDesc="登录成功";
		}
			
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
}
