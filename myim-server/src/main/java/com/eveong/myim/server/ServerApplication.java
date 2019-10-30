package com.eveong.myim.server;

import com.eveong.myim.server.service.ZKService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

/**
 * 
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:46:12
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.eveong.myim.common","com.eveong.myim.server"})
public class ServerApplication implements CommandLineRunner{

	@Resource
	ZKService zkService;
	
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		zkService.register();
	}
}
