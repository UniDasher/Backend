package com.dasher.common.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

public class MyContextLoader extends ContextLoaderListener {

	private static final Logger log = Logger.getLogger(MyContextLoader.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext servletContext = event.getServletContext();
		MyApplicationContext.getInstance().init(servletContext);
		log.info("^^^^^^^^^^^context inited^^^^^^^^^^");
//		System.out.println(">>>>>>>>>>>>>>>Init Spring Bean............");
//		ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");
//		System.out.println(">>>>>>>>>>>>>>>Init Spring Bean Completed!");
	}

	@Override
	public ContextLoader createContextLoader() {
		log.info("returning ClipdiyContextLoader");
		return new MyContextLoader();
	}
}
