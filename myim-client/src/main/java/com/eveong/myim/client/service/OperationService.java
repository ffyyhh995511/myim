package com.eveong.myim.client.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	public void addRoute(String serverIp, Integer serverPort) throws UnsupportedEncodingException {
		String url = routeHost + "route/add";
		int userId = (int) (Math.random() * 100); 
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("userId", String.valueOf(userId));
		param.put("serverIp", serverIp);
		param.put("serverPort", String.valueOf(serverPort));
		HttpClientUtil.sendHttpPost(url, param);
		log.info("加入路由成功");
	}
}
