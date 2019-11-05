package com.poplar.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created By poplar on 2019/11/5
 * 服务端处理器
 */
public class CustomServerHandler extends SimpleChannelInboundHandler<CustomProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端收到的内容： ");
        System.out.println("内容长度：  " + length);
        System.out.println("内容： " + new String(content, UTF_8));
        System.out.println("服务端收到消息的数量： " + (++count));
        //向客户端返回数据
        String responseMsg = LocalDateTime.now().toString();
        int responseLength = responseMsg.getBytes(UTF_8).length;
        byte[] responseContent = responseMsg.getBytes(UTF_8);
        //封装协议
        CustomProtocol protocol = new CustomProtocol(responseLength, responseContent);
        ctx.channel().writeAndFlush(protocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
