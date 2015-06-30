package com.dasher.servlet;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.dasher.service.*;

public class MenuTimerController extends TimerTask {
	@Autowired
	private MenuService menuService;
	@Autowired
	private MarketMenuService marketMenuService;
	
	Timer timer = new Timer();
	public MenuTimerController(Timer timer) {
	    this.timer = timer;
	}
 /*
  * 被调用具体的方法
  * <!-- 定时器配置 -->
	<bean id="MenuTimerController"
		class="com.dasher.servlet.MenuTimerController">
		<property name="marketMenuService" ref="marketMenuService"></property>
		<property name="menuService" ref="menuService"></property>
	</bean>
  */
	@Override
	public void run() {
		System.out.println("run:正式执行");
		//获取超时的订单列表
		//餐厅订单超时处理
		menuService.getListOverTime();
		//超市订单超时处理
		marketMenuService.getListOverTime();
	}
}
