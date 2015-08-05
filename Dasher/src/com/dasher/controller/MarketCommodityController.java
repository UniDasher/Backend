package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.dasher.model.MarketCommodity;
import com.dasher.model.ShopDishType;
import com.dasher.service.LoginService;
import com.dasher.service.MarketCommodityService;
import com.dasher.service.ShopDishTypeService;
import com.dasher.util.DateUtil;
import com.dasher.util.FileUploadUtil;
import com.dasher.util.ShowMsg;

@Controller
public class MarketCommodityController extends MyController {

	@Autowired
	private MarketCommodityService marketCommodityService;
	@Autowired
	private ShopDishTypeService shopDishTypeService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/commodity/list")
	@ResponseBody
	protected Object phoneList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
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
		String smid=getString(request, "smid");
		
		if(!smid.equals(""))
		{
			//手机端获取超市的商品列表
			//获取用户的下单列表
			List<ShopDishType> typeList=shopDishTypeService.listBySmid(smid);
			List<MarketCommodity> list=marketCommodityService.listBySmid(smid);
			if(typeList.size()>0&&list.size()>0)
			{
				model.put("typelist", typeList);
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("typelist", null);
				model.put("list", null);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
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
	
	@RequestMapping("phone/commodity/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
		String myloginId=loginService.getByAuthCode(authCode);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
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
		
		String smid=getString(request, "smid");
		String name=getString(request, "name");
		String price=getString(request, "price");
		String unit=getString(request, "unit");
		String typeId=getString(request, "typeId");
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
		else if(price=="")
		{
			resultDesc=ShowMsg.priceNull;
			resultCode=2;
		}
		else if(unit=="")
		{
			resultDesc=ShowMsg.unitNull;
			resultCode=2;
		}
		else if(typeId=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else if(!typeId.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else
		{

			//验证金额
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
			Matcher matcher=pattern.matcher(price);
			if(matcher.matches()==false)
			{
				resultDesc=ShowMsg.dishsMoneyErr;
				resultCode=2;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);
				return model;
			}

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
			mc.setPrice(Float.parseFloat(price));
			mc.setUnit(unit);
			mc.setTypeId(Integer.parseInt(typeId));
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
		else if(l.getType()!=0)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);

		String mcid=getString(request, "mcid");
		String name=getString(request, "name");
		String price=getString(request, "price");
		String unit=getString(request, "unit");
		String typeId=getString(request, "typeId");
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
		else if(price=="")
		{
			resultDesc=ShowMsg.priceNull;
			resultCode=2;
		}
		else if(unit=="")
		{
			resultDesc=ShowMsg.unitNull;
			resultCode=2;
		}
		else if(typeId=="")
		{
			resultDesc=ShowMsg.typeNull;
			resultCode=2;
		}
		else if(!typeId.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.typeErr;
			resultCode=2;
		}
		else
		{
			//验证金额
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
			Matcher matcher=pattern.matcher(price);
			if(matcher.matches()==false)
			{
				resultDesc=ShowMsg.dishsMoneyErr;
				resultCode=2;
				model.put("resultCode", resultCode);	
				model.put("resultDesc", resultDesc);
				return model;
			}
			MarketCommodity mc=new MarketCommodity();
			mc.setMcid(mcid);
			mc.setName(name);
			mc.setPrice(Float.parseFloat(price));
			mc.setUnit(unit);
			mc.setTypeId(Integer.parseInt(typeId));
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

	@RequestMapping("/commodity/file")
	@ResponseBody
	protected Object uploadFile(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		model=new ModelMap();
		
		Map<String,String> dataMap=FileUploadUtil.uploadFile(request, "/WEB-INF/upload/market/dish");
		if(dataMap==null){
			resultCode=1;
			resultDesc=ShowMsg.imageUploadFail;
			
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		String authCode=dataMap.get("authCode");
		String smid=dataMap.get("smid");
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

		if(smid=="")
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
					list=FileUploadUtil.readCommodityXls(request, "/WEB-INF/upload/market/dish/"+fileName);
				}
				else
				{
					list=FileUploadUtil.readCommodityXlsx(request, "/WEB-INF/upload/market/dish/"+fileName);
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
				if(list!=null){
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
						mc2.setSmid(smid);
						mc2.setMcid(mcid);
						mc2.setName(mc.getName());
						mc2.setPrice(mc.getPrice());
						mc2.setTypeId(mc.getTypeId());
						mc2.setUnit(mc.getUnit());
						mc2.setSubscribe(mc.getSubscribe());
						mc2.setCreateBy(myloginId);
						mc2.setCreateDate(DateUtil.getCurrentDateStr());
						result=marketCommodityService.add(mc2);
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
		model.put("authCode", authCode);
		
		String mycurPage=getString(request, "curPage");  
		String mypageSize=getString(request, "countPage");//每页的数据数
		String smid=getString(request, "smid");
		String typeId=getString(request, "typeId");
		String searchStr=getString(request, "searchStr");
		if(!mycurPage.equals("")&&!mypageSize.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				//此处查询修改，参数typeId查询
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=marketCommodityService.getListCount(smid,typeId, searchStr);
				if(count>0)
				{
					model.put("count", count);
					List<MarketCommodity> list=marketCommodityService.list(smid,typeId, 
							searchStr, startRow, pageSize);
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
}
