package com.eveong.myim.client.service;

import java.io.BufferedInputStream;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.eveong.myim.client.common.TemporaryCommon;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsoleService {
	
	private int chatFormat = 2;
	
	@Resource
	OperationService operationService;

	/**
	 * 控制台配置一定的格式要求
	 * userId:10;toUserId;20;你好
	 * 
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午5:01:28
	 */
	public void readFromConsole() {
		Scanner sc = new Scanner(new BufferedInputStream(System.in));
		log.info("聊天格式如下");
		log.info("toUserId:20; 你好");
		log.info("########### welcome to myim ###########");
		while(sc.hasNext()){
			String lineString = sc.nextLine();
			if(StringUtils.isBlank(lineString)) {
				log.info("聊天内容不能空");
			}
			String[] split = lineString.split(";");
			if(split.length != chatFormat) {
				log.info("发送规范错误");
			}
			int sendUserId = TemporaryCommon.userId;
			int receiveUserId = Integer.parseInt(split[0].split(":")[1]);
			String content = split[1];
			operationService.send(sendUserId, receiveUserId, content);
		}
	}
}
