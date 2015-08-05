package com.dasher.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.*;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class IGtPushUtil {
	private static String appId = "4dxBQRR2psAs0rZu4umjF3";
	private static String appkey = "USasTkD4W86GTaZ8L886K2";
	private static String master = "NaQBVsNDXp85qr77AiDvU5";
	static String CID = "请输入CID";
	static String Alias = "请输入Alias";
	static String host = "http://sdk.open.api.getui.net/serviceex";
	
	private static IGtPush push =null;
	
//	static{
//		push = new IGtPush(appkey, master);
//		
//        try {
//			push.connect();
//			
//			System.out.println(push.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static IPushResult PushtoSingleDeal(String CID,int index){
		
		String title="";
		String content="";
		if(ShowMsg.userApplyIndex==index){
			title=ShowMsg.userApplyTitle;
			content="1#"+ShowMsg.userApplyIndex+"#"+ShowMsg.userApplyTitle+"#"+ShowMsg.userApplyContent;
		}else if(ShowMsg.menuReceiveIndex==index){
			title=ShowMsg.menuReceiveTitle;
			content="1#"+ShowMsg.menuReceiveIndex+"#"+ShowMsg.menuReceiveTitle+"#"+ShowMsg.menuReceiveContent;
		}else if(ShowMsg.menuCompleteIndex==index){
			title=ShowMsg.menuCompleteTitle;
			content="2#"+ShowMsg.menuCompleteIndex+"#"+ShowMsg.menuCompleteTitle+"#"+ShowMsg.menuCompleteContent;
		}else if(ShowMsg.menuOverTimeIndex==index){
			title=ShowMsg.menuOverTimeTitle;
			content="1#"+ShowMsg.menuOverTimeIndex+"#"+ShowMsg.menuOverTimeTitle+"#"+ShowMsg.menuOverTimeContent;
		}else if(ShowMsg.menuComplainIndex==index){
			title=ShowMsg.menuComplainTitle;
			content="2#"+ShowMsg.menuComplainIndex+"#"+ShowMsg.menuComplainTitle+"#"+ShowMsg.menuComplainContent;
		}else if(ShowMsg.menuCancleDealIndex==index){
			title=ShowMsg.menuCancleDealTitle;
			content="1#"+ShowMsg.menuCancleDealIndex+"#"+ShowMsg.menuCancleDealTitle+"#"+ShowMsg.menuCancleDealContent;
		}else if(ShowMsg.menuOverTimeDealIndex==index){
			title=ShowMsg.menuOverTimeDealTitle;
			content="1#"+ShowMsg.menuOverTimeDealIndex+"#"+ShowMsg.menuOverTimeDealTitle+"#"+ShowMsg.menuOverTimeDealContent;
		}else if(ShowMsg.menuComplainDealIndex==index){
			title=ShowMsg.menuComplainDealTitle;
			content="1#"+ShowMsg.menuComplainDealIndex+"#"+ShowMsg.menuComplainDealTitle+"#"+ShowMsg.menuComplainDealContent;
		}else if(ShowMsg.serverSettleIndex==index){
			title=ShowMsg.serverSettleTitle;
			content="2#"+ShowMsg.serverSettleIndex+"#"+ShowMsg.serverSettleTitle+"#"+ShowMsg.serverSettleContent;
		}
		
		return PushtoSingle( CID, title, content);
	}
    public static IPushResult PushtoSingleDeal2(String CID,int index){
		
		String title="";
		String content="";
		
		title=ShowMsg.menuComplainDealTitle;
		content="2#"+ShowMsg.menuComplainDealIndex+"#"+ShowMsg.menuComplainDealTitle+"#"+ShowMsg.menuComplainDealContent;
		
		return PushtoSingle( CID, title, content);
	}
	//对指定用户进行信息推送
	public static IPushResult PushtoSingle(String CID,String title,String content){
		push = new IGtPush(appkey, master);
		
        try {
			push.connect();
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		
		//LinkTemplate template = linkTemplateDemo(title,content);
		//NotificationTemplate template = notificationTemplateDemo(title,content,content);
		TransmissionTemplate template = transmissionTemplateDemo(title,content);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        //用户别名推送，cid和用户别名只能2者选其一
        //String alias = "个";
        //target.setAlias(alias);
        IPushResult ret = push.pushMessageToSingle(message, target);
        
        return ret;
	}
	//对用户列表进行推送
	public static IPushResult PushtoList(String[] CIDS,String title,String content,String transmissionContent){
		
		//通知透传模板
        NotificationTemplate template = notificationTemplateDemo(title,content,transmissionContent);
   
        ListMessage message = new ListMessage();
        message.setData(template);
        //设置消息离线，并设置离线时间
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24*1000*3600);
   
        //配置推送目标
        List<Target> targets = new ArrayList<Target>();
        
        for(int i=0;i<CIDS.length;i++){
        	Target target1 = new Target();
            target1.setAppId(appId);
            //用户别名推送，cid和用户别名2者只能选其一
            //String alias1 = "个";
            //target1.setAlias(alias1);
            target1.setClientId(CIDS[i]);
            targets.add(target1);
        }
         
        //获取taskID
        String taskId = push.getContentId(message);
        //使用taskID对目标进行推送
        IPushResult ret = push.pushMessageToList(taskId, targets);
		return ret;
	}
	private static LinkTemplate linkTemplateDemo(String title,String content) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("");
        // 配置通知栏网络图标，填写图标URL地址
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 设置打开的网址地址
        template.setUrl("");
        return template;
    }
	private static NotificationTemplate notificationTemplateDemo(String title,String content,String transmissionContent) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("http://58.211.23.95:8080/app/push.png");
        // 配置通知栏网络图标
        template.setLogoUrl("http://58.211.23.95:8080/app/push.png");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        //"请输入您要透传的内容"
        template.setTransmissionContent(transmissionContent);
        return template;
    }
	//对用户进行透传
	private static TransmissionTemplate transmissionTemplateDemo(String title,String content){
		TransmissionTemplate template=new TransmissionTemplate();
		// 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appkey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        //"请输入您要透传的内容"
        template.setTransmissionContent(content);
        
        return template;
	}
	
}
