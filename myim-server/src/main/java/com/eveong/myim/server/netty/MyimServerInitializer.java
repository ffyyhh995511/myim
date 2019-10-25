package com.eveong.myim.server.netty;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:54:59
 * @version 1.0
 */
@Component
public class MyimServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
	}

}
