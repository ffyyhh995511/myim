package com.eveong.myim.server.netty;

import com.alibaba.fastjson.JSON;
import com.eveong.myim.common.netty.protocol.MyimEnum;
import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.server.service.OperationService;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
//每一次请求都是一个新的handler
@Scope("prototype")
//特别注意这个注解@Sharable，默认的4版本不能自动导入匹配的包，需要手动加入
@Sharable
@Slf4j
public class MyimProtocolServerHandler extends SimpleChannelInboundHandler<MyimProtocol> {
	
	@Resource
	OperationService operationService;
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyimProtocol msg) throws Exception {
		log.info("来自客户端消息：" + JSON.toJSONString(msg));
		MyimProtocol myimProtocol = new MyimProtocol();
		myimProtocol.setMsg("服务端😭💋");
		myimProtocol.setMyimEnum(MyimEnum.TEXT);
		ctx.channel().writeAndFlush(myimProtocol);
		
		
		if(msg.getMyimEnum() == MyimEnum.LOGIN) {
			operationService.login(msg.getSendUserId(), ctx.channel());
		}
		if(msg.getMyimEnum() == MyimEnum.LOGOUT) {
			operationService.logout(msg.getSendUserId());
		}
		if(msg.getMyimEnum() == MyimEnum.TEXT) {
			operationService.text(msg);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("来自客户端消息：channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("来自客户端消息：channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.info("来自客户端消息：channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		log.info("来自客户端消息：userEventTriggered");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.info("来自客户端消息：exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}
	
	
}