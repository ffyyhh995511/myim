package com.eveong.myim.common.netty.protocol;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyimProtocolDecode extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableBytes = in.readableBytes();
        byte[] data = new byte[readableBytes];
		in.readBytes(data);
		 //将byte数据转化为我们需要的对象
		Object obj = JSON.parseObject(data, MyimProtocol.class);
        out.add(obj);
	}

}
