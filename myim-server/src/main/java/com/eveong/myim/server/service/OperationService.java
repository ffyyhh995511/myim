package com.eveong.myim.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.server.common.CacheEnum;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OperationService {
	
	String loginKey = CacheEnum.Login.getKey();
	
	/**
	 * redisTemplate 存储channel对下，会报json递归错误，修改为本地map
	 */
	@Resource
	RedisTemplate<String,  Channel> redisTemplate;
	
	private Map<Integer,Channel> channelMap = new HashMap<>();

	
	/**
	 * 服务端记录每个userId对应的channel
	 * @param userId
	 * @param channel
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:22:30
	 */
	public void login(Integer userId, Channel channel) {
		//redisTemplate.opsForHash().put(loginKey, userId.toString(), channel);
		channelMap.put(userId, channel);
		log.info(userId + "登录成功");
	}
	
	/**
	 * 
	 * @param userId
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:23:02
	 */
	public void logout(Integer userId) {
		//redisTemplate.opsForHash().delete(loginKey, userId.toString());
		channelMap.remove(userId);
	}

	/**
	 * 获取channle并写入
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:27:32
	 */
	public void channelWrite(MyimProtocol msg) {
		//Channel channel = (Channel) redisTemplate.opsForHash().get(loginKey, msg.getReceiveUserId().toString());
		Channel channel = channelMap.get(msg.getReceiveUserId());
		channel.writeAndFlush(msg);
	}
}
