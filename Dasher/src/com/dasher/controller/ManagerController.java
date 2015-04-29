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
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Manager;
import com.dasher.service.ManagerService;
import com.dasher.util.DateUtil;
import com.dasher.util.MyMD5Util;
import com.dasher.util.ShowMsg;

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
		
		if(account=="")
		{
			resultDesc=ShowMsg.userNull;
			resultCode=2;
		}
		else if(password.length()<6||password.length()>30)
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=2;
		}
		else if(firstName=="")
		{
			resultDesc=ShowMsg.FirstNameNull;
			resultCode=2;
		}
		else if(lastName=="")
		{
			resultDesc=ShowMsg.LastNameNull;
			resultCode=2;
		}
		else if(email=="")
		{
			resultDesc=ShowMsg.EmailNull;
			resultCode=2;
		}
		
		else
		{
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
					resultDesc=ShowMsg.addSuc;
				}
				else
				{
					resultCode=1;
					resultDesc=ShowMsg.addFail;
				}

			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.addRepeat;
			}
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
		String myid=getString(request, "id");
		if(!myid.equals("")&&myid.matches("^[0-9]*$"))
		{
			int id=Integer.parseInt(myid);
			Manager m=managerService.getById(id);
			if(m!=null)
			{
				resultCode=0;
				model.put("Manager", m);
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		else
		{
			resultCode=2;
			resultDesc=ShowMsg.ParFail;
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
		String myid=getString(request, "id");
		String firstName=getString(request, "firstName");
		String lastName=getString(request, "lastName");
		String email=getString(request, "email");
		int type=getInt(request, "type");
		
		if(!myid.equals("")&&myid.matches("^[0-9]*$"))
		{
			int id=Integer.parseInt(myid);
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
				resultDesc=ShowMsg.updateSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.updateFail;
			}
		}
		else
		{
			resultCode=2;
			resultDesc=ShowMsg.ParFail;
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
		String myid=getString(request, "id");
		if(!myid.equals("")&&myid.matches("^[0-9]*$"))
		{
			int id=Integer.parseInt(myid);
			Manager m=new Manager();
			m.setId(id);
			m.setUpdateBy(id);
			m.setUpdateDate(DateUtil.getCurrentDateStr());
			result=managerService.delete(m);
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
		else
		{
			resultCode=2;
			resultDesc=ShowMsg.ParFail;
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
			resultDesc=ShowMsg.findSuc;
		}
		else
		{
			resultCode=1;
			resultDesc=ShowMsg.findFail;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/admin/login")
	@ResponseBody
	protected Object login(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String account=getString(request, "account");
		String password=getString(request, "password");
		if(account=="")
		{
			resultDesc=ShowMsg.userNull;
			resultCode=2;
		}
		else if(password.length()>5&&password.length()<31)
		{
			int flag=managerService.managerLoin(account, password);
			if(flag==1)
			{
				resultDesc=ShowMsg.userNameNull;
				resultCode=1;
			}
			else if(flag==2)
			{
				resultDesc=ShowMsg.pwdErr;
				resultCode=1;
			}
			else if(flag==0)
			{
				resultDesc=ShowMsg.loginSuc;
				resultCode=0;
				Manager m=managerService.getByAccount(account);
				model.put("firstName", m.getFirstName());
				model.put("lastName", m.getLastName());
				model.put("type", m.getType());
				session.setAttribute("account", account);
				session.setAttribute("password", password);
			}
			else
			{
				resultDesc=ShowMsg.loginFail;
				resultCode=1;
			}
		}
		else
		{
			resultDesc=ShowMsg.pwdLength;
			resultCode=2;
		}
		
		
			
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);	
		return model;
	}
	
	@RequestMapping("/getSess")
	@ResponseBody
	protected Object getSess(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		response.setContentType("text/html;charset=utf-8");
		String account=(String)session.getAttribute("account");
		String password=(String)session.getAttribute("password");
		model.put("account", account);
		model.put("password", password);
		return model;
	}
	
}
