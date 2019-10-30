package com.eveong.myim.server.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.server.common.CacheEnum;

import io.netty.channel.Channel;

@Service
public class OperationService {
	
	String loginKey = CacheEnum.Login.getKey();
	
	@Resource
	RedisTemplate redisTemplate;

	
	/**
	 * 服务端记录每个userId对应的channel
	 * @param userId
	 * @param channel
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:22:30
	 */
	public void login(Integer userId, Channel channel) {
		redisTemplate.opsForHash().put(loginKey, userId, channel);
	}
	
	/**
	 * 
	 * @param userId
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:23:02
	 */
	public void logout(Integer userId) {
		redisTemplate.opsForHash().delete(loginKey, userId);
	}

	/**
	 * 
	 * @param msg
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午6:27:32
	 */
	public void text(MyimProtocol msg) {
		Channel channel = (Channel) redisTemplate.opsForHash().get(loginKey, msg.getReceiveUserId());
		channel.writeAndFlush(msg);
	}
}
