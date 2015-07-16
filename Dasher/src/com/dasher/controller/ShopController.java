package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dasher.model.Login;
import com.dasher.model.Shop;
import com.dasher.service.LoginService;
import com.dasher.service.ShopService;
import com.dasher.util.DateUtil;
import com.dasher.util.FileUploadUtil;
import com.dasher.util.ShowMsg;



@Controller
public class ShopController extends MyController {
    @Autowired
	private ShopService shopService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;
	
	@RequestMapping("phone/shop/list/near")
	@ResponseBody
	protected Object listNear(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
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
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		//业务逻辑
		if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.LonLatNull;
			resultCode=2;
		}
		else
		{
			List<Shop> shopList=shopService.getListByLati(Double.parseDouble(longitude) ,
					Double.parseDouble(latitude),ShowMsg.distance);
			model.put("list", shopList);
			model.put("count", shopList.size());
			resultDesc=ShowMsg.findSuc;
			resultCode=0;
		}

		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("phone/shop/type")
	@ResponseBody
	protected Object shopType(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		String authCode=getHeadersInfo(request,ShowMsg.X_Auth_Token);
		String myloginId=loginService.getByAuthCode(authCode);
		//Login l=loginService.getByLogId(myloginId);
		if("".equals(authCode)||"".equals(myloginId)||myloginId==null||myloginId.equals(""))
		{
			resultDesc=ShowMsg.NoLogin;
			resultCode=3;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		
		List<ModelMap> s=shopService.getShopType();
		model.put("list", s);
		resultCode=0;
		resultDesc=ShowMsg.findSuc;
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/shop/info")
	@ResponseBody
	protected Object phoneInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
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
		String sid=getString(request, "sid");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Shop s=shopService.getBySid(sid);
			if(s==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", s);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("/shop/add")
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
		String name=getString(request, "name");
		String typeTab=getString(request, "typeTab");
		String subscribe=getString(request, "subscribe");
		String email=getString(request, "email");
		String phone=getString(request, "phone");
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		
		if(name=="")
		{
			resultDesc=ShowMsg.shopNull;
			resultCode=2;
		}
		else if(typeTab=="")
		{
			resultDesc=ShowMsg.typeTabNull;
			resultCode=2;
		}
//		else if(email=="")
//		{
//			resultDesc=ShowMsg.EmailNull;
//			resultCode=2;
//		}
		else if(phone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=2;
		}
		
		else if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.LonLatNull;
			resultCode=2;
		}
		
		else
		{
//			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
//			Matcher matcher=pattern.matcher(email);
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(phone);
//			if(matcher.matches()==false)
//			{
//				resultCode=2;
//				resultDesc=ShowMsg.emailErr;
//			}
//			else 
			if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				Shop shop=shopService.getByName(name);
				if(shop==null)
				{
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
					Date date=new Date();
					String strs[]=sdf.format(date).split("-");
					String sid="";
					for(int i=0;i<strs.length;i++)
					{
						sid=sid+strs[i];
					}
					Shop s=new Shop();
					s.setSid(sid);
					s.setName(name);
					s.setTypeTab(typeTab);
					s.setAddress(address);
					s.setSubscribe(subscribe);
					s.setEmail(email);
					s.setPhone(phone);
					s.setLogo("/upload/shop/images/default.png");
					s.setLongitude(longitude);
					s.setLatitude(latitude);
					s.setCreateBy(Integer.parseInt(myloginId));
					s.setCreateDate(DateUtil.getCurrentDateStr());

					result=shopService.add(s);
					if(result==true)
					{
						resultCode=0;
						resultDesc=ShowMsg.addSuc;
						model.put("sid", sid);
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
					resultDesc=ShowMsg.ShopaddRepeat;
				}
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("/shop/update")
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
		String sid=getString(request, "sid");
		String name=getString(request, "name");
		String typeTab=getString(request, "typeTab");
		String subscribe=getString(request, "subscribe");
		String email=getString(request, "email");
		String phone=getString(request, "phone");
		String address=getString(request, "address");
		String longitude=getString(request, "longitude");
		String latitude=getString(request, "latitude");
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(name=="")
		{
			resultDesc=ShowMsg.shopNull;
			resultCode=2;
		}
		else if(typeTab=="")
		{
			resultDesc=ShowMsg.typeTabNull;
			resultCode=2;
		}
//		else if(email=="")
//		{
//			resultDesc=ShowMsg.EmailNull;
//			resultCode=2;
//		}
		else if(phone=="")
		{
			resultDesc=ShowMsg.MobilePhoneNull;
			resultCode=2;
		}
		else if(longitude==""||latitude=="")
		{
			resultDesc=ShowMsg.LonLatNull;
			resultCode=2;
		}
		else
		{
//			Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
//			Matcher matcher=pattern.matcher(email);
			Pattern pattern2=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher matcher2=pattern2.matcher(phone);
//			if(matcher.matches()==false)
//			{
//				resultCode=2;
//				resultDesc=ShowMsg.emailErr;
//			}
//			else 
			if(matcher2.matches()==false)
			{
				resultCode=2;
				resultDesc=ShowMsg.mobilePhoneErr;
			}
			else
			{
				Shop shop=shopService.getByName(name);
				Shop sp=shopService.getBySid(sid);
				Shop s=new Shop();
				s.setSid(sid);
				s.setName(name);
				s.setTypeTab(typeTab);
				s.setAddress(address);
				s.setSubscribe(subscribe);
				s.setEmail(email);
				s.setPhone(phone);
				s.setLongitude(longitude);
				s.setLatitude(latitude);
				s.setUpdateBy(Integer.parseInt(myloginId));
				s.setUpdateDate(DateUtil.getCurrentDateStr());
				if(shop==null)
				{
					result=shopService.update(s);
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
					if(sp.getName().equals(name))
					{
						result=shopService.update(s);
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
						resultDesc=ShowMsg.ShopaddRepeat;
					}
					
				}
			}

		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("/shop/upload")
	@ResponseBody
	protected Object upload(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		
		Map<String,String> dataMap=FileUploadUtil.uploadFile(request, "/upload/shop/images");
		if(dataMap==null){
			resultCode=1;
			resultDesc=ShowMsg.imageUploadFail;
			
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//获取参数
	    String authCode=dataMap.get("authCode");
	    String sid=dataMap.get("sid");
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
		String logo=dataMap.get("fileName");
		
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			if("false".equals(logo))
			{
				resultCode=1;
				resultDesc=ShowMsg.imageUploadFail;
			}
			else
			{
				Shop s=new Shop();
				s.setSid(sid);
				s.setLogo("/upload/shop/images/"+logo);
				s.setUpdateBy(0);
				s.setUpdateDate(DateUtil.getCurrentDateStr());
				result=shopService.updateLogo(s);
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
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	

	@RequestMapping("/shop/delete")
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
		model.put("authCode", authCode);
		String sid=getString(request, "sid");
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Shop s=new Shop();
			s.setSid(sid);
			s.setUpdateBy(Integer.parseInt(myloginId));
			s.setUpdateDate(DateUtil.getCurrentDateStr());
			result=shopService.delete(s);
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

	@RequestMapping("/shop/info")
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
		String sid=getString(request, "sid");
		if(sid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Shop s=shopService.getBySid(sid);
			if(s==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", s);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}

	@RequestMapping("/shop/list")
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
		String searchStr=getString(request, "searchStr");
		if(!mycurPage.equals("")&&!mypageSize.equals(""))
		{
			if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$"))
			{
				int curPage=Integer.parseInt(mycurPage);
				int pageSize=Integer.parseInt(mypageSize);
				int startRow=(curPage-1)*pageSize;
				int count=shopService.getShopCount(searchStr);
				if(count>0)
				{
					model.put("count", count);
					List<Shop> shopList=shopService.list(searchStr, startRow, pageSize);
					model.put("list", shopList);
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
	@RequestMapping("/shop/menu/list")
	@ResponseBody
	protected Object menuList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		List<Shop> shopList=shopService.menuList();
		List<ModelMap> list=new ArrayList<ModelMap>();
		
		for(Shop s:shopList){
			ModelMap shopModel=new ModelMap();
			shopModel.put("sid", s.getSid());
			shopModel.put("name", s.getName());
			list.add(shopModel);
		}
		model.put("list", list);
		resultDesc=ShowMsg.findSuc;
		resultCode=0;
		
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
}
