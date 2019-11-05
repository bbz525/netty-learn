package com.poplar.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created By poplar on 2019/11/5
 * 客服端处理器
 */
public class CustomClientHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "send from client";
            int length = message.getBytes(UTF_8).length;
            byte[] content = message.getBytes(UTF_8);
            //封装协议
            CustomProtocol protocol = new CustomProtocol(length, content);
            ctx.channel().writeAndFlush(protocol);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("客服端收到来自服务端的消息: ");
        System.out.println("消息长度： " + length);
        System.out.println("消息内容： " + new String(content, UTF_8));
        System.out.println("客服端收到来自服务端的消息数: " + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
