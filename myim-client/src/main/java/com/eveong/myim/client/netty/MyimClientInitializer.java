package com.eveong.myim.client.netty;

import org.springframework.stereotype.Component;

import com.eveong.myim.common.netty.protocol.MyimProtocolDecode;
import com.eveong.myim.common.netty.protocol.MyimProtocolEncode;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月22日 下午5:54:59
 * @version 1.0
 */
@Component
@Slf4j
public class MyimClientInitializer extends ChannelInitializer<SocketChannel>{


	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        log.info("正在连接中...");
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("ping", new IdleStateHandler(0, 5, 0));
        ch.pipeline().addLast("idleStateTrigger", new ClientIdleStateTrigger());
        
        pipeline.addLast(new MyimProtocolEncode());
        //解码
        pipeline.addLast(new MyimProtocolDecode());
        //客户端处理类
        pipeline.addLast(new MyimProtocolClientHandler());

    }

}
