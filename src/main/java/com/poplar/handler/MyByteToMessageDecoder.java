package com.poplar.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created By poplar on 2019/11/4
 * 自定义解码器
 */
public class MyByteToMessageDecoder extends ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        System.out.println("decode invoked");
        System.out.println(msg.readableBytes());
        out.add(msg.readLong());

    }

}
