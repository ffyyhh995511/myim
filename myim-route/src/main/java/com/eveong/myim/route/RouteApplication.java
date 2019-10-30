package com.eveong.myim.route;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月29日 下午4:23:10
 * @version 1.0
 */
@SpringBootApplication
public class RouteApplication implements CommandLineRunner{
	
	@Resource
	RedisTemplate redisTemplate;
	
    public static void main( String[] args )
    {
    	SpringApplication.run(RouteApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
	}
}
