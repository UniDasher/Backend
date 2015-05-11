package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.dasher.model.ShopDish;
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
//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);
		
		String sid=getString(request, "sid");
		String name=getString(request, "name");
		String price=getString(request, "price");
		String typeId=getString(request, "typeId");
		String chilies=getString(request, "chilies");
		String description=getString(request, "description");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.ShopDishNull;
			resultCode=2;
		}
		else if(price=="")
		{
			resultDesc=ShowMsg.priceNull;
			resultCode=2;
		}
		else if(!price.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.priceErr;
			resultCode=2;
		}
		else if(typeId=="")
		{
			resultDesc=ShowMsg.typeIdNull;
			resultCode=2;
		}
		else if(!typeId.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else if(chilies=="")
		{
			resultDesc=ShowMsg.chiliesNull;
			resultCode=2;
		}
		else
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String []strs=sdf.format(date).split("-");
			String did="";
			for(int i=0;i<strs.length;i++)
			{
				did=did+strs[i];
			}
			
			ShopDish sd=new ShopDish();
			sd.setSid(sid);
			sd.setDid(did);
			sd.setName(name);
			sd.setPrice(Float.parseFloat(price));
			sd.setTypeId(Integer.parseInt(typeId));
			sd.setChilies(chilies);
			sd.setDescription(description);
			sd.setCreateBy(Integer.parseInt(myloginId));
			sd.setCreateDate(DateUtil.getCurrentDateStr());
			result=shopDishService.add(sd);
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

	@RequestMapping("/dish/update")
	@ResponseBody
	protected Object update(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String did=getString(request, "did");
		String name=getString(request, "name");
		String price=getString(request, "price");
		String typeId=getString(request, "typeId");
		String chilies=getString(request, "chilies");
		String description=getString(request, "description");
		
		if(did=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.ShopDishNull;
			resultCode=2;
		}
		else if(price=="")
		{
			resultDesc=ShowMsg.priceNull;
			resultCode=2;
		}
		else if(!price.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.priceErr;
			resultCode=2;
		}
		else if(typeId=="")
		{
			resultDesc=ShowMsg.typeIdNull;
			resultCode=2;
		}
		else if(!typeId.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else if(chilies=="")
		{
			resultDesc=ShowMsg.chiliesNull;
			resultCode=2;
		}
		else
		{
			ShopDish sd=new ShopDish();
			sd.setDid(did);
			sd.setName(name);
			sd.setPrice(Float.parseFloat(price));
			sd.setTypeId(Integer.parseInt(typeId));
			sd.setChilies(chilies);
			sd.setDescription(description);
			sd.setUpdateBy(Integer.parseInt(myloginId));
			sd.setUpdateDate(DateUtil.getCurrentDateStr());
			result=shopDishService.update(sd);
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
	
	@RequestMapping("/dish/delete")
	@ResponseBody
	protected Object delete(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String did=getString(request, "did");
		if(did=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			ShopDish sd=new ShopDish();
			sd.setDid(did);
			sd.setUpdateBy(Integer.parseInt(myloginId));
			sd.setUpdateDate(DateUtil.getCurrentDateStr());
			result=shopDishService.delete(sd);
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

	@RequestMapping("/dish/info")
	@ResponseBody
	protected Object info(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String did=getString(request, "did");
		if(did=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			ShopDish sd=shopDishService.getByDid(did);
			if(sd!=null)
			{
				model.put("data", sd);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/dish/pc/list")
	@ResponseBody
	protected Object pclist(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String mycurPage=getString(request, "curPage");
		String mypageSize=getString(request, "countPage");
		String sid=getString(request, "sid");
		String typeId=getString(request, "typeId");
		String searchStr=getString(request, "searchStr");
		if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
		{
			int curPage=Integer.parseInt(mycurPage);
			int pageSize=Integer.parseInt(mypageSize);
			int startRow=(curPage-1)*pageSize;
			List<ShopDish> list=shopDishService.list(sid, typeId, searchStr, startRow, pageSize);
			if(list.size()>0)
			{
				model.put("count", list.size());
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				resultDesc=ShowMsg.findFail;
				resultCode=1;
			}
		
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	
}
