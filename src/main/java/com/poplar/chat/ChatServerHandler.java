package com.poplar.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created By poplar on 2019/9/26
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //遍历所有的客服端

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("【客户】" + channel.remoteAddress() + " 发送消息: " + msg + "\n");
            } else {
                ch.writeAndFlush(" 【自己】发送消息: " + msg + "\n");
            }
        });
    }

    //当有新的客服端加入的时候调用
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //服务器通知所有客服端xx加入
        channelGroup.writeAndFlush("【服务器】：" + channel.remoteAddress() + "加入\n");
        //记录下当前链接上服务器的客服端
        channelGroup.add(channel);

    }

    //当有客服端离开的时候调用
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //服务器通知所有客服端xx离开
        channelGroup.writeAndFlush("【服务器】：" + channel.remoteAddress() + "离开\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //服务器通知所有客服端xx离开
        System.out.println(channel.remoteAddress() + "上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //服务器通知所有客服端xx离开
        System.out.println(channel.remoteAddress() + "离线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
