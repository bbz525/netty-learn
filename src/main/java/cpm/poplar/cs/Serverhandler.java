package cpm.poplar.cs;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created By poplar on 2019/9/23
 */
public class Serverhandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //打印远程地址和接受客服端发送过来的消息
        System.out.println(ctx.channel().remoteAddress() + " ," + msg);
        ctx.channel().writeAndFlush("From server " + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //关闭链接
        ctx.close();
    }
}
