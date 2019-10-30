package com.eveong.myim.client.service;

import java.io.BufferedInputStream;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsoleService {

	/**
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午5:01:28
	 */
	public void readFromConsole() {
		Scanner sc = new Scanner(new BufferedInputStream(System.in));
		log.info("########### welcome to myim ###########");
		while(sc.hasNext()){
			String lineString = sc.nextLine();
			log.info(lineString);
		}
	}
}
