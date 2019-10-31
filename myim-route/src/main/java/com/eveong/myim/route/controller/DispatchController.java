package com.eveong.myim.route.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.route.service.DispatchService;
import com.eveong.myim.route.vo.RouteAddAndGetVO;

@RestController
@RequestMapping("dispatch")
public class DispatchController {
	
	@Resource
	DispatchService dispatchService;
	
	/**
	 * 调度客户端的任意操作
	 * @param myimProtocol
	 * @return
	 * @author:fangyunhe
	 * @time:2019年10月31日 下午2:22:46
	 */
	@PostMapping(value = "receive")
	public String receive(@RequestBody MyimProtocol myimProtocol){
		dispatchService.receive(myimProtocol);
		return "发送成功";
	}
}
