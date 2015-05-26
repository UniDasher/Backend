package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Login;
import com.dasher.model.Market;
import com.dasher.model.MarketCommodity;
import com.dasher.model.Menu;
import com.dasher.model.MenuEvaluate;
import com.dasher.model.Shop;
import com.dasher.model.ShopDish;
import com.dasher.service.LoginService;
import com.dasher.service.MarketCommodityService;
import com.dasher.service.MarketService;
import com.dasher.service.MenuEvaluateService;
import com.dasher.service.MenuService;
import com.dasher.util.DateUtil;
import com.dasher.util.FileUploadUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MarketCommodityController extends MyController {

	@Autowired
	private MarketCommodityService marketCommodityService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("/commodity/add")
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

		String smid=getString(request, "smid");
		String name=getString(request, "name");
		String unit=getString(request, "unit");
		String type=getString(request, "type");
		String subscribe=getString(request, "subscribe");
		if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.marketNull;
			resultCode=2;
		}
		else if(unit=="")
		{
			resultDesc=ShowMsg.unitNull;
			resultCode=2;
		}
		else if(type=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else
		{
			
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String strs[]=sdf.format(date).split("-");
			String mcid="";
			for(int i=0;i<strs.length;i++)
			{
				mcid=mcid+strs[i];
			}
			
			MarketCommodity mc=new MarketCommodity();
			mc.setSmid(smid);
			mc.setMcid(mcid);
			mc.setName(name);
			mc.setUnit(unit);
			mc.setType(type);
			mc.setSubscribe(subscribe);
			mc.setCreateBy(myloginId);
			mc.setCreateDate(DateUtil.getCurrentDateStr());
			
			result=marketCommodityService.add(mc);
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

	@RequestMapping("/commodity/update")
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

		String mcid=getString(request, "mcid");
		String name=getString(request, "name");
		String unit=getString(request, "unit");
		String type=getString(request, "type");
		String subscribe=getString(request, "subscribe");
		if(mcid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.marketNull;
			resultCode=2;
		}
		else if(unit=="")
		{
			resultDesc=ShowMsg.unitNull;
			resultCode=2;
		}
		else if(type=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else
		{
			MarketCommodity mc=new MarketCommodity();
			mc.setMcid(mcid);
			mc.setName(name);
			mc.setUnit(unit);
			mc.setType(type);
			mc.setSubscribe(subscribe);
			mc.setUpdateBy(myloginId);
			mc.setUpdateDate(DateUtil.getCurrentDateStr());
			
			result=marketCommodityService.update(mc);
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

	@RequestMapping("/commodity/delete")
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

		String mcid=getString(request, "mcid");
		if(mcid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			MarketCommodity mc=new MarketCommodity();
			mc.setMcid(mcid);
			mc.setUpdateBy(myloginId);
			mc.setUpdateDate(DateUtil.getCurrentDateStr());
			result=marketCommodityService.delete(mc);
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


	@RequestMapping("/commodity/info")
	@ResponseBody
	protected Object info(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		//		model.put("authCode", loginService.userHandleLogin(myloginId));
		model.put("authCode", authCode);

		String mcid=getString(request, "mcid");
		if(mcid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			MarketCommodity mc=marketCommodityService.getByMcid(mcid);
			if(mc==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", mc);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}

		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/commodity/list")
	@ResponseBody
	protected Object list(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		String smid=getString(request, "smid");
		String searchStr=getString(request, "searchStr");
		if(!mycurPage.equals("")&&!mypageSize.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=marketCommodityService.getListCount(smid, searchStr);
				if(count>0)
				{
					model.put("count", count);
					List<MarketCommodity> list=marketCommodityService.list(smid, searchStr, startRow, pageSize);
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
		}
		else
		{
			resultDesc=ShowMsg.searchFail;
			resultCode=2;
		}
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	
	@RequestMapping("/commodity/file")
	@ResponseBody
	protected Object uploadFile(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		String fileName=FileUploadUtil.uploadFile(request, "/WEB-INF/upload/market/commodity");
		if("false".equals(fileName)){
			resultCode=1;
			resultDesc=ShowMsg.imageUploadFail;
		}
		else if(smid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			
			List<MarketCommodity> list=null;
			try 
			{
				if(fileName.toUpperCase().indexOf(".xls")>0)
				{
					list=FileUploadUtil.readCommodityXls(request, "/WEB-INF/upload/shop/dish/"+fileName);
				}
				else
				{
					list=FileUploadUtil.readCommodityXlsx(request, "/WEB-INF/upload/shop/dish/"+fileName);
				}
				
				int count=marketCommodityService.getCountBySmid(smid);
				if(count>0)
				{
					MarketCommodity mc=new MarketCommodity();
					mc.setSmid(smid);
					mc.setUpdateBy(myloginId);
					mc.setUpdateDate(DateUtil.getCurrentDateStr());
					result=marketCommodityService.deleteList(mc);
				}
				
				for(MarketCommodity mc:list)
				{
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
					Date date=new Date();
					String strs[]=sdf.format(date).split("-");
					String mcid="";
					for(int i=0;i<strs.length;i++)
					{
						mcid=mcid+strs[i];
					}
					
					MarketCommodity mc2=new MarketCommodity();
					mc2.setSmid(mc.getSmid());
					mc2.setMcid(mcid);
					mc2.setName(mc.getName());
					mc2.setUnit(mc.getUnit());
					mc2.setType(mc.getType());
					mc2.setSubscribe(mc.getSubscribe());
					mc2.setCreateBy(myloginId);
					mc2.setCreateDate(DateUtil.getCurrentDateStr());
					
					result=marketCommodityService.add(mc2);
					
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

}
