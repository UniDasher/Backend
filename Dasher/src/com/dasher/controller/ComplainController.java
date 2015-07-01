package com.dasher.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.dasher.model.Complain;
import com.dasher.model.Login;
import com.dasher.service.ComplainService;
import com.dasher.service.LoginService;
import com.dasher.util.DateUtil;
import com.dasher.util.ShowMsg;

@Controller
public class ComplainController extends MyController {

	@Autowired
	private ComplainService complainService;
	@Autowired
	private LoginService loginService;
	private boolean result=false;
	private int resultCode;
	private String resultDesc;
	private ModelMap model;

	@RequestMapping("phone/complain/add")
	@ResponseBody
	protected Object add(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		model=new ModelMap();
		//获取参数
		String JSONStr=getJsonString(request);
	    JSONObject jsonObject=null;
	    String authCode="";
	    String mid="";
	    String uid="";
	    String wid="";
	    String type="";
	    String content="";
		try {
			jsonObject = new JSONObject(JSONStr);
			authCode = getHeadersInfo(request,"X-Auth-Token");
			mid=jsonObject.getString("mid");
			uid=jsonObject.getString("uid");
			wid=jsonObject.getString("wid");
			type=jsonObject.getString("type");
			content=jsonObject.getString("content");
		} catch (JSONException e1) {
			resultDesc="参数获取失败";
			resultCode=2;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);
			return model;
		}
		//判断是否已登录
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
		model.put("authCode", authCode);

		if(mid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(content=="")
		{
			resultDesc=ShowMsg.contentNull;
			resultCode=2;
		}
		else
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss-SSS");
			Date date=new Date();
			String strs[]=sdf.format(date).split("-");
			String comId="";
			for(int i=0;i<strs.length;i++)
			{
				comId=comId+strs[i];
			}
			Complain c=new Complain();
			c.setComId(comId);
			c.setUid(myloginId);
			c.setMid(mid);
			c.setType(Integer.parseInt(type));
			c.setWid(wid);
			c.setContent(content);
			c.setCreateBy(myloginId);
			c.setCreateDate(DateUtil.getCurrentDateStr());
			result=complainService.add(c);
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
	@RequestMapping("phone/complain/list")
	@ResponseBody
	protected Object phoneUserList(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		}else if(l.getType()!=1)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String uid=getString(request, "uid");
		String status=getString(request, "status");
		
		if(uid=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			List<Complain> list=complainService.userComList(l.getLoginId(),Integer.parseInt(status));
			resultCode=0;
			resultDesc=ShowMsg.addSuc;
			model.put("list", list);	
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("phone/complain/info")
	@ResponseBody
	protected Object phoneDealInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		}else if(l.getType()!=1)
		{
			resultDesc=ShowMsg.NoPermiss;
			resultCode=4;
			model.put("resultCode", resultCode);	
			model.put("resultDesc", resultDesc);	
			return model;
		}
		model.put("authCode", authCode);
		
		String comId=getString(request, "comId");
		String type=getString(request, "type");
		
		if(comId=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Complain c=complainService.getByComId(comId,Integer.parseInt(type));
			if(c==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", c);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}
	
	@RequestMapping("/complain/list")
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
		String mypageSize=getString(request, "countPage");
		String status=getString(request, "status");
		String searchStr=getString(request, "searchStr");
		String startDate=getString(request, "startDate");
		String endDate=getString(request, "endDate");
		
		
		if(mycurPage.matches("^[0-9]*$")&&mypageSize.matches("^[0-9]*$")&&status.matches("^[0-9]*$"))
		{
			int curPage=Integer.parseInt(mycurPage);
			int pageSize=Integer.parseInt(mypageSize);
			int startRow=(curPage-1)*pageSize;
			int count=complainService.getCount(searchStr,Integer.parseInt(status),startDate,endDate);
			if(count>0)
			{
				model.put("count", count);
				List<Complain> list=complainService.list(searchStr,Integer.parseInt(status),
						startDate,endDate, startRow, pageSize);
				model.put("list", list);
				resultDesc=ShowMsg.findSuc;
				resultCode=0;
			}
			else
			{
				model.put("count", 0);
				model.put("list", null);
				resultDesc=ShowMsg.findFail;
				resultCode=0;
			}
		
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/complain/info")
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
		
		String comId=getString(request, "comId");
		String type=getString(request, "type");
		if(comId=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else
		{
			Complain c=complainService.getByComId(comId,Integer.parseInt(type));
			if(c==null)
			{
				resultCode=1;
				resultDesc=ShowMsg.findFail;
			}
			else
			{
				model.put("data", c);
				resultCode=0;
				resultDesc=ShowMsg.findSuc;
			}
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
	
	@RequestMapping("/complain/deal")
	@ResponseBody
	protected Object deal(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
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
		
		String comId=getString(request, "comId");
		//String comType=getString(request, "comType");
		String type=getString(request, "type");
		String comResult=getString(request, "comResult");
		String comContent=getString(request, "comContent");
		String returnMoney=getString(request, "returnMoney");
		String deductMoney=getString(request, "deductMoney");
		
		if(comId=="")
		{
			resultDesc=ShowMsg.ParFail;
			resultCode=2;
		}
		else if(comResult=="")
		{
			resultDesc=ShowMsg.comResultNull;
			resultCode=2;
		}
		else if(!comResult.matches("^[0-9]*$"))
		{
			resultDesc=ShowMsg.comResultErr;
			resultCode=2;
		}
		else
		{
			Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");// 判断小数点后一位的数字的正则表达式
		    if(!returnMoney.equals(""))
			{
				Matcher matcher=pattern.matcher(returnMoney);
				if(matcher.matches()==false)
				{
					resultDesc=ShowMsg.returnMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
		    if(!deductMoney.equals(""))
			{
				Matcher matcher=pattern.matcher(deductMoney);
				if(matcher.matches()==false)
				{
					resultDesc=ShowMsg.deductMoneyErr;
					resultCode=2;
					model.put("resultCode", resultCode);	
					model.put("resultDesc", resultDesc);
					return model;
				}
			}
		    
		    Complain c=new Complain();
		    c.setComId(comId);
		    //c.setComType(Integer.parseInt(comType));
		    c.setType(Integer.parseInt(type));
		    c.setComResult(Integer.parseInt(comResult));
		    c.setComContent(comContent);
		    if(!returnMoney.equals(""))
		    {
		    	c.setReturnMoney(Float.parseFloat(returnMoney));
		    }
		    if(!deductMoney.equals(""))
		    {
		    	c.setDeductMoney(Float.parseFloat(deductMoney));
		    }
		    c.setUpdateBy(myloginId);
		    c.setUpdateDate(DateUtil.getCurrentDateStr());
		    c.setStatus(2);
		    
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			Date date=new Date();
			String fileName=sdf.format(date);
			fileName=fileName+".xlsx";
		    
		    result=complainService.handle(request,fileName,c);
			if(result==true)
			{
				model.put("fileName","/upload/settle/user/"+fileName);	
				resultCode=0;
				resultDesc=ShowMsg.handleSuc;
			}
			else
			{
				resultCode=1;
				resultDesc=ShowMsg.handleFail;
			}
			
		}
		model.put("resultCode", resultCode);	
		model.put("resultDesc", resultDesc);
		return model;
	}	
}
