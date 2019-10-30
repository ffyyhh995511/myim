package com.eveong.myim.route.service;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.eveong.myim.route.common.CacheEnum;
import com.eveong.myim.route.vo.RouteAddAndGetVO;

@Component
public class RouteService {
	
	String routeWithUserIdkey = CacheEnum.RouteWithUserId.getKey();
	
	@Resource
	RedisTemplate redisTemplate;

	/**
	 * 
	 * @param vo
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午4:25:58
	 */
	public void add(RouteAddAndGetVO vo) {
		String value = JSON.toJSONString(vo);
		String field = String.valueOf(vo.getUserId());
		redisTemplate.opsForHash().put(routeWithUserIdkey, field, vo);
	}
	
	/**
	 * 
	 * @param userId
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午4:26:47
	 */
	public void del(Integer userId) {
		String field = String.valueOf(userId);
		redisTemplate.opsForHash().delete(routeWithUserIdkey, field);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午4:38:41
	 */
	public RouteAddAndGetVO get(Integer userId) {
		String field = String.valueOf(userId);
		RouteAddAndGetVO value = (RouteAddAndGetVO) redisTemplate.opsForHash().get(routeWithUserIdkey, field);
		return value;
	}
}
