package com.dasher.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.dasher.util.FileUploadUtil;
import com.dasher.util.ShowMsg;

@Controller
public class ShopDishController extends MyController {

	@Autowired
	private ShopDishService shopDishService;
	@Autowired
	private ShopDishTypeService shopDishTypeService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/dish/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		String authCode=getHeadersInfo(request,"X-Auth-Token");
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
		else if(l.getType()!=1)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
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
	@RequestMapping("phone/dish/list")
	@ResponseBody
	protected Object phoneList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String authCode=getHeadersInfo(request,"X-Auth-Token");
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
		else if(l.getType()!=1)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		String sid=getString(request, "sid");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			//获取指定商家的菜品列表
			List<ShopDishType> typeList=shopDishTypeService.listBySid(sid);
			List<ShopDish> list=shopDishService.listBySid(sid);
			if(typeList.size()>0&&list.size()>0)
			{
				model.put("list", list);
				model.put("typelist", typeList);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("list", null);
				model.put("typelist", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
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
			UUID uuid=UUID.randomUUID();
			String str[]=uuid.toString().split("-");
			String did="";
			for(int i=0;i<str.length;i++)
			{
				did=did+str[i];
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

	@RequestMapping("/dish/file")
	@ResponseBody
	protected Object uploadFile(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		model=new ModelMap();
		
		Map<String,String> dataMap=FileUploadUtil.uploadFile(request, "/upload/shop/dish");
		if(dataMap==null){
			resultCode=1;
			resultDesc=ShowMsg.imageUploadFail;
			
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		
		String authCode=dataMap.get("authCode");
		String sid=dataMap.get("sid");
		String fileName=dataMap.get("fileName");
		
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
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<ShopDish> list=null;
			try 
			{
				if(fileName.toUpperCase().indexOf(".xls")>0)
				{
					list=FileUploadUtil.readXls(request, "/upload/shop/dish/"+fileName);
				}
				else
				{
					list=FileUploadUtil.readXlsx(request, "/upload/shop/dish/"+fileName);
				}
				
				int count=shopDishService.getCountBySid(sid);
				if(count>0)
				{
					ShopDish sd=new ShopDish();
					sd.setSid(sid);
					sd.setUpdateBy(Integer.parseInt(myloginId));
					sd.setUpdateDate(DateUtil.getCurrentDateStr());
					result=shopDishService.deleteList(sd);
				}
				
				if(list!=null){
					//餐品保存
					for(ShopDish s:list)
					{
						String name=s.getName();
						String price=s.getPrice()+"";
						String typeId=s.getTypeId()+"";
						String chilies=s.getChilies();
						String description=s.getDescription();
						UUID uuid=UUID.randomUUID();
						String str[]=uuid.toString().split("-");
						String did="";
						for(int i=0;i<str.length;i++)
						{
							did=did+str[i];
						}
						ShopDish sd2=new ShopDish();
						sd2.setSid(sid);
						sd2.setDid(did);
						sd2.setName(name);
						sd2.setPrice(Float.parseFloat(price));
						sd2.setTypeId(Integer.parseInt(typeId));
						sd2.setChilies(chilies);
						sd2.setDescription(description);
						sd2.setCreateBy(Integer.parseInt(myloginId));
						sd2.setCreateDate(DateUtil.getCurrentDateStr());
						result=shopDishService.add(sd2);
					}
				}
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
			} catch (InvalidFormatException e) {
				e.printStackTrace();
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
		else if(l.getType()!=0)
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
		else if(l.getType()!=0)
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
		else if(l.getType()!=0)
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
	
	@RequestMapping("/dish/list")
	@ResponseBody
	protected Object pclist(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
			int count=shopDishService.getShopDishCount(sid, typeId, searchStr);
			if(count>0)
			{
				model.put("count", count);
				List<ShopDish> list=shopDishService.list(sid, typeId, searchStr, startRow, pageSize);
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
