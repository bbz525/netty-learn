package com.poplar.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * Created By poplar on 2019/9/22
 */
public class HelloWorldTestHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("请求方法名： " + request.method().name());
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("/favicon.ico");
                return;
            }
            //响应会客户端的内容
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            //设置响应头的内容
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain").set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //把内容写到客服端
            /*
             *  ctx.writeAndFlush(response);
             *  ctx.channel().writeAndFlush(response);
             *  在Netty中有两种发送消息的方法
             * 可以直接写到Channel中,也可以写到与ChannelHandler所关联的那个channelHandlerContext中, 对于前一种方式来说, 消息会从ChannelPipeline
             * 的末尾开始流动，而对于后一种方式来说，消息会从ChannelPipeline 的下一个ChannelHandler开始流动。
             * 结论:
             * 1. ChannelHandlerContext与ChannelHandler之间的关联绑定关系是永远都不会发生改变的,因此对其进行缓存是没有任何问最的
             * 2.对于与Channel的同名方法来说, ChannelHandlerContext的方法将会产生更短的事件流,所以我们应该在可能的情况下利用这个特性来提升应用性能
             */
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered");
        super.channelUnregistered(ctx);
    }
}
