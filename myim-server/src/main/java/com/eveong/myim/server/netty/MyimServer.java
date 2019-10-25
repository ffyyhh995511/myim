package com.eveong.myim.server.netty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:50:36
 * @version 1.0
 */
@Service
@Slf4j
public class MyimServer {
	
	@Value("${netty.server.port}")
	private int nettyServerPort;
	
	@Resource
	private MyimServerInitializer myimServerInitializer;

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	/**
	 * 
	 * @throws InterruptedException
	 * @author:fangyunhe
	 * @time:2019年10月22日 下午5:52:50
	 */
	@PostConstruct
	public void init() throws InterruptedException {
		log.info("netty nio server init");
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(myimServerInitializer);
		// 服务器绑定端口监听
		ChannelFuture channelFuture = serverBootstrap.bind(nettyServerPort).sync();
		if(channelFuture.isSuccess()) {
			log.info("netty nio server start");
		}
	}
	
	/**
	 * 
	 * @author:fangyunhe
	 * @time:2019年10月22日 下午5:53:02
	 */
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		log.info("netty nio server stop... ");
    }
}
