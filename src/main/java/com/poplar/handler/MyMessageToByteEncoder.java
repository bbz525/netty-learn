package com.poplar.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created By poplar on 2019/11/4
 * 自定义编码器
 */
public class MyMessageToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("encode invoked");
        System.out.println(msg);
        //此处需要注意不要调用成readLong()
        out.writeLong(msg);
    }
}
