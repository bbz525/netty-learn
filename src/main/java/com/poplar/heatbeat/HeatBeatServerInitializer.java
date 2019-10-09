package com.poplar.heatbeat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created By poplar on 2019/10/9
 */
public class HeatBeatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //读写空闲监听
        pipeline.addLast(new IdleStateHandler(5, 5, 2, TimeUnit.SECONDS));
        pipeline.addLast(new HeatBeatServerHandler());
    }
}
