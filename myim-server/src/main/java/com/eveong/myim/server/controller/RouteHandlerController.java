package com.eveong.myim.server.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.server.service.OperationService;
/**
 * 
 * 服务端处理route的转发请求
 * @author:fangyunhe
 * @time:2019年10月31日 下午2:35:33
 * @version 1.0
 */
@RestController
@RequestMapping("routeHandler")
public class RouteHandlerController {
	
	@Resource
	OperationService operationService;
	
	@PostMapping
	public String handle(@RequestBody MyimProtocol myimProtocol) {
		operationService.channelWrite(myimProtocol);
		return "ok";
	}
}
