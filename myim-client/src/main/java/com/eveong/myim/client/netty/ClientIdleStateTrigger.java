package com.eveong.myim.client.netty;

import com.eveong.myim.common.netty.protocol.MyimEnum;
import com.eveong.myim.common.netty.protocol.MyimProtocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
            	//空闲时间给服务器发送心跳信息
                ctx.channel().writeAndFlush(getPingData());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    
    
    private MyimProtocol getPingData() {
    	MyimProtocol myimProtocol = new MyimProtocol();
    	myimProtocol.setMyimEnum(MyimEnum.HEART_BEAT);
    	return myimProtocol;
    }
}