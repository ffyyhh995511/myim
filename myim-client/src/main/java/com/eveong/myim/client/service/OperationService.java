package com.eveong.myim.client.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eveong.myim.common.netty.protocol.MyimEnum;
import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.common.util.HttpClientUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OperationService {
	
	@Value("${route.host}")
	private String routeHost;
	
	/**
	 * 加入路由
	 * @param serverIp
	 * @param serverPort
	 * @throws UnsupportedEncodingException
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午5:26:31
	 */
	public void addRoute(int userId, String serverIp, int serverPort, int httpPort) throws UnsupportedEncodingException {
		String url = routeHost + "route/add"; 
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("userId", String.valueOf(userId));
		param.put("serverIp", serverIp);
		param.put("tcpServerPort", String.valueOf(serverPort));
		param.put("httpServerPort", String.valueOf(httpPort));
		HttpClientUtil.sendHttpPost(url, param);
		log.info("加入路由成功");
		log.info("你的userId={}", userId);
	}
	
	/**
	 * 
	 * 客户端任意操作都是通过这个接口发送到路由层
	 * @author:fangyunhe
	 * @time:2019年10月31日 下午2:16:52
	 */
	public void send(int sendUserId, int receiveUserId, String content) {
		String url = routeHost + "dispatch/receive";
		MyimProtocol myimProtocol = new MyimProtocol();
		myimProtocol.setContent(content);
		myimProtocol.setMyimEnum(MyimEnum.TEXT);
		myimProtocol.setSendUserId(sendUserId);
		myimProtocol.setReceiveUserId(receiveUserId);
		HttpClientUtil.sendHttp(url, JSON.toJSONString(myimProtocol), HttpClientUtil.JSON_CONTENT_TYPE);
	}
}
