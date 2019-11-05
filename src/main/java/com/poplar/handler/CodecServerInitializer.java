package com.poplar.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created By poplar on 2019/9/26
 */
public class CodecServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyByteToMessageDecoder());
        pipeline.addLast(new MyMessageToByteEncoder());
        pipeline.addLast(new CodecServerHandler());

    }
}
