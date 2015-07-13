package com.dasher.util;

import java.util.*;
import com.dasher.chat.Constants;
import com.dasher.chat.httpclient.apidemo.EasemobIMUsers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EasemobUtil {
	//环信用户注册
	public static Map<String,String> createNewIMUserSingle(String username){
		Map<String,String> map=new HashMap<String, String>();
		//注册用户的环信账号
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",username);
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
        /*正确
        {"action":"post",
        "application":"86239b00-237e-11e5-93dc-2db313e5772c",
        "path":"/users",
        "uri":"http://a1.easemob.com/dashertest/dasher/users",
        "entities":[
        	{"uuid":"a943314a-2900-11e5-98d6-03e5d80ded45",
        	"type":"user",
        	"created":1436751852116,
        	"modified":1436751852116,
        	"username":"hjbtest",
        	"activated":true}],
        "timestamp":1436751852111,
        "duration":35,
        "organization":"dashertest",
        "applicationName":"dasher",
        "statusCode":200}
        */
       /*错误
        {"error":"duplicate_unique_property_exists",
        "timestamp":1436752995070,
        "duration":0,
        "exception":"org.apache.usergrid.persistence.exceptions.DuplicateUniquePropertyExistsException",
        "error_description":"Application 86239b00-237e-11e5-93dc-2db313e5772cEntity user requires that property named username be unique, value of hjbtest1 exists",
        "statusCode":400}
        */
        if(createNewIMUserSingleNode==null){
        	map.put("statusCode", "-1");
        	return map;
        }
        List<String> statusCodeList=createNewIMUserSingleNode.findValuesAsText("statusCode");
        if(statusCodeList.size()<=0){
        	map.put("statusCode", "-1");
        	return map;
        }
        String statusCode=statusCodeList.get(0);
        map.put("statusCode", statusCode);
        if("200".equals(statusCode)){
        	JsonNode jsonObject=createNewIMUserSingleNode.get("entities");
        	for (JsonNode jsonNode : jsonObject) {
            	List<String> uuid=jsonNode.findValuesAsText("uuid");
            	//保存用户环信账号
            	map.put("uuid", uuid.get(0));
    		}
        }
		return map;
	}
	//环信用户登录
	public static Map<String,String> imUserLogin(String userName){
		Map<String,String> map=new HashMap<String, String>();
		//环信用户登录
		ObjectNode imUserLoginNode = EasemobIMUsers.imUserLogin(userName, Constants.DEFAULT_PASSWORD);
		/*正确
        {"access_token":"YWMtNrZQ-ikIEeW6cy2yEATD7wAAAU-6RIFieKQWItPkvkMq-vdI5H2n9z-_NRg",
        "expires_in":5184000,
        "user":{
        	"uuid":"ccec464a-2905-11e5-9289-2509cc4ef0e0",
        	"type":"user",
        	"created":1436754059428,
        	"modified":1436754059428,
        	"username":"hjbtest3",
        	"activated":true},
        "statusCode":200}
        */
       /*错误
        {"error_description":"invalid username or password",
        "error":"invalid_grant",
        "statusCode":404}
        */
		if(imUserLoginNode==null){
        	map.put("statusCode", "-1");
        	return map;
        }
		List<String> statusCodeList=imUserLoginNode.findValuesAsText("statusCode");
        if(statusCodeList.size()<=0){
        	map.put("statusCode", "-1");
        	return map;
        }
        String statusCode=statusCodeList.get(0);
        map.put("statusCode", statusCode);
        if("200".equals(statusCode)){
        	JsonNode jsonObject=imUserLoginNode.get("user");
        	for (JsonNode jsonNode : jsonObject) {
            	List<String> uuid=jsonNode.findValuesAsText("uuid");
            	//保存用户环信账号
            	map.put("uuid", uuid.get(0));
    		}
        }
		return map;
	}
	
}
