package com.eveong.myim.client.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eveong.myim.client.netty.MyimClient;

@RestController
@RequestMapping("/send")
public class SendMsgController {
	
	@Resource
	MyimClient myimClient;
	
	@GetMapping(value = "msg")
	public String sendMsg(){
		myimClient.send();
		return "ok";
	}
}
