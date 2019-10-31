package com.eveong.myim.route.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RouteAddAndGetVO implements Serializable{
	private static final long serialVersionUID = 1L;

	Integer userId;
	
	String serverIp;
	
	Integer tcpServerPort;
	
	Integer httpServerPort;
}
