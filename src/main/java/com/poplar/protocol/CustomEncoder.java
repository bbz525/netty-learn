package com.poplar.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created By poplar on 2019/11/5
 * 根据自定义的协议编码
 */
public class CustomEncoder extends MessageToByteEncoder<CustomProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        System.out.println("CustomEncoder encode invoked");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
