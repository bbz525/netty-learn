package com.poplar.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created By poplar
 * 客服端 Initializer 作用同服务端
 */
public class CustomClientInitializer extends ChannelInitializer<SocketChannel> {

    //此方法每当有一个客服端连接时就会被调用一次，也就会重新创建一个CustomClientInitializer
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new CustomDecoder());
        pipeline.addLast(new CustomEncoder());
        pipeline.addLast(new CustomClientHandler());

    }
}
