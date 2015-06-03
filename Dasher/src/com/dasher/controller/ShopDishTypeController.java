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
import com.dasher.service.ShopDishTypeService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class ShopDishTypeController extends MyController {

	@Autowired
	private ShopDishTypeService shopDishTypeService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/dish/type/add")
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String name=getString(request, "name");
		String type=getString(request, "type");
		if(name=="")
		{
			resultDesc=ShowMsg.DishTypeNull;
			resultCode=2;
		}
		else if(type=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else if(!type.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else
		{
			ShopDishType sdtype=shopDishTypeService.getByName(name);
			if(sdtype==null)
			{
				String mysortNum=shopDishTypeService.getMax();
				if(mysortNum==null||mysortNum=="")
				{
					mysortNum=0+"";
				}
				ShopDishType sdt=new ShopDishType();
				sdt.setName(name);
				sdt.setType(Integer.parseInt(type));
				sdt.setSortNum(Integer.parseInt(mysortNum.trim())+1);
				sdt.setCreateBy(Integer.parseInt(myloginId));
				sdt.setCreateDate(DateUtil.getCurrentDateStr());
				result=shopDishTypeService.add(sdt);
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
				resultDesc=ShowMsg.ShopDishTypeaddRepeat;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	

	@RequestMapping("/dish/type/update")
	@ResponseBody
	protected Object typeupdate(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String myid=getString(request, "id");
		String name=getString(request, "name");
		if(myid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.DishTypeNull;
			resultCode=2;
		}
		else if(!myid.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.inputErr;
			resultCode=2;
		}
		else
		{
			ShopDishType sdtype=shopDishTypeService.getByName(name);
			if(sdtype==null)
			{
				ShopDishType sdt=new ShopDishType();
				sdt.setId(Integer.parseInt(myid));
				sdt.setName(name);
				sdt.setUpdateBy(Integer.parseInt(myloginId));
				sdt.setUpdateDate(DateUtil.getCurrentDateStr());
				result=shopDishTypeService.update(sdt);
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
				resultCode=1;
				resultDesc=ShowMsg.ShopDishTypeaddRepeat;
			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("/dish/type/delete")
	@ResponseBody
	protected Object typedelete(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String myid=getString(request, "id");
		if(myid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(!myid.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.inputErr;
			resultCode=2;
		}
		else
		{
			ShopDishType sdt=new ShopDishType();
			sdt.setId(Integer.parseInt(myid));
			sdt.setUpdateBy(Integer.parseInt(myloginId));
			sdt.setUpdateDate(DateUtil.getCurrentDateStr());
			result=shopDishTypeService.delete(sdt);
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

	@RequestMapping("/dish/type/list")
	@ResponseBody
	protected Object typelist(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		
		String type=getString(request, "type");
		if(type=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else if(!type.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else
		{
			List<ShopDishType> list=shopDishTypeService.list(Integer.parseInt(type));
			if(list.size()>0)
			{
				model.put("list", list);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				model.put("list", null);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	


	@RequestMapping("/dish/type/sort")
	@ResponseBody
	protected Object typesort(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String id_1=getString(request, "id_1");
		String sortNum_1=getString(request, "sortNum_1");
		String id_2=getString(request, "id_2");
		String sortNum_2=getString(request, "sortNum_2");
		
		ShopDishType sdt1=new ShopDishType();
		sdt1.setId(Integer.parseInt(id_1));
		sdt1.setSortNum(Integer.parseInt(sortNum_1));
		sdt1.setUpdateBy(Integer.parseInt(myloginId));
		sdt1.setUpdateDate(DateUtil.getCurrentDateStr());
		
		ShopDishType sdt2=new ShopDishType();
		sdt2.setId(Integer.parseInt(id_2));
		sdt2.setSortNum(Integer.parseInt(sortNum_2));
		sdt2.setUpdateBy(Integer.parseInt(myloginId));
		sdt2.setUpdateDate(DateUtil.getCurrentDateStr());
		
		result=shopDishTypeService.updateSortNum(sdt1);
		if(result==true)
		{
			result=shopDishTypeService.updateSortNum(sdt2);
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
			resultCode=1;
			resultDesc=ShowMsg.updateFail;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	
	
	@RequestMapping("/shop/type/list")
	@ResponseBody
	protected Object typelistBySid(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}

		model.put("authCode", authCode);
		String sid=getString(request, "sid");
		List<ShopDishType> list=shopDishTypeService.listBySid(sid);
		if(list.size()>0)
		{
			model.put("list", list);
			resultCode=0;
			resultDesc=ShowMsg.findSuc;
		}
		else
		{
			model.put("list", null);
			resultCode=0;
			resultDesc=ShowMsg.findSuc;
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);

		return model;
	}	

	
	@RequestMapping("/market/type/list")
	@ResponseBody
	protected Object typelistBySmid(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		String smid=getString(request, "smid");
		List<ShopDishType> list=shopDishTypeService.listBySmid(smid);
		if(list.size()>0)
		{
			model.put("list", list);
			resultCode=0;
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


}
