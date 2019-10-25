package com.eveong.myim.server;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.eveong.myim.server.zk.CuratorClient;

/**
 * 
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:46:12
 * @version 1.0
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

	@Resource
	CuratorClient curatorClient;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {		
		curatorClient.registerService();
		
		curatorClient.getRegisterInstance();
	}
}
