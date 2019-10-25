package com.eveong.myim.server.zk.instance;

import lombok.Data;

@Data
public class Payload {
	private String ip;

	private Integer nettyPort;

	private Integer httpPort;

}