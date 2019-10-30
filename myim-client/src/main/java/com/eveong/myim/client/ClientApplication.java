package com.eveong.myim.client;

import com.eveong.myim.client.netty.MyimClient;
import com.eveong.myim.client.service.ConsoleService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月29日 下午4:23:10
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.eveong.myim.common","com.eveong.myim.client"})
public class ClientApplication implements CommandLineRunner {
	
	@Resource
	ConsoleService consoleService;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		consoleService.readFromConsole();
	}
}
