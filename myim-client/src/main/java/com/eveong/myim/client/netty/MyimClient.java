package com.eveong.myim.client.netty;

import com.eveong.myim.common.zk.CuratorClient;
import com.eveong.myim.common.zk.instance.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.eveong.myim.client.common.TemporaryCommon;
import com.eveong.myim.client.service.OperationService;
import com.eveong.myim.common.netty.protocol.MyimEnum;
import com.eveong.myim.common.netty.protocol.MyimProtocol;
import com.eveong.myim.common.netty.protocol.MyimProtocolDecode;
import com.eveong.myim.common.netty.protocol.MyimProtocolEncode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class MyimClient {

    @Resource
    CuratorClient curatorClient;
    
    @Resource
    OperationService operationService;

    private Channel channel;
    
    public Channel getChannel() {
    	return channel;
    }
	
    @PostConstruct
	public void start() throws Exception {
        Payload registerInstance = curatorClient.getRegisterInstance();
        if(registerInstance == null){
            log.error("服务器没有注册实例");
        }
        String ip = registerInstance.getIp();
        int nettyPort = registerInstance.getNettyPort();
        int httpPort = registerInstance.getHttpPort();

        final EventLoopGroup group = new NioEventLoopGroup();
 
        Bootstrap b = new Bootstrap();
        // 使用NioSocketChannel来作为连接用的channel类
        b.group(group).channel(NioSocketChannel.class)
            .handler(new MyimClientInitializer());
        //发起异步连接请求，绑定连接端口和host信息
        final ChannelFuture future = b.connect(ip, nettyPort).sync();
 
        future.addListener(new ChannelFutureListener() {
 
            @Override
            public void operationComplete(ChannelFuture arg0) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");
 
                } else {
                    System.out.println("连接服务器失败");
                    future.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            }
        });
 
        this.channel = future.channel();
        int userId = (int) (Math.random() * 100);
        TemporaryCommon.userId = userId;
        operationService.addRoute(userId, ip, nettyPort, httpPort);
        
        login(userId);
    }
    
    /**
     * 
     * 
     * 测试发送
     * @author:fangyunhe
     * @time:2019年10月31日 下午4:21:33
     */
	public void testSend() {
		MyimProtocol myimProtocol = new MyimProtocol();
		myimProtocol.setContent("hello 你好，@cheng🌝 😭💋");
		myimProtocol.setMyimEnum(MyimEnum.TEXT);
		channel.writeAndFlush(myimProtocol);
	}
	
	
	private void login(int userId) {
		MyimProtocol myimProtocol = new MyimProtocol();
		myimProtocol.setMyimEnum(MyimEnum.LOGIN);
		myimProtocol.setSendUserId(userId);
		channel.writeAndFlush(myimProtocol);
	}
}
