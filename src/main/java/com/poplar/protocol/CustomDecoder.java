package com.poplar.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created By poplar on 2019/11/5
 * 根据自定义的协议解码
 */
public class CustomDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        System.out.println("CustomDecoder decode invoked ");
        int length = buf.readInt();
        byte[] content = new byte[length];
        buf.readBytes(content);
        //封装协议
        CustomProtocol protocol = new CustomProtocol(length, content);
        out.add(protocol);
    }
}
