package com.poplar.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created By poplar on 2019/9/22
 * netty hello world 服务端代码
 */
public class HelloWorldTestServer {

    public static void main(String[] args) {
        //创建两个线程循环工作组,一个用于接收请求,一个用于处理请求
        /**
         * public NioEventLoopGroup() {
         *         this(0);
         *     }
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new HelloWorldChannelInitializer());
            /*前面的都是再做一些准备工作，调用bind才真正的创建实列
            * 通过调用同步方法sync()方法确保整个初始化和注册真正的完成
            * */
            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //表示优雅关闭，表示关闭Channel和清楚服务端的资源
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
