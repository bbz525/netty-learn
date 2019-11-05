package com.poplar.protocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created By poplar
 * 服务端 Initializer，主要用于批量注册处理器，之后会被remove掉
 */
public class CustomServerInitializer extends ChannelInitializer<SocketChannel> {

    //此方法每当有一个客服端连接时就会被调用一次，也就会重新创建一个CustomClientInitializer
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new CustomDecoder());
        pipeline.addLast(new CustomEncoder());
        pipeline.addLast(new CustomServerHandler());

    }
}
