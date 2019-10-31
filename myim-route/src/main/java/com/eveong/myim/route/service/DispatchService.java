package com.eveong.myim.route.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.common.util.HttpClientUtil;
import com.eveong.myim.route.vo.RouteAddAndGetVO;

@Service
public class DispatchService {
	@Value("${server.host}")
	private String serverHost;
	
	@Resource
	RouteService routeService;
	
	/**
	 * 
	 * @param myimProtocol
	 * @author:fangyunhe
	 * @time:2019年10月31日 下午2:53:52
	 */
	public void receive(MyimProtocol myimProtocol) {
		int receiveUserId = myimProtocol.getReceiveUserId();
		RouteAddAndGetVO routeAddAndGetVO = routeService.get(receiveUserId);
		//转发到server
		serverHost = String.format(serverHost, routeAddAndGetVO.getServerIp(), routeAddAndGetVO.getHttpServerPort());
		String url = serverHost + "/routeHandler";
		HttpClientUtil.sendHttp(url, JSON.toJSONString(myimProtocol), HttpClientUtil.JSON_CONTENT_TYPE);
	}

}
