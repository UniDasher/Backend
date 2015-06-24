package com.dasher.util;

import com.dasher.chat.Constants;
import com.dasher.chat.HTTPMethod;
import com.dasher.chat.Roles;
import com.dasher.chat.httpclient.utils.HTTPClientUtils;
import com.dasher.chat.httpclient.vo.ClientSecretCredential;
import com.dasher.chat.httpclient.vo.Credential;
import com.dasher.chat.httpclient.vo.EndPoints;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EasemobUtil {
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);
	// 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
	/**
     * 注册IM用户[单个]
     */
	public static ObjectNode createNewIMUserSingle(String username,String password){
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",username);
        datanode.put("password", password);
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
        
		return createNewIMUserSingleNode;
	}
	/**
	 * 注册IM用户[单个]
	 * 给指定Constants.APPKEY创建一个新的用户
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {
		ObjectNode objectNode = factory.objectNode();
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			objectNode.put("message", "Bad format of Constants.APPKEY");
			return objectNode;
		}
		objectNode.removeAll();
		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			objectNode.put("message", "Property that named username must be provided .");
			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			objectNode.put("message", "Property that named password must be provided .");
			return objectNode;
		}
		try {
		    objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode,
					HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectNode;
	}

}
