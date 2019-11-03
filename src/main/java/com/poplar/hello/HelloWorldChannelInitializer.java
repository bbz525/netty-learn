package com.poplar.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created By poplar on 2019/9/23
 * 初始化器
 * 当方法返回后HelloWorldChannelInitializer就会被移除，此类的作用只是把一堆Handler封装了一下，没什么实质的作用
 */
public class HelloWorldChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * This method will be called once the Channel was registered.
     * After the method returns this instance will be removed from the ChannelPipeline of the Channel.
     * 此方法将在注册通道后调用。方法返回后，将从通道的 Channelpipeline 中删除此实例。
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("helloWorldTestHandler", new HelloWorldTestHandler());
    }
}
