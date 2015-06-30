package com.dasher.servlet;

import java.util.*;
import javax.servlet.*;

import com.dasher.controller.*;
/**
 * 系统启动时的监听类 初始化系统数据
 * 
 * @author jhoneder
 * 
 */
public class InitListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		 try {
			goTimmer();
		 } catch (Exception e) {
		    System.out.println("失败:" + e.getMessage());
		 }
	}

	private void goTimmer() {
		  Timer timmerTask = new Timer();
		  Calendar calEnviron = Calendar.getInstance();
		  // 每天的05:00.am开始执行
		  calEnviron.set(Calendar.HOUR_OF_DAY, 5);
		  calEnviron.set(Calendar.MINUTE, 00);
		  // date为制定时间
		  Date dateSetter = new Date();
		  dateSetter = calEnviron.getTime();
		  // nowDate为当前时间
		  Date nowDateSetter = new Date();
		  // 所得时间差为，距现在待触发时间的间隔
		  long intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
		  System.out.println("intervalEnviron:"+intervalEnviron);
		  if (intervalEnviron < 0) {
		      calEnviron.add(Calendar.DAY_OF_MONTH, 1);
		      dateSetter = calEnviron.getTime();
		      intervalEnviron = dateSetter.getTime() - nowDateSetter.getTime();
		      System.out.println("intervalEnviron:"+intervalEnviron);
		  }
		  //每分钟执行一次
		  timmerTask.schedule(new MenuTimerController(timmerTask), 1*1000*10, 1 * 1000 * 60);
	}

}
