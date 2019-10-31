package com.eveong.myim.client.netty;

import com.eveong.myim.common.netty.protocol.MyimProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyimProtocolClientHandler extends SimpleChannelInboundHandler<MyimProtocol>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyimProtocol msg) throws Exception {
		log.info(msg.getSendUserId() + "对你说:");
		log.info(msg.getContent());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelActive...");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("channelInactive...");
		super.channelInactive(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		log.info("userEventTriggered...");
		super.userEventTriggered(ctx, evt);
	}

}
