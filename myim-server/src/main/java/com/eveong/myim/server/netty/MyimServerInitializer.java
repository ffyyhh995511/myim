package com.eveong.myim.server.netty;

import com.eveong.myim.common.netty.protocol.MyimProtocolDecode;
import com.eveong.myim.common.netty.protocol.MyimProtocolEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:54:59
 * @version 1.0
 */
@Component
public class MyimServerInitializer extends ChannelInitializer<SocketChannel>{
	
	@Resource
	MyimProtocolServerHandler myimProtocolServerHandler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(10, 0, 0));
        ch.pipeline().addLast("idleStateTrigger", new ServerIdleStateTrigger());
		ChannelPipeline pipeline = ch.pipeline();
		//解码
		pipeline.addLast(new MyimProtocolDecode());
		//编码
		pipeline.addLast(new MyimProtocolEncode());
		pipeline.addLast(myimProtocolServerHandler);
	}

}
