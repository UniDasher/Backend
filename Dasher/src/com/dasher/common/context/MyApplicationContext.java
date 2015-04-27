package com.dasher.common.context;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*
 *   @copyright (c) Qeeka 2005 
 * @author YinChunhui    Mar 25, 2012 
 */

public class MyApplicationContext {

	public ApplicationContext context = null;
	private static MyApplicationContext instance = null;
	
	private MyApplicationContext(){
		
	}
	public synchronized static MyApplicationContext getInstance(){
		if(instance == null){
			instance = new MyApplicationContext();
		}
		return instance;
	}
	
	public void init(ServletContext servletContext){
		context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
	
	public Object getBean(String beanID){
		return context.getBean(beanID);
	}
}
