package com.eveong.myim.common.netty.protocol;

import lombok.Data;

@Data
public class MyimProtocol {
	public String msg;
	
	public Integer sendUserId;
	
	public Integer receiveUserId;
	
	public MyimEnum myimEnum;
}
